package model;

import java.util.ArrayList;

public abstract class PathFindingAlgorhithm {
    Path buildPath(Graph graph)
    {
        Path result = new Path(new ArrayList<>(), Integer.MAX_VALUE);
        
        Vertex current = graph.getSink();
        while (current.getCameFrom() != null && !current.equals(graph.getSource())) {
            Vertex back = current.getCameFrom().getStart().equals(current) ? current.getCameFrom().getEnd() : current.getCameFrom().getStart();

            result.getPath().add(0,current.getCameFrom());

            int cfp = current.getCameFrom().residualFlow(back);
            result.setResidualFlowMin(result.getResidualFlowMin() > cfp ? cfp : result.getResidualFlowMin());

            // clear cameFrom field for next loop
            current.setCameFrom(null);
            current = back;
        }
        return result;
    }

    abstract boolean search(Vertex sourcePeak, Vertex sinkPeak);

}
