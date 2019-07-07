package model;

import java.util.ArrayList;

class Vertex 
{
    Vertex(char name) {
        this.name = name;
        outEdges = new ArrayList<Edge>();
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
 
    public boolean isEdge(char dest) {
        if(outEdges.isEmpty()) {
            return false;
        }
        for(Edge it : outEdges) {
            if(it.dest().name() == dest) {
                return true;
            }
        }
 
        return false;
    }
 
    private char name;
    private ArrayList<Edge> outEdges;
}