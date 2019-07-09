/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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

    @org.junit.Test(expected = Exception.class)
    public void testProcessWithoutSource() throws VertexNotFoundException, Exception {
        graph.deleteVertex(graph.getSource());
        FordFulkerson.process(graph, new DFS());
    }

    @org.junit.Test(expected = Exception.class)
    public void testProcessWithoutSink() throws VertexNotFoundException, Exception {
        graph.deleteVertex(graph.getSink());
        FordFulkerson.process(graph, new DFS());
        assertEquals(0, graph.getTotalFlow());
    }

    @org.junit.Test
    public void testProcessFFWithBFS() throws Exception {
        FordFulkerson.process(graph, new BFS());
        assertEquals(8, graph.getTotalFlow());
    }

    @org.junit.Test
    public void testProcessFFWithDFS() throws Exception {
        FordFulkerson.process(graph, new DFS());
        assertEquals(8, graph.getTotalFlow());
    }
    
    @org.junit.Test
    public void testProcess() throws Exception{
        graph = new Graph();
        for(int i = 0; i < 1000; i++){
            graph.addVertex(Integer.toString(i));
        }
        for(int i = 5; i<1000; i++){
            for(int j = 1; j < 5; j++){
                graph.addEdge(graph.getVertexByName(Integer.toString(i)), 
                        graph.getVertexByName(Integer.toString(j)), i);
            }
        } 
        graph.setSource(graph.getVertexByName("1"));
        graph.setSink(graph.getVertexByName("999"));
        FordFulkerson.process(graph, new DFS());
    }
}
