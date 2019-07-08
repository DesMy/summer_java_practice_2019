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
public class EdgesTest {
    
    private Graph graph;
    
    public EdgesTest() {
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
    public void testAddEdges() throws Exception {
        assertEquals(8, graph.getEdges().size());

        graph.addEdge(graph.getVertexByName("C"), graph.getVertexByName("F"), 3);

        assertEquals(4, graph.getVrtx().get(2).getNeighbours().size()); //vertex C
        assertEquals(9, graph.getEdges().size());
    }

    @org.junit.Test(expected = Exception.class)
    public void testAddEdgesExists() throws Exception {
        graph.addEdge(graph.getVertexByName("C"), graph.getVertexByName("E"), 4);
    }

    @org.junit.Test(expected = NullPointerException.class)
    public void testAddEdgesWithoutStrartVrtx() throws Exception {
        graph.addEdge(graph.getVertexByName("Z"), graph.getVertexByName("B"), 9);
    }

    @org.junit.Test(expected = NullPointerException.class)
    public void testAddEdgesWithoutEndVrtx() throws Exception {
        graph.addEdge(graph.getVertexByName("C"), graph.getVertexByName("I"), 7);
    }    
    
    @org.junit.Test
    public void testDelEdge() throws VertexNotFoundException {
        assertEquals(3, graph.getVrtx().get(1).getNeighbours().size());//Vertex B

        graph.deleteEdge(graph.getVertexByName("B"), graph.getVertexByName("D"));

        assertEquals(2, graph.getVrtx().get(3).getNeighbours().size());//Vertex B
        assertEquals(7, graph.getEdges().size());
        assertNotNull(graph.getVertexByName("B"));
        assertNotNull(graph.getVertexByName("D"));
    }
    
    @org.junit.Test(expected = VertexNotFoundException.class)
    public void testDelEdgeNotExists() throws VertexNotFoundException{
        graph.deleteEdge(graph.getVertexByName("B"), graph.getVertexByName("Z"));
    }

}
