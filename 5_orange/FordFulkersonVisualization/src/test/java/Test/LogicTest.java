/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import controller.Controller;
import java.io.IOException;
import model.BFS;
import model.DFS;
import model.FordFulkerson;
import model.Graph;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import ui.VertexNotFoundException;

/**
 *
 * @author duyenNH
 */
public class LogicTest {
    private Graph graph;
    
    public LogicTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
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
        assertEquals(0, graph.getVrtx().get(6).getNeighbours().size());
    }    
    
    @org.junit.Test(expected = Exception.class)
    public void testAddVrtxExists() throws Exception{
        graph.addVertex("A");
    }    
    
    @org.junit.Test
    public void testAddEdges() throws Exception{
        assertEquals(8, graph.getEdges().size());
        
        graph.addEdge(graph.getVertexByName("C"), graph.getVertexByName("F"), 3); 
        
        assertEquals(4, graph.getVrtx().get(2).getNeighbours().size()); //vertex C
        assertEquals(9, graph.getEdges().size());
    }
    
    @org.junit.Test(expected = Exception.class)
    public void testAddEdgesExists() throws Exception{
        graph.addEdge(graph.getVertexByName("C"), graph.getVertexByName("E"), 4);
    }
    
    @org.junit.Test(expected = NullPointerException.class)
    public void testAddEdgesWithoutStrartVrtx() throws Exception{
        graph.addEdge(graph.getVertexByName("Z"), graph.getVertexByName("B"), 9);
    }
    
    @org.junit.Test(expected = NullPointerException.class)
    public void testAddEdgesWithoutEndVrtx() throws Exception{
        graph.addEdge(graph.getVertexByName("C"), graph.getVertexByName("I"), 7);
    }
        
    @org.junit.Test (expected = VertexNotFoundException.class)
    public void testDelVertexNotFoundException() throws VertexNotFoundException{
          graph.deleteVertex(graph.getVertexByName("S"));
    }
    
    @org.junit.Test
    public void testDelVertex() throws VertexNotFoundException{        
        assertNotNull(graph.getVertexByName("C"));
        
        graph.deleteVertex(graph.getVertexByName("C"));
        
        assertEquals(5, graph.getVrtx().size());
        assertNull(graph.getVertexByName("C"));
    }
    
    @org.junit.Test
    public void testDelVertexSource() throws VertexNotFoundException{
        graph.deleteVertex(graph.getSource());
        assertNull(graph.getSource());        
    }    
    
    @org.junit.Test
    public void testDelVertexSink() throws VertexNotFoundException{
        graph.deleteVertex(graph.getSink());
        assertNull(graph.getSink());
    }
    
    @org.junit.Test
    public void testDeleteEdge() throws VertexNotFoundException{
        assertEquals(3, graph.getVrtx().get(1).getNeighbours().size());//Vertex B
        
        graph.deleteEdge(graph.getVertexByName("B"), graph.getVertexByName("D"));
        
        assertEquals(2, graph.getVrtx().get(3).getNeighbours().size());//Vertex B
        assertEquals(7, graph.getEdges().size()); 
        assertNotNull(graph.getVertexByName("B"));
        assertNotNull(graph.getVertexByName("D"));
    }
    
    @org.junit.Test
    public void testInputFile() throws IOException {
        Controller c = new Controller();
        
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
        
    @org.junit.Test(expected = NullPointerException.class)
    public void testProcessWithoutSource() throws VertexNotFoundException{
        graph.deleteVertex(graph.getSource());
        FordFulkerson.process(graph, new DFS());
    }
    
    @org.junit.Test
    public void testProcessWithoutSink() throws VertexNotFoundException{
        graph.deleteVertex(graph.getSink());
        FordFulkerson.process(graph, new DFS());
        assertEquals(0, graph.getTotalFlow());
    }
    
    @org.junit.Test
    public void testProcessFFWithBFS(){        
        FordFulkerson.process(graph, new BFS());
        assertEquals(8, graph.getTotalFlow());
    }
    
    @org.junit.Test
    public void testProcessFFWithDFS(){        
        FordFulkerson.process(graph, new DFS());
        assertEquals(8, graph.getTotalFlow());
    }
}
