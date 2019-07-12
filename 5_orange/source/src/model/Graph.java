package model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import ui.VertexNotFoundException;

public class Graph implements Serializable, Cloneable {

    private ArrayList<Edge> edges;
    private ArrayList<Vertex> vrtx;

    private Vertex source;
    private Vertex sink;

    public Graph() {
        edges = new ArrayList<>();
        vrtx = new ArrayList<>();
    }

    public void initPrevFlow(){
        for(Edge e: edges) e.setPrevFlow(e.getFlow());
    }
    
    public void resetFlow(){
        for(Edge e: edges) e.setFlow(0);
    }
    public Vertex getSource() {
        return source;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public ArrayList<Vertex> getVrtx() {
        return vrtx;
    }

    public void setVrtx(ArrayList<Vertex> vrtx) {
        this.vrtx = vrtx;
    }

    public void setSource(Vertex source) throws Exception {
        if (source.equals(this.sink)) {
            this.sink = null;
        }
        if (this.source != null) {
            this.source.setSource(false);
        }
        this.source = source;
        this.source.setSource(true);
    }

    public Vertex getSink() {
        return sink;
    }

    public void setSink(Vertex sink) throws Exception {
        if (sink.equals(this.source)) {
            this.source = null;
        }
        if (this.sink != null) {
            this.sink.setSink(false);
        }
        this.sink = sink;
        this.sink.setSink(true);
    }

    public Edge addEdge(Vertex start, Vertex end, int capacity) throws Exception {
        Edge edg = new Edge(start, end, capacity);
        for (int i = edges.size() - 1; i >= 0; i--) {
            if (edg.getStart().equals(edges.get(i).getStart())
                    && edg.getEnd().equals(edges.get(i).getEnd())) {
                throw new Exception("ERROR: This edge already exists!");
            }
        }
        edges.add(edg);
        start.getNeighbours().add(edg);
        end.getNeighbours().add(edg);
        return edg;
    }

    public Vertex addVertex(String newVertex) throws Exception {
        if (getVertexByName(newVertex) != null) {
            throw new Exception("Vertex with this name already exist");
        }
        Vertex result = new Vertex(newVertex);

        vrtx.add(result);
        return result;
    }

    public DeleteVertexActionResult deleteVertex(Vertex v) throws VertexNotFoundException {
        if (v != null && vrtx.contains(v)) {
            LinkedList<Edge> affectedEdges = new LinkedList<>();
            for (int i = edges.size() - 1; i >= 0; i--) {
                if (edges.get(i).getStart().equals(v) || edges.get(i).getEnd().equals(v)) {
                    affectedEdges.add(edges.remove(i));
                }
            }
            if (source != null && source.equals(v)) {
                source = null;
            }
            if (sink != null && sink.equals(v)) {
                sink = null;
            }
            DeleteVertexActionResult res = new DeleteVertexActionResult(vrtx.remove(vrtx.indexOf(v)), affectedEdges);
            return res;
        } else {
            throw new VertexNotFoundException();
        }
    }

    public void deleteEdge(Vertex v1, Vertex v2) throws VertexNotFoundException {
        if (v1 != null && v2 != null && vrtx.contains(v1) && vrtx.contains(v2)) {

            for (int i = edges.size() - 1; i >= 0; i--) {
                if (edges.get(i).getStart().equals(v1) && edges.get(i).getEnd().equals(v2)) {
                    Edge delete = edges.remove(i);
                    delete.getStart().getNeighbours().remove(delete);
                    delete.getEnd().getNeighbours().remove(delete);
                    break;
                }
            }
        } else {
            throw new VertexNotFoundException();
        }
    }

    public int getTotalFlow() {
        int total_flow = 0;
        for (Edge e : this.source.getNeighbours()) {
            total_flow += e.getFlow();
        }
        return total_flow;
    }

    public Vertex getVertexByName(String name) {
        for (Vertex v : getVrtx()) {
            if (v.getName().equals(name)) {
                return v;
            }
        }
        return null;
    }

    @Override
    protected Graph clone() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Graph) ois.readObject();
        } catch (IOException e) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
