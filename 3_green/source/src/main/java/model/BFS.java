package model;

import java.util.ArrayList;
import java.util.ArrayDeque;

abstract class BFS 
{

    public static String findPath(Vertex from, Vertex to) {
        class VertexPair {
            VertexPair(Vertex cur, Vertex par) {
                current = cur;
                parent = par;
            }
            private Vertex current;
            private Vertex parent;
        }


        StringBuilder out = new StringBuilder();
        ArrayList <Vertex> checked = new ArrayList<Vertex>();
        ArrayList <VertexPair> pairs = new ArrayList<VertexPair>();
        ArrayDeque <Vertex> vertexQueue = new ArrayDeque<Vertex>();

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
                if(curEdge.getCapacity() != 0 && checked.indexOf(curEdge.dest()) == -1) {
                    pairs.add(new VertexPair(curEdge.dest(), curVertex));
                    checked.add(curEdge.dest());
                    vertexQueue.add(curEdge.dest());
                }
            }
        }
        return null;
    }
}