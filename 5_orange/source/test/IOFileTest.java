/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import controller.Controller;
import java.io.IOException;
import model.Graph;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

/**
 *
 * @author duyenNH
 */
public class IOFileTest {
    
    private Graph graph;
    
    public IOFileTest() {
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
    public void testIOFile() throws IOException, Exception {
        Controller c = new Controller();
        c.graph = graph;

        c.saveFile("test2.txt");

        // TODO review the generated test code and remove the default call to fail.
        c.loadFile("test2.txt");
        assertEquals(6, c.graph.getVrtx().size());
        assertEquals(8, c.graph.getEdges().size());
        assertEquals("F", c.graph.getVrtx().get(5).getName());
    }
}
