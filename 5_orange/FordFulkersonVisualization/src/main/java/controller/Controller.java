/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import model.BFS;
import model.DFS;
import model.DeleteVertexActionResult;
import model.Edge;
import model.FordFulkerson;
import model.Graph;
import model.Vertex;
import ui.VertexNotFoundException;

/**
 *
 * @author duyenNH
 */
public class Controller {
    
    public Graph graph;

    private LinkedList<Graph> result = null;

    public Controller() {
        this.graph = new Graph();
    }

    public Edge addEdge(String start, String end, int capacity) throws VertexNotFoundException {
        Vertex v1 = graph.getVertexByName(start);
        Vertex v2 = graph.getVertexByName(end);
        if(v1==null || v2==null) throw new VertexNotFoundException();
        return graph.addEdge(v1,v2, capacity);
    }

    public Vertex addVertex(String newVertex) {
        return graph.addVertex(newVertex);
    }
    
    public void setSource(String name){
        graph.setSource(graph.getVertexByName(name));
    }
    
    public void setSink(String name){
        graph.setSink(graph.getVertexByName(name));
    }
    
    public DeleteVertexActionResult deleteVertex(String v) throws VertexNotFoundException{
        return graph.deleteVertex(graph.getVertexByName(v));
        
    }
    
    public void deleteEdge(String start, String end) throws VertexNotFoundException{
        // TODO;
        Vertex v1 = graph.getVertexByName(start);
        Vertex v2 = graph.getVertexByName(end);
    
        graph.deleteEdge(v1, v2);
    }

    public void process() {
        result = FordFulkerson.process(graph, new DFS());
    }
    
    public int getNumberOfStep(){
        if(result == null || result.size()==0) return 0;
        return result.size();
    }
    public Graph getStep(int typeStep) {
        if (typeStep <= result.size() && typeStep > 0) {
            return result.get(typeStep-1);
        } else {
            return null;
        }
    }

    public void saveFile(String fileName) throws IOException {
        FileWriter fw = new FileWriter(fileName);
        fw.write(graph.getVrtx().size() + "\n");
        for (Vertex v : graph.getVrtx()) {
            fw.write(v.getName() + "\n");
        }
        fw.write(graph.getEdges().size() + "\n");
        for (Edge e : graph.getEdges()) {
            fw.write(e.getStart().getName() + " " + e.getEnd().getName() + " " + e.getCapacity() + "\n");
        }
        if (graph.getSource() != null) {
            fw.write(graph.getSource().getName());
        } else {
            fw.write('#');
        }
        fw.write('\n');
        if (graph.getSink() != null) {
            fw.write(graph.getSink().getName());
        } else {
            fw.write('#');
        }
        fw.close();
    }

    public void loadFile(String fileName) throws FileNotFoundException, IOException {

        FileReader fr = new FileReader(fileName);
        Scanner sc = new Scanner(fr);        
        graph = new Graph();
        
        int V = sc.nextInt();
        for (int i = 0; i < V; i++) {
            graph.addVertex(sc.next());
        }
        int E = sc.nextInt();
        for (int i = 0; i < E; i++) {
            Vertex start = graph.getVertexByName(sc.next());
            Vertex end = graph.getVertexByName(sc.next());
            int capacity = sc.nextInt();
            graph.addEdge(start, end, capacity);
        }
        String srcName = sc.next();
        if (!srcName.equals("#")) {
            //Vertex src = graph.getVertexByName(srcName);
            graph.setSource(graph.getVertexByName(srcName));
        }
        String sinkName = sc.next();
        if (!sinkName.equals("#")) {
            graph.setSink(graph.getVertexByName(sinkName));
        }
        fr.close();
    }
}
