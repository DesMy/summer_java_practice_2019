package Practice.GUI;

import java.awt.*;

public class Edge {
    private Color color = Color.BLACK;
    private Node startNode;
    private Node endNode;
    private Integer count1 = 0;
    private Integer count2 = 0;

    public Edge(Node startNode, Node endNode, int count1, int count2) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.count1 = count1;
        this.count2 = count2;
        startNode.addListEdge(this);
        endNode.addListEdge(this);
    }


    public void changeColor(Color color) {
        this.color = color;
    }

    public Node getStartNode() {
        return startNode;
    }

    public Node getEndNode() {
        return endNode;
    }

    public Integer getWeight() {
        return count1;
    }

    public Integer getBandwidth() {
        return count2;
    }

}
