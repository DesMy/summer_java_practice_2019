package Model;

import java.awt.*;

public class Edge {
    private Color color = Color.BLACK;
    public Node startNode;
    public Node endNode;
    public Integer count1 = 0;
    public Integer count2 = 0;

    public Edge(Node startNode, Node endNode, int count1, int count2) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.count1 = count1;
        this.count2 = count2;
        startNode.addListEdge(this);
        endNode.addListEdge(this);
    }

    public Edge(Edge edge) {
        this.startNode = edge.startNode;
        this.endNode = edge.endNode;
        this.count1 = edge.count1;
        this.count2 = edge.count2;
        startNode.addListEdge(this);
        endNode.addListEdge(this);
    }


    public Color getColor() {
        return  this.color;
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
