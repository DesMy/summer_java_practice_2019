import java.util.ArrayList;

public class BFS {
    boolean BFsearch(Vertex sourcePeak, Vertex sinkPeak) {
        ArrayList<Vertex> open_set = new ArrayList();
        ArrayList<Vertex> closed_set = new ArrayList();
        open_set.add(sourcePeak);
        while (!open_set.isEmpty()) {

            Vertex current = open_set.get(open_set.size()-1);
            open_set.remove(open_set.size()-1);

            closed_set.add(current);

            if (current == sinkPeak) {
                // found the path
                return true;
            }

            for (Edge e : current.neighbours) {
                if (e.residualFlow(current) > 0) {
                    Vertex other = e.end == current ? e.start : e.end;

                    if (closed_set.indexOf(other) != closed_set.size()-1) {
                        // Already processed (in closed set)
                        continue;
                    }

                    if (open_set.indexOf(other) == open_set.size()-1) {
                        // not in open set
                        open_set.add(other);
                        other.cameFrom = e;
                    }
                }
            }
        }
        return false;
    }
}
