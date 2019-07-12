/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
public class VertexTest {
    
    private Graph graph;
    
    public VertexTest() {
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
    public void testAddVertex() throws Exception {
        assertEquals(6, graph.getVrtx().size());

        graph.addVertex("G");

        assertEquals(7, graph.getVrtx().size());
        assertEquals("G", graph.getVrtx().get(6).getName());
        assertEquals(0, graph.getVrtx().get(6).getNeighbours().size());
    }   

    @org.junit.Test(expected = Exception.class)
    public void testAddVertexExists() throws Exception {
        graph.addVertex("A");
    }

    @org.junit.Test(expected = VertexNotFoundException.class)
    public void testDelVertexNotExists() throws VertexNotFoundException {
        graph.deleteVertex(graph.getVertexByName("S"));
    }

    @org.junit.Test
    public void testDelVertex() throws VertexNotFoundException {
        assertNotNull(graph.getVertexByName("C"));

        graph.deleteVertex(graph.getVertexByName("C"));

        assertEquals(5, graph.getVrtx().size());
        assertNull(graph.getVertexByName("C"));
    }

    @org.junit.Test
    public void testDelVertexSource() throws VertexNotFoundException {
        graph.deleteVertex(graph.getSource());
        assertNull(graph.getSource());
    }

    @org.junit.Test
    public void testDelVertexSink() throws VertexNotFoundException {
        graph.deleteVertex(graph.getSink());
        assertNull(graph.getSink());
    }

}
