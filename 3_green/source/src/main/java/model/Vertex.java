package model;

import java.util.ArrayList;

public class Vertex
{
    Vertex(char name) {
        this.name = name;
        outEdges = new ArrayList<>();
    }
 
    //Getters:
    public char name() {
        return name;
    }
    public ArrayList<Edge> outEdges() {
        return outEdges;
    }

    //Setters:
    public void addEdge(Edge edge) {
        outEdges.add(edge);
    }
 
    private char name;
    private ArrayList<Edge> outEdges;
}