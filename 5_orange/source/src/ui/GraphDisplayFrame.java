/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.event.MouseInputAdapter;
import model.Edge;
import model.Graph;
import model.Vertex;

/**
 *
 * @author theph
 */
public class GraphDisplayFrame extends JPanel implements VertexActionListener {
    
    Graph graph;
    HashMap<String, GraphVertex> uiVMapping;
    
    HashMap<Edge, GraphEdge> uiEMapping;
    
    Component parent;
    
    private Line2D tempEdge;
    
    public GraphDisplayFrame(Component parent) {
        this.setLayout(null);
        this.parent = parent;
        tempEdge = null;
    }
    
    public void init(Graph graph) {
        this.removeAll();
        this.graph = graph;
        uiVMapping = new HashMap<>();
        uiEMapping = new HashMap<>();
        for (Vertex v : graph.getVrtx()) {
            addVertex(v);
        }
        for (Edge e : graph.getEdges()) {
            addEdge(e);
        }
    }
    
    public Line2D getTempEdge() {
        return tempEdge;
    }
    
    public void setTempEdge(Line2D tempEdge) {
        this.tempEdge = tempEdge;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(this.tempEdge!=null){
            g.drawLine((int)this.tempEdge.getX1(), (int)this.tempEdge.getY1(), (int)this.tempEdge.getX2(), (int)this.tempEdge.getY2());
        }
    }
    
    
    @Override
    public void onVertexPositionChanged() {
        repaint();
    }
    
    @Override
    public void onSourceChanged(GraphVertex newSource) {
        repaint();
    }
    
    @Override
    public void onSinkChanged(GraphVertex newSink) {
        repaint();
    }
    
    @Override
    public void onDelete(GraphVertex v) {
        repaint();
    }
    
    public void addVertex(Vertex v) {
        addVertex(v, new Point((int) (Math.random() * 400), (int) (Math.random() * 400)));
    }
    
    public void addVertex(Vertex v, Point location) {
        GraphVertex gv = new GraphVertex(this, (int) location.getX(), (int) location.getY(), v);
        //setup listeners
        if (this.parent instanceof VertexActionListener) {
            gv.addVertexChangedListener((VertexActionListener) this.parent);
        }
        gv.addVertexChangedListener(this);
        
        this.add(gv);
        uiVMapping.put(v.getName(), gv);
        repaint();
    }
    
    public void addEdge(Edge e) {
        String nameStart = e.getStart().getName();
        String nameEnd = e.getEnd().getName();
        GraphEdge ge = new GraphEdge(this, uiVMapping.get(nameStart), uiVMapping.get(nameEnd), e);
        this.add(ge);
        uiEMapping.put(e, ge);
        repaint();
    }
    
    public void deleteVertex(Vertex v) {
        if (uiVMapping.containsKey(v.getName())) {
            this.remove(uiVMapping.remove(v.getName()));
        }
        revalidate();
        repaint();
    }
    
    public void deleteEdge(Edge e) {
        if (uiEMapping.containsKey(e)) {
            this.remove(uiEMapping.remove(e));
        }
        revalidate();
        repaint();
    }
    
    public GraphEdge hasEdgeAt(int x, int y) {
        for (GraphEdge e : uiEMapping.values()) {
            if (Line2D.ptSegDist(e.getV1().getX() + GraphElement.radius, e.getV1().getY() + GraphElement.radius, e.getV2().getX() + GraphElement.radius, e.getV2().getY() + GraphElement.radius, x, y) < 7) {
                return e;
            }
        }
        return null;
    }
    
    public void deleteEdges(List<Edge> edges) {
        for (Edge e : edges) {
            deleteEdge(e);
        }
    }
    
    public void loadStepGraph(Graph graph) {
        for (Vertex v : graph.getVrtx()) {
            uiVMapping.get(v.getName()).setData(v);
        }
        for (Edge e : graph.getEdges()) {
            uiEMapping.get(e).setData(e);
        }
    }
    
    @Override
    public void onVertexSelected(GraphVertex v, EventObject event) {
    }
    
}
