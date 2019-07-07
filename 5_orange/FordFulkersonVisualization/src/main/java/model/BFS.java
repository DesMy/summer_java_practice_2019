package model;

import java.util.ArrayList;

public class BFS extends PathFindingAlgorhithm{
    @Override
    boolean search(Vertex sourcePeak, Vertex sinkPeak) {
        ArrayList<Vertex> open_set = new ArrayList<>();
        ArrayList<Vertex> closed_set = new ArrayList<>();
        open_set.add(sourcePeak);
        while (!open_set.isEmpty()) {

            Vertex current = open_set.remove(open_set.size()-1);

            closed_set.add(current);

            if (current.equals(sinkPeak)) {
                // found the path
                return true;
            }

            for (Edge e : current.getNeighbours()) {
                if (e.residualFlow(current) > 0) {
                    Vertex other = e.getEnd().equals(current) ? e.getStart() : e.getEnd();

                    if (closed_set.contains(other)) {
                        // Already processed (in closed set)
                        continue;
                    }

                    if (!open_set.contains(other)) {
                        // not in open set
                        open_set.add(other);
                        other.setCameFrom(e);
                    }
                }
            }
        }
        return false;
    }
}
