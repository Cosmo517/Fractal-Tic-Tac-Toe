package MCTS;

import Driver.Board;

import java.util.ArrayList;
import java.util.List;

public class State
{
    Board board;
    int playerNumber;
    int visitCount;
    double winScore;

    public State()
    {
        board = new Board();
    }

    public State(State state)
    {
        this.board = new Board(state.getBoard());
        this.playerNumber = state.getPlayerNumber();
        this.visitCount = state.getVisitCount();
        this.winScore = state.getWinScore();
    }

    public State(Board board)
    {
        this.board = new Board(board);
    }

    public List<State> getAllPossibleStates()
    {
        List<State> possibleStates = new ArrayList<>();
        List<Position> availablePositions = this.board.getEmptyPositions();
        availablePositions.forEach(p ->
        {
            State newState = new State(this.board);
            newState.setPlayerNumber(3 - this.playerNumber);
            newState.getBoard().performMove(newState.getPlayerNumber(), p);
            possibleStates.add(newState);
        });
        return possibleStates;
    }

    public void addScore(double score)
    {
        if (this.winScore != Integer.MIN_VALUE)
            this.winScore += score;
    }

    public void incrementVisit()
    {
        this.visitCount++;
    }

    public void randomPlay()
    {
        List<Position> availablePositions = this.board.getEmptyPositions();
        int totalPossibilities = availablePositions.size();
        int selectRandom = (int) (Math.random() * totalPossibilities);
        this.board.performMove(this.playerNumber, availablePositions.get(selectRandom));
    }

    public void togglePlayer()
    {
        this.playerNumber = 3 - this.playerNumber;
    }


    public int getOpponent()
    {
        return 3 - playerNumber;
    }


    public Board getBoard()
    {
        return board;
    }

    public void setBoard(Board newBoard)
    {
        board = newBoard;
    }

    public void setPlayerNumber(int newPlayerNumber)
    {
        playerNumber = newPlayerNumber;
    }

    public int getPlayerNumber()
    {
        return playerNumber;
    }


    public void setVisitCount(int newVisitCount)
    {
        visitCount = newVisitCount;
    }

    public int getVisitCount()
    {
        return visitCount;
    }

    public void setWinScore(double newWinScore)
    {
        winScore = newWinScore;
    }

    public double getWinScore()
    {
        return winScore;
    }

}
