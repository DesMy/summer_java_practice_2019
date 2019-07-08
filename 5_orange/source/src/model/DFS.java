package model;

public class DFS extends PathFindingAlgorhithm{
    @Override
    boolean search(Vertex sourcePeak, Vertex sinkPeak) {
        if (sourcePeak == sinkPeak) {
            return true;
        }
        for (Edge e : sourcePeak.getNeighbours()) {
            if (e.residualFlow(sourcePeak) > 0) {
                Vertex other = e.getEnd().equals(sourcePeak) ? e.getStart() : e.getEnd();
                if (!other.isVisited()) {
                    other.setVisited(true);
                    other.setCameFrom(e);
                    boolean reached = search(other, sinkPeak);
                    other.setVisited(false);
                    if (reached) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
