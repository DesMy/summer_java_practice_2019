package model;

import java.util.ArrayList;
import java.util.Iterator;

public class Graph
{
    public Graph(char source, char sink) {
        this.source = source;
        this.sink = sink;
        vertices = new ArrayList<>();
        vertices.add(new Vertex(source));
        vertices.add(new Vertex(sink));
    }

    public Graph(Graph o) {
        vertices = new ArrayList<>();
        source = o.source();
        sink = o.sink();
        addVertex(source);
        addVertex(sink);

        for(Vertex v : o.getVertices()) {
            addVertex(v.name());
            for(Edge e : v.outEdges()) {
                addEdge(e.from().name(), e.to().name(), e.getCapacity(), e.getFlow());
            }
        }
    }

    //Getters:
    public ArrayList<Vertex> getVertices() {
        return vertices;
    }
    public char source() {
        return source;
    }
    public char sink() {
        return sink;
    }

    public void addVertex(char name) {
        if(findVertex(name) == null) {
            vertices.add(new Vertex(name));
        }
    }
    public void addEdge(char from, char to, int capacity, int flow) {
        if(capacity < 0) {
            throw new IllegalArgumentException("Negative weight of edge.");
        }
        if(from == to) {
            throw new IllegalArgumentException("Loop is not allowed.");
        }

        addVertex(from);
        addVertex(to);
        Vertex _from = findVertex(from);
        Vertex _to = findVertex(to);
        if(!isEdge(from, to)) {
            _from.addEdge(new Edge(_from, _to, capacity, flow));
        }
    }

    public void deleteVertex(char name) {
        Vertex v = findVertex(name);
        if(v != null) {
            for(Vertex vt : vertices) {
                Iterator<Edge> it = vt.outEdges().iterator();
                while(it.hasNext()) {
                    Edge e = it.next();
                    if(e.to().name() == v.name()) {
                        it.remove();
                    }
                }
            }
            Iterator<Edge> it = v.outEdges().iterator();
            while(it.hasNext()) {
                Edge e = it.next();
                if(e.to().name() == v.name()) {
                    it.remove();
                }
            }
            vertices.remove(v);
        }
    }

    public void deleteEdge(char from, char to) {
        Vertex fr = findVertex(from);
        Vertex t = findVertex(to);
        if(fr != null && t != null) {
            Iterator<Edge> it = fr.outEdges().iterator();
            while(it.hasNext()) {
                Edge e = it.next();
                if(e.to().name() == t.name()) {
                    it.remove();
                    break;
                }
            }
        }
    }

    boolean isEdge(char from, char to) {
        Vertex _from = findVertex(from);
        Vertex _to = findVertex(to);
        if(_from == null || _to == null) {
            return false;
        }
        for(Edge e : _from.outEdges()) {
            if(e.to().name() == to) {
                return true;
            }
        }

        return false;
    }

    public int getFlow() {
        Vertex _source = getVertices().get(0);
        int curFlow = 0;
        for(Edge e : _source.outEdges()) {
            curFlow += e.getFlow();
        }
        return curFlow;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(Vertex v : vertices) {
            str.append(v.name()).append(System.getProperty("line.separator"));
        }
        for(Vertex v : vertices) {
            for(Edge e : v.outEdges()) {
                str.append(e.from().name())
                        .append(' ')
                        .append(e.to().name())
                        .append(' ')
                        .append(e.getCapacity())
                        .append(' ')
                        .append(e.getFlow())
                        .append(System.getProperty("line.separator"));
            }
        }
        return str.toString();
    }

    Vertex findVertex(char name) {
        for (Vertex it : vertices) {
            if (it.name() == name) {
                return it;
            }
        }
        return null;
    }

    private ArrayList<Vertex> vertices;
    private char source;
    private char sink;
}