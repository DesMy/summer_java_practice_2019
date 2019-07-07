package src.draw;

import java.awt.*;
import src.source.*;

public class Drawing implements Drawable {
    private Graph graph;
    public Vertex[] vertex;
    public Edge[] edges;
    private Point center;
    //private final int ovalRadius = 40;

    public Drawing() {
        graph = null;
    }

    @Override
    public void draw(Graphics2D g) {
        int ovalRadius = Vertex.radius;
        for(int i = 0; i < edges.length; i++) {
            if(edges[i].IsBridge()) {
                g.setColor(Color.RED);
            }
            else g.setColor(edges[i].getColor());
            g.drawLine(edges[i].getStartV().getVertexCenter().x + (ovalRadius - graph.getVertexList().length)/2,
                    edges[i].getStartV().getVertexCenter().y + (ovalRadius - graph.getVertexList().length)/2,
                    edges[i].getEndV().getVertexCenter().x + (ovalRadius - graph.getVertexList().length)/2,
                    edges[i].getEndV().getVertexCenter().y + (ovalRadius - graph.getVertexList().length)/2);
        }
        for(int i = 0; i < vertex.length; i++) {
            g.setColor(vertex[i].getColor());
            g.fillOval(vertex[i].getVertexCenter().x, vertex[i].getVertexCenter().y,
                    ovalRadius - graph.getVertexList().length, ovalRadius - graph.getVertexList().length);
            g.setColor(Color.BLACK);
            g.drawString(Integer.toString(i + 1), vertex[i].getVertexCenter().x + (ovalRadius - graph.getVertexList().length)/2 - 4,
                    vertex[i].getVertexCenter().y + (ovalRadius - graph.getVertexList().length)/2 + 4);
        }
    }

    public void setGraph(Graph graph){
        this.graph = graph;
        vertex = new Vertex[graph.getVertexList().length];
        for(int i = 0; i < vertex.length; i++) {
           vertex[i] = new Vertex(50 + vertex.length );
        }

        int max_edges = (graph.getVertexList().length * (graph.getVertexList().length - 1) / 2);
        edges = new Edge[max_edges];
        for(int i = 0; i < max_edges; i++) {
            edges[i] = new Edge();
        }
        setAllCoordinates(max_edges);
    }

    public void setCenterCoordinates(Point center){
        this.center = center;
    }

    private void setAllCoordinates(int max_edges){
        int R = 50 + graph.getVertexList().length * graph.getVertexList().length;
        for(int i = 0; i < graph.getVertexList().length; i++){
            vertex[i].setVertexCenters((int) (center.x + R * Math.cos(360/graph.getVertexList().length + 2 * Math.PI * i / graph.getVertexList().length)),
                    (int) (center.y + R * Math.sin(360/graph.getVertexList().length + 2 * Math.PI * i / graph.getVertexList().length)));
        }
        Point[] edges_arr = new Point[max_edges];
        edges_arr = graph.getEdges();
        if(edges_arr == null)
            System.out.println("Ребер нет, но вы держитесь");
        else {
            for (int i = 0; i < graph.getEdgeAmount(); i++) {
                edges[i].setStartV(vertex[edges_arr[i].x]);
                edges[i].setEndV(vertex[edges_arr[i].y]);
            }
            if(graph.Bridge()) {
                setBridges();
            }
        }
    }

    private void setBridges() {
        if(!graph.bridges.isEmpty()) {
            for(int i = 0; i < graph.bridges.size(); i++){
                for(int j = 0; j < graph.getEdgeAmount(); j++) {
                    if (vertex[graph.bridges.elementAt(i).x - 1] == edges[j].getStartV() ||
                            vertex[graph.bridges.elementAt(i).x - 1] == edges[j].getEndV()) {
                        if (vertex[graph.bridges.elementAt(i).y - 1] == edges[j].getEndV() ||
                                vertex[graph.bridges.elementAt(i).y - 1] == edges[j].getStartV())
                        {
                            edges[j].setBridge();
                        }
                    }
                }
            }
        }
    }

    public Vertex[] getVertices(){ return vertex; }
}
