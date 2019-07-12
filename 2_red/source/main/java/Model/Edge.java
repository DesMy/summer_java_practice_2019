package Model;

import java.awt.*;

public class Edge {
    private Color color = Color.BLACK;
    public Node startNode;
    public Node endNode;
    public Integer weight = 0;
    public Integer bandwidth = 0;

    public Edge(Node startNode, Node endNode, int weight, int bandwidth) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.weight = weight;
        this.bandwidth = bandwidth;
        startNode.addListEdge(this);
        endNode.addListEdge(this);
    }

    public Edge(Edge edge) {
        this.startNode = edge.startNode;
        this.endNode = edge.endNode;
        this.weight = edge.weight;
        this.bandwidth = edge.bandwidth;
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
        return weight;
    }

    public Integer getBandwidth() {
        return bandwidth;
    }

}
