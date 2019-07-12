package controller;

import java.awt.*;
import java.util.ArrayList;

import ui.Frame;
import model.Graph;
import model.FordFulkerson;
import ui.GraphicVertex;

public class Controller
{
    private Frame ui;
    private Graph graph;
    private ArrayList<Graph> conditions;
    private int currentGraph;

    public Controller() {
        ui = new Frame(this);
        ui.setVisible(true);
        conditions = new ArrayList<>();
        currentGraph = 0;
    }

    public void restoreGraph() {
        if(conditions.size() > 0) {
            graph = new Graph(conditions.get(0));
            conditions.clear();
            currentGraph = 0;
            ui.clearPanel();
            drawGraph(graph);
        }
    }

    public void addVertex(char name, Point p) {
        graph.addVertex(name);
        ui.addVertex(new GraphicVertex(name, p));
    }

    public void addEdge(char from, char to, int capacity, int flow) {
        graph.addEdge(from, to, capacity, flow);
        ui.addEdge(from, to, capacity, flow);
    }

    public void deleteVertex(char name) {
        graph.deleteVertex(name);
        ui.deleteVertex(name);
    }

    public void deleteEdge(char from, char to) {
        graph.deleteEdge(from, to);
        ui.deleteEdge(from, to);
    }

    public void makeGraph(String str) {
        String[] arr = str.split(System.getProperty("line.separator"));
        char source = arr[0].charAt(0);
        char sink = arr[1].charAt(0);
        graph = new Graph(source, sink);

        for(int i=2; i<arr.length; ++i) {
            String[] cur = arr[i].split(" ");
            graph.addEdge(cur[0].charAt(0), cur[1].charAt(0), Integer.parseInt(cur[2]), 0);
        }

        drawGraph(graph);
    }

    public void randomGraph() {
        ArrayList<Character> vertices = new ArrayList<>();
        char source = 'A';
        char sink = 'B';
        vertices.add(source);
        vertices.add(sink);
        graph = new Graph(source, sink);

        for(int i=67; i!=123; ++i) {
            if(i>=91 && i<=96) continue;
            if(Math.random()<0.2) {
                vertices.add((char)i);
            }
        }

        int nWays = (int)(Math.random()*vertices.size()+1);
        ArrayList<String> ways = new ArrayList<>(nWays);
        for(int i=0; i<nWays; ++i) {
            StringBuilder str = new StringBuilder();
            str.append(vertices.get(0));
            for(int l=2; l<(int)(Math.random()*(vertices.size()-1)); ++l) {
                if(Math.random()<0.5) str.append(vertices.get(l));
            }
            str.append(vertices.get(1));
            ways.add(str.toString());
        }

        for(String way : ways) {
            for(int i=0; i<way.length()-1; ++i) {
                graph.addEdge(way.charAt(i), way.charAt(i+1), (int)(Math.random()*100), 0);
            }
        }

        drawGraph(graph);
    }

    public void reset() {
        ui.clearPanel();
        conditions.clear();
        currentGraph = 0;
        graph = null;
        ui.repaint();
    }

    private void drawGraph(Graph graph) {
        int j = 0;
        String[] s = graph.toString().split(System.getProperty("line.separator"));
        ArrayList<GraphicVertex> vrt = new ArrayList<>();
        for(GraphicVertex v : ui.getVertices()) {
            vrt.add(new GraphicVertex(v.getName(), v.getP()));
        }

        ui.clearPanel();

        if(vrt.isEmpty()) {
            ArrayList<Point> coord = new ArrayList<>();
            int r = 150, n = graph.getVertices().size();
            for (int i = 0; i < n; i++) {
                int x = 475 + (int)(r * Math.cos(2*Math.PI/n*i));
                int y = 200 + (int)(r* Math.sin(2*Math.PI/n*i));
                coord.add(new Point(x,y));
            }
            for(; j < s.length && s[j].length() == 1; j++) {
                ui.addVertex(new GraphicVertex(s[j].charAt(0), coord.get(j)));
            }
        } else {
            j = vrt.size();
            for(GraphicVertex gv: vrt) {
                ui.addVertex(gv);
            }
        }

        for(;j < s.length && s[j].length() > 1; j++) {
            String[] temp = s[j].split(" ");
            ui.addEdge(temp[0].charAt(0), temp[1].charAt(0), Integer.parseInt(temp[2]), Integer.parseInt(temp[3]));
        }

        ui.repaint();
    }

    private void copyGraph() {
        Graph newGraph = new Graph(graph);
        conditions.add(newGraph);
    }

    public int toStart() {
        if (graph == null) {
            return -1;
        }
        if (conditions.isEmpty()) {
            initFordFulkerson();
        }
        currentGraph = 0;
        drawGraph(conditions.get(currentGraph));
        return 0;
    }

    public int toEnd() {
        if (graph == null) {
            return -1;
        }
        if (conditions.isEmpty()) {
            initFordFulkerson();
        }
        currentGraph = conditions.size() - 1;
        drawGraph(conditions.get(currentGraph));
        return conditions.get(currentGraph).getFlow();
    }

    public int stepForward(){
        if (graph == null) {
            return -1;
        }
        if (conditions.isEmpty()) {
            initFordFulkerson();
        }
        if (currentGraph == conditions.size() - 1) {
            return conditions.get(conditions.size() - 1).getFlow();
        }
        currentGraph ++;
        drawGraph(conditions.get(currentGraph));
        return conditions.get(currentGraph).getFlow();
    }

    public int stepBack(){
        if (graph == null) {
            return -1;
        }
        if (conditions.isEmpty()) {
            initFordFulkerson();
        }
        if (currentGraph == 0) {
            return 0;
        }
        --currentGraph;
        drawGraph(conditions.get(currentGraph));
        return conditions.get(currentGraph).getFlow();
    }

    private void initFordFulkerson() {
        int flow;
        do {
            copyGraph();
            flow = FordFulkerson.step(graph);
        } while (flow != 0);
    }
}