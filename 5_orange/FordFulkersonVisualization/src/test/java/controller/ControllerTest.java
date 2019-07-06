/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import model.BFS;
import model.DFS;
import model.FordFulkerson;
import model.Graph;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import ui.VertexNotFoundException;

/**
 *
 * @author duyenNH
 */
public class ControllerTest {
    private Graph graph;
    
    public ControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
       graph = new Graph();
        
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("F");
        
        graph.addEdge(graph.getVertexByName("A"), graph.getVertexByName("B"), 8);
        graph.addEdge(graph.getVertexByName("A"), graph.getVertexByName("C"), 2);
        graph.addEdge(graph.getVertexByName("B"), graph.getVertexByName("D"), 6);
        graph.addEdge(graph.getVertexByName("C"), graph.getVertexByName("E"), 5);
        graph.addEdge(graph.getVertexByName("D"), graph.getVertexByName("C"), 2);
        graph.addEdge(graph.getVertexByName("E"), graph.getVertexByName("B"), 3);
        graph.addEdge(graph.getVertexByName("D"), graph.getVertexByName("F"), 4);
        graph.addEdge(graph.getVertexByName("E"), graph.getVertexByName("F"), 5);
        
        graph.setSource(graph.getVertexByName("A"));
        graph.setSink(graph.getVertexByName("F"));       
    }
    
    @After
    public void tearDown() {
    }

    @org.junit.Test
    public void testAddVertex(){
        assertEquals(6, graph.getVrtx().size());
        
        graph.addVertex("G");
        
        assertEquals(7, graph.getVrtx().size());
        assertEquals("G", graph.getVrtx().get(6).getName());
    }
    
    @org.junit.Test
    public void testAddEdges(){
        assertEquals(8, graph.getEdges().size());
        
        graph.addEdge(graph.getVertexByName("C"), graph.getVertexByName("F"), 3);
                
        assertEquals(4, graph.getVrtx().get(2).getNeighbours().size());
        assertEquals(9, graph.getEdges().size());
    }
    @org.junit.Test
    public void testVertexNotFoundException() throws VertexNotFoundException{
        boolean thrown = false;

        try {
          graph.deleteVertex(graph.getVertexByName("S"));
        } catch (VertexNotFoundException e) {
          thrown = true;
        }
        
        assertTrue(thrown); 
    }
    
    @org.junit.Test
    public void testDeleteVertex() throws VertexNotFoundException{
        graph.deleteVertex(graph.getVertexByName("C"));
        assertEquals(5, graph.getVrtx().size());
    }
    
    @org.junit.Test
    public void testDeleteEdge() throws VertexNotFoundException{
        graph.deleteEdge(graph.getVertexByName("B"), graph.getVertexByName("D"));
        assertEquals(7, graph.getEdges().size());        
    }
    
    @org.junit.Test
    public void testInputFile() throws IOException {
        Controller c = new Controller();

        // TODO review the generated test code and remove the default call to fail.
        
        c.loadFile("file.txt");
        
        assertEquals(3, c.graph.getVrtx().size());
        assertEquals(2, c.graph.getEdges().size());
        assertEquals(2, c.graph.getVrtx().get(1).getNeighbours().size());
        assertEquals("A", c.graph.getSource().getName());
        assertEquals("C", c.graph.getSink().getName());
    }    
      
    @org.junit.Test
    public void testIOFile() throws IOException{
        Controller c = new Controller(); 
        c.graph = graph;
        
        c.saveFile("test2.txt");
        
        // TODO review the generated test code and remove the default call to fail.
        
        c.loadFile("test2.txt");
        assertEquals(6, c.graph.getVrtx().size());   
        assertEquals(8, c.graph.getEdges().size());
        assertEquals("F", c.graph.getVrtx().get(5).getName());
    }
    
    @org.junit.Test
    public void testProcess(){        
        LinkedList<Graph> result = FordFulkerson.process(graph, new DFS());
        assertEquals(8, graph.getTotalFlow());
    }
}
