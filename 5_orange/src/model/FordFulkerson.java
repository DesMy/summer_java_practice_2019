import java.util.ArrayList;

public class FordFulkerson {
    int process(Graph graph, PathFindingAlgorhithm algorithm, ArrayList<Graph> graphs) {
        Vertex sourcePeak = graph.vrtx.get(0);
        Vertex sinkPeak = graph.vrtx.get(graph.vrtx.size()-1);
        int total_flow = algorithm.search(sourcePeak,sinkPeak, graph, graphs);
        return total_flow;
    }
}
