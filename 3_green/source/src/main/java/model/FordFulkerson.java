package model;

import java.util.ArrayList;

public abstract class FordFulkerson
{
    public static int process(Graph graph, char source, char sink) {
        Vertex _source = graph.findVertex(source);
        Vertex _sink = graph.findVertex(sink);
        if(source == sink || _source == null || _sink == null) {
            throw new IllegalArgumentException("Wrong source or sink.");
        }

        int flow = 0;
        String path = BFS.findPath(_source, _sink);
        while (path != null) {
            if (graph.controller != null) {
                graph.controller.copyGraph(graph);
            }
            flow += step(graph, path);
            path = BFS.findPath(_source, _sink);
        }
        return flow;
    }

    public static int step(Graph graph, String path) {
        ArrayList<Edge> edgeList = new ArrayList();
        Vertex curVertex = graph.findVertex(path.charAt(0));
        int minCapacity = 0;
        for (int i = 1; i < path.length(); i++) {
            for (Edge curEdge: curVertex.outEdges()) {
                if (curEdge.dest().name() == path.charAt(i)) {
                    edgeList.add(curEdge);
                    if(curEdge.getCapacity() < minCapacity || minCapacity == 0){
                        minCapacity = curEdge.getCapacity();
                    }
                    graph.addPair(path.charAt(i), path.charAt(i-1), curEdge.getCapacity());
                    curVertex = curEdge.dest();
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