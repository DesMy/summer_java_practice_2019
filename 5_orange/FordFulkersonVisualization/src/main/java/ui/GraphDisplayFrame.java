/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.swing.JComponent;
import javax.swing.JPanel;
import model.Edge;
import model.Graph;
import model.Vertex;

/**
 *
 * @author theph
 */
public class GraphDisplayFrame extends JPanel {

    HashMap<String, GraphVertex> uiVMapping;
    //HashMap<Character, Vertex> contentVMapping;

    HashMap<Edge, GraphEdge> uiEMapping;
    //HashMap<Character, Edge> contentEMapping;

    public void init(Graph graph) {
        uiVMapping = new HashMap<>();
        uiEMapping = new HashMap<>();
        for (Vertex v : graph.getVrtx()) {
            addVertex(v);
        }
        for (Edge e : graph.getEdges()) {
            addEdge(e);
        }
    }

    public void addVertex(Vertex v) {
        GraphVertex gv = new GraphVertex(this, (int) (Math.random() * 400), (int) (Math.random() * 400), v);
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

    public GraphDisplayFrame() {
        this.setLayout(null);
    }

}
