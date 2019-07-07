public class DFS {
    boolean DFsearch(Vertex sourcePeak, Vertex sinkPeak) {
        if (sourcePeak == sinkPeak) {
            return true;
        }
        for (Edge e : sourcePeak.neighbours) {
            if (e.residualFlow(sourcePeak) > 0) {
                Vertex other = e.end == sourcePeak ? e.start : e.end;
                if (!other.visited) {
                    other.visited = true;
                    other.cameFrom = e;
                    boolean reached = DFsearch(other, sinkPeak);
                    other.visited = false;
                    if (reached) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
