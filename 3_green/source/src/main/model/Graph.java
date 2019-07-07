package model;

import java.util.ArrayList;

public class Graph 
{
    public Graph() {
        vertices = new ArrayList<Vertex>();
    }
 
    public void addPair(char from, char to, int weight) {
        Vertex _from = findVertex(from);
        Vertex _to = findVertex(to);
 
        if(_from == null) {
            _from = new Vertex(from);
            vertices.add(_from);
        }
        if(_to == null) {
            _to = new Vertex(to);
            vertices.add(_to);
        }
        if(!(_from.isEdge(to))) {
            _from.addEdge(new Edge(_to, weight));
        }
    }
 
    Vertex findVertex(char name) {
        for(Vertex it : vertices) {
            if(it.name() == name) {
                return it;
            }
        }
        return null;
    }
 
    private ArrayList<Vertex> vertices;
}