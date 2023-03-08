package MCTS;

import Driver.Board;

import java.util.List;

public class MonteCarloTreeSearch
{
    private static final int WIN_SCORE = 10;
    private int level;
    private int opponent;

    public MonteCarloTreeSearch()
    {
        this.level = 3;
    }

    public Board findNextMove(Board board, int playerNumber)
    {
        long start = System.currentTimeMillis();
        long end = start + 60 * getMillisForCurrentLevel();

        opponent = 3 - playerNumber;
        Tree tree = new Tree();
        Node rootNode = tree.getRoot();
        rootNode.getState().setBoard(board);
        rootNode.getState().setPlayerNumber(opponent);

        while (System.currentTimeMillis() < end)
        {
            // Phase 1 - Selection
            Node promisingNode = selectPromisingNode(rootNode);
            // Phase 2 - Expansion
            if (promisingNode.getState().getBoard().checkStatus() == Board.IN_PROGRESS)
                expandNode(promisingNode);

            // Phase 3 - Simulation
            Node nodeToExplore = promisingNode;
            if (promisingNode.getChildArray().size() > 0)
            {
                nodeToExplore = promisingNode.getRandomChildNode();
            }
            int playoutResult = simulateRandomPlayout(nodeToExplore);

            // phase 4 - Update
            backPropogation(nodeToExplore, playoutResult);
        }

        Node winnerNode = rootNode.getChildWithMaxScore();
        tree.setRoot(winnerNode);
        return winnerNode.getState().getBoard();
    }

    private Node selectPromisingNode(Node rootNode)
    {
        Node node = rootNode;
        while (node.getChildArray().size() != 0)
            node = UCT.findBestNodeWithUCT(node);
        return node;
    }

    private void expandNode(Node node)
    {
        List<State> possibleStates = node.getState().getAllPossibleStates();
        possibleStates.forEach(state ->
        {
            Node newNode = new Node(state);
            newNode.setParent(node);
            newNode.getState().setPlayerNumber(node.getState().getOpponent());
            node.getChildArray().add(newNode);
        });

    }

    private int simulateRandomPlayout(Node node)
    {
        Node tempNode = new Node(node);
        State tempState = tempNode.getState();
        int boardStatus = tempState.getBoard().checkStatus();

        if (boardStatus == opponent)
        {
            tempNode.getParent().getState().setWinScore(Integer.MIN_VALUE);
            return boardStatus;
        }
        while (boardStatus == Board.IN_PROGRESS)
        {
            tempState.togglePlayer();
            tempState.randomPlay();
            boardStatus = tempState.getBoard().checkStatus();
        }
        return boardStatus;
    }

    private void backPropogation(Node nodeToExplore, int playerNumber)
    {
        Node tempNode = nodeToExplore;
        while (tempNode != null)
        {
            tempNode.getState().incrementVisit();
            if (tempNode.getState().getPlayerNumber() == playerNumber)
                tempNode.getState().addScore(WIN_SCORE);
            tempNode = tempNode.getParent();
        }
    }

    private int getMillisForCurrentLevel()
    {
        return 2 * (this.level - 1) + 1;
    }


    public void setLevel(int level)
    {
        this.level = level;
    }

    public int getLevel()
    {
        return level;
    }
}
