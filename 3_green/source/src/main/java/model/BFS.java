package model;

import java.util.ArrayList;
import java.util.ArrayDeque;

abstract class BFS 
{

    static String findPath(Vertex from, Vertex to) {
        class VertexPair {
            private VertexPair(Vertex cur, Vertex par) {
                current = cur;
                parent = par;
            }
            private Vertex current;
            private Vertex parent;
        }


        StringBuilder out = new StringBuilder();
        ArrayList <Vertex> checked = new ArrayList<>();
        ArrayList <VertexPair> pairs = new ArrayList<>();
        ArrayDeque <Vertex> vertexQueue = new ArrayDeque<>();

        pairs.add(new VertexPair(from, null));
        checked.add(from);
        vertexQueue.add(from);
 
        while (!(vertexQueue.isEmpty())) {
            Vertex curVertex = vertexQueue.pollFirst();
 
            if(curVertex == to) {

                while (curVertex != null){
                    out.append(curVertex.name());
                    for(VertexPair curPair: pairs) {
                        if (curPair.current == curVertex) {
                            curVertex = curPair.parent;
                        }
                    }
                }
 
                return out.reverse().toString();
            }
 
            for(Edge curEdge: curVertex.outEdges()) {
                if(curEdge.getCapacity() != 0 && checked.indexOf(curEdge.to()) == -1) {
                    pairs.add(new VertexPair(curEdge.to(), curVertex));
                    checked.add(curEdge.to());
                    vertexQueue.add(curEdge.to());
                }
            }
        }
        return null;
    }
}