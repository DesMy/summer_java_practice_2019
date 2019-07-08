package model;

import java.util.ArrayList;
import controller.Controller;

public class Graph
{
    public Graph(Controller controller) {
        vertices = new ArrayList<Vertex>();
        this.controller = controller;
    }

    public Graph() {
        vertices = new ArrayList<Vertex>();
        this.controller = null;
    }

    public void addPair(char from, char to, int weight) {
        if(weight < 0) {
            throw new IllegalArgumentException("Negative or zero weight of edge.");
        }
        if(from == to) {
            throw new IllegalArgumentException("Loop is not allowed.");
        }

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

    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    private ArrayList<Vertex> vertices;
    public final Controller controller;
}