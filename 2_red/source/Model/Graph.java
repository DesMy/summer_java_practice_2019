package Model;

import java.util.LinkedList;

public class Graph {

    public LinkedList <Node> nodes;
    public LinkedList <Edge> edges;

    public Graph(){
        nodes = new LinkedList<Node>();
        edges = new LinkedList<Edge>();
    }

    public Graph(Graph graph){
        this();
        for (Node node : graph.nodes) {
            this.nodes.add(node);
        }
        for (Edge edge : graph.edges) {
            this.edges.add(new Edge(edge));
        }
    }

    public void addNode(Node _node){
        nodes.add(_node);
    }

    public void addEdge(Edge _edge){
        edges.add(_edge);
    }

    public boolean deleteNode(Node node){
        for (Edge edge:node.getListEdge()){
            deleteEdge(edge);
        }
        return nodes.remove(node);
    }

    public boolean deleteEdge(Edge edge) {
        return edges.remove(edge);
    }
    public  LinkedList<Node> getNodes(){return nodes;}

    public void Clear(){
        nodes.clear();
        edges.clear();
    }
}
