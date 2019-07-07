package model;

import java.util.ArrayList;
import java.util.LinkedList;

public class FordFulkerson {

    public static LinkedList<Graph> process(Graph graph, PathFindingAlgorhithm pathFinder) {
        // cannot find a path from source to sink, algorithm stop;
        LinkedList<Graph> result = new LinkedList<>() ;
        result.add(graph.clone());
        while (pathFinder.search(graph.getSource(), graph.getSink())) {
            
            //Trace back from sink to get the path;
            Path path = pathFinder.buildPath(graph);
            
            int cf_min = path.getResidualFlowMin();
            
            Vertex current = graph.getSource();
            for (Edge e : path.getPath()) {
                if (e.getStart() == current) {
                    // f(u,v) - arc in correct direction
                    e.setFlow( e.getFlow() + cf_min);
                    current = e.getEnd();
                } else {
                    //f(v,u) - arc in opposite direction
                    e.setFlow( e.getFlow() - cf_min);
                    current = e.getStart();
                }
            }
            // TODO: clone the graph
            result.add(graph.clone());
        }
        return result;
    }
}
