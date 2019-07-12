package model;

import java.util.ArrayList;

public abstract class FordFulkerson
{
    static int process(Graph graph) {

        if(graph.getVertices().get(0).name() == graph.getVertices().get(1).name()) {
            throw new IllegalArgumentException("Wrong source or sink.");
        }

        int flow = 0;
        int stepFlow = step(graph);
        while (stepFlow != 0) {
            flow += stepFlow;
            stepFlow = step(graph);
        }
        return flow;
    }

    public static int step(Graph graph) {

        Vertex _source = graph.getVertices().get(0);
        Vertex _sink = graph.getVertices().get(1);

        String path = BFS.findPath(_source, _sink);
        if (path == null) {

            return 0;
        }
        ArrayList<Edge> edgeList = new ArrayList<>();
        Vertex curVertex = graph.findVertex(path.charAt(0));
        int minCapacity = 0;
        for (int i = 1; i < path.length(); i++) {
            for (Edge curEdge: curVertex.outEdges()) {
                if (curEdge.to().name() == path.charAt(i)) {
                    edgeList.add(curEdge);
                    if(curEdge.getCapacity() < minCapacity || minCapacity == 0){
                        minCapacity = curEdge.getCapacity();
                    }
                    graph.addEdge(path.charAt(i), path.charAt(i-1), curEdge.getCapacity(), curEdge.getFlow());
                    curVertex = curEdge.to();
                    break;
                }
            }
        }
        for(Edge curEdge:edgeList) {
            curEdge.changeCapacity(minCapacity);
        }
        return minCapacity;
    }
}