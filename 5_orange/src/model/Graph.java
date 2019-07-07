import java.util.ArrayList;

public class Graph {
    ArrayList<Edge> edges;
    ArrayList<Vertex> vrtx;

    Graph() {
        edges = new ArrayList();
        vrtx = new ArrayList();
    }

    void addEdge(Vertex start, Vertex end, int capacity){
        Edge edg = new Edge(start, end, capacity);
        edges.add(edg);
        start.neighbours.add(edg);
        end.neighbours.add(edg);
    }

    void addVertex(char newVertex){
        vrtx.add(new Vertex(newVertex));
    }
}
