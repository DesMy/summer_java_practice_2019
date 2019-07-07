public class Edge {
    Vertex start;
    Vertex end;
    int flow;
    int capacity;
    Edge(Vertex start, Vertex end, int capacity) {
        this.start = start;
        this.end = end;
        this.capacity = capacity;
        this.flow = 0;
    }

    int residualFlow(Vertex from) {
        if (from == start)
            return capacity - flow;
        else
            return flow;
    }
}
