package controller;

import model.Edge;
import model.Graph;
import model.Vertex;
import model.FordFulkerson;
import ui.Frame;
import ui.GraphicEdge;
import ui.GraphicVertex;


import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class Controller
{
    private Frame ui;
    private ArrayList<Graph> graphs;
    private int currentGraph;
    private int[][] map;
    private char source;
    private char sink;


    public Controller() {
        ui = new Frame(this);
        ui.setVisible(true);
        graphs = new ArrayList<>();
        currentGraph = -1;
        map = new int[10][10];
    }

    public void initGraph(ArrayList<String> input) {
        Graph graph = new Graph(this);
        source = input.get(0).charAt(0);
        sink = input.get(1).charAt(0);
        input.remove(0);
        input.remove(0);
        for(String str : input) {
            String[] arr = str.split(" ");
            graph.addPair(arr[0].charAt(0), arr[1].charAt(0), Integer.parseInt(arr[2]));
        }
        graphs.add(graph);
        ++currentGraph;
        initMap();
        drawCurrentGraph();
    }

    public void initMap() {
        Graph graph = graphs.get(currentGraph);
        for(int i=0; i<graph.getVertices().size(); ++i) {
            int randx = (int) (Math.random() * 10);
            int randy = (int) (Math.random() * 10);
            while(map[randx][randy] == 1) {
                randx = (int) (Math.random() * 10);
                randy = (int) (Math.random() * 10);
            }
            map[randx][randy] = 1;
        }
    }

    public void drawCurrentGraph() {
        ui.clearPanel();
        Graph graph = graphs.get(currentGraph);
        ArrayList<GraphicVertex> gv = new ArrayList<>();
        ArrayList<GraphicEdge> ge = new ArrayList<>();
        for(Vertex v : graph.getVertices()) {
            int i = 0;
            int j = 0;
            Founded:
            for(i=0; i<10; ++i) {
                for(j=0; j<10; ++j) {
                    if(map[i][j] == 1) {
                        map[i][j] = 2;
                        break Founded;
                    }
                }
            }
            gv.add(new GraphicVertex(v.name(), new Point(95*i+10, 30*j+10)));
            ui.addElement(gv.get(gv.size()-1));
        }
        for(int i=0; i<gv.size(); ++i) {
            for(Edge e : graph.getVertices().get(i).outEdges()) {
                for(GraphicVertex ver : gv) {
                    if(ver.name() == e.dest().name()) {
                        if(e.dest().isEdge(ver.name())) {

                        } else {
                            ge.add(new GraphicEdge(gv.get(i), ver, e.getCapacity(), e.getFlow()));
                            ui.addElement(ge.get(ge.size()-1));
                        }
                        break;
                    }
                }

            }
        }
        ui.repaint();
        Founded:
        for(int i=0; i<10; ++i) {
            for(int j=0; j<10; ++j) {
                if(map[i][j] == 2) {
                    map[i][j] = 1;
                    break Founded;
                }
            }
        }
    }

    public void copyGraph(Graph graph) {
        Graph newGraph = new Graph();
        for(Vertex v : graph.getVertices()) {
            for(Edge e : v.outEdges()) {
                newGraph.addPair(v.name(), e.dest().name(), e.getCapacity()+e.getFlow());
                for(Vertex nv : newGraph.getVertices()) {
                    for(Edge ne : nv.outEdges()) {
                        if(nv.name() == v.name() && ne.dest().name() == e.dest().name()) {
                            ne.changeCapacity(e.getFlow());
                        }
                    }
                }
            }
        }
        graphs.add(newGraph);
    }

    public int stepForward(){
        if(graphs.size() == 1) {
            FordFulkerson.process(graphs.get(0), source, sink);
            ++currentGraph;
        }
        if(currentGraph == graphs.size() - 1) {

            int process = FordFulkerson.process(graphs.get(1), source, sink);
            currentGraph = 1;
            drawCurrentGraph();
            currentGraph = graphs.size() - 1;
            return process;
        }

        ++currentGraph;
        drawCurrentGraph();

        return 0;
    }

    public void stepBack(){
        if (currentGraph <= 1) {
            return;
        }
        --currentGraph;
        drawCurrentGraph();
    }

    public int runFordFulkerson() {
        int process = FordFulkerson.process(graphs.get(currentGraph), source, sink);
        drawCurrentGraph();

        return process;
    }
}