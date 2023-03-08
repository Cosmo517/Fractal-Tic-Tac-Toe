package MCTS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Node
{
    State state;
    Node parent;
    List<Node> childArray;

    public Node()
    {
        this.state = new State();
        childArray = new ArrayList<>();
    }

    public Node(State state)
    {
        this.state = state;
        childArray = new ArrayList<>();
    }

    public Node(State state, Node parent, List<Node> childArray)
    {
        this.state = state;
        this.parent = parent;
        this.childArray = childArray;
    }

    public Node(Node node)
    {
        this.childArray = new ArrayList<>();
        this.state = new State(node.getState());
        if (node.getParent() != null)
            this.parent = node.getParent();
        List<Node> childArray = node.getChildArray();
        for (Node child : childArray)
            this.childArray.add(new Node(child));
    }

    public Node getRandomChildNode()
    {
        int numberOfPossibilities = this.childArray.size();
        int selectRandom = (int) (Math.random() * numberOfPossibilities);
        return this.getChildArray().get(selectRandom);
    }

    public Node getChildWithMaxScore()
    {
        return Collections.max(this.childArray, Comparator.comparing(c ->
                c.getState().getVisitCount()));
    }

    public void setState(State newState)
    {
        state = newState;
    }

    public State getState()
    {
        return state;
    }

    public void setParent(Node newParent)
    {
        parent = newParent;
    }

    public Node getParent()
    {
        return parent;
    }

    public void setChildArray(List<Node> newChildArray)
    {
        childArray = newChildArray;
    }

    public List<Node> getChildArray()
    {
        return childArray;
    }
}
