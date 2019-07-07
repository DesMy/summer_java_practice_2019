/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author duyenNH
 */
public class ControllerTest {
    
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
    }
    
    @After
    public void tearDown() {
    }

    @org.junit.Test
    public void testInputFile() throws IOException {
        Controller c = new Controller(); 

        // TODO review the generated test code and remove the default call to fail.
        
        c.loadFile("file.txt");
        
        assertEquals(3, c.graph.getVrtx().size());
        //assertEquals(2, c.graph.getEdges().size());
        assertEquals(2, c.graph.getVrtx().get(1).getNeighbours().size());
        assertEquals("A", c.graph.getSource().getName());
        assertEquals("C", c.graph.getSink().getName());
        //fail("The test case is a prototype.");
    }    
      
    @org.junit.Test
    public void testIOFile() throws IOException{
        Controller c = new Controller(); 
        c.graph.addVertex("A");
        c.graph.addVertex("B");
        c.graph.addVertex("C");
        c.graph.addVertex("D");
        c.graph.addVertex("E");
        c.graph.addVertex("F");
        
        c.graph.addEdge(c.graph.getVertexByName("A"), c.graph.getVertexByName("B"), 8);
        c.graph.addEdge(c.graph.getVertexByName("A"), c.graph.getVertexByName("C"), 2);
        c.graph.addEdge(c.graph.getVertexByName("B"), c.graph.getVertexByName("D"), 6);
        c.graph.addEdge(c.graph.getVertexByName("C"), c.graph.getVertexByName("E"), 5);
        c.graph.addEdge(c.graph.getVertexByName("D"), c.graph.getVertexByName("C"), 2);
        c.graph.addEdge(c.graph.getVertexByName("E"), c.graph.getVertexByName("B"), 3);
        c.graph.addEdge(c.graph.getVertexByName("D"), c.graph.getVertexByName("F"), 4);
        c.graph.addEdge(c.graph.getVertexByName("E"), c.graph.getVertexByName("F"), 5);
        
        c.graph.setSource(c.graph.getVertexByName("A"));
        c.graph.setSink(c.graph.getVertexByName("F"));
        
        c.saveFile("test2.txt");
        
        // TODO review the generated test code and remove the default call to fail.
        
        c.loadFile("test2.txt");
        assertEquals(6, c.graph.getVrtx().size());   
        assertEquals(8, c.graph.getEdges().size());
        assertEquals("F", c.graph.getVrtx().get(5).getName());
                
        //fail("The test case is a prototype.");
    }
    @org.junit.Test(timeout=1000)
    public void testProcess1() throws IOException{
        Controller c = new Controller(); 
        
        // TODO review the generated test code and remove the default call to fail.
        c.loadFile("test1.txt");
        assertEquals(4, c.graph.getVrtx().size());   
        assertEquals(6, c.graph.getEdges().size());
        assertEquals("C", c.graph.getVrtx().get(2).getName());
        
        c.process();
        assertEquals(2, c.graph.getTotalFlow()); 
        
        //fail("The test case is a prototype.");
    }
    
    @org.junit.Test(timeout=1000)
    public void testProcess2() throws IOException{
        Controller c = new Controller(); 
        c.graph.addVertex("A");
        c.graph.addVertex("B");
        c.graph.addVertex("C");
        c.graph.addVertex("D");
        c.graph.addVertex("E");
        c.graph.addVertex("F");
        
        c.graph.addEdge(c.graph.getVertexByName("A"), c.graph.getVertexByName("B"), 8);
        c.graph.addEdge(c.graph.getVertexByName("A"), c.graph.getVertexByName("C"), 2);
        c.graph.addEdge(c.graph.getVertexByName("B"), c.graph.getVertexByName("D"), 6);
        c.graph.addEdge(c.graph.getVertexByName("C"), c.graph.getVertexByName("E"), 5);
        c.graph.addEdge(c.graph.getVertexByName("D"), c.graph.getVertexByName("C"), 2);
        c.graph.addEdge(c.graph.getVertexByName("E"), c.graph.getVertexByName("B"), 3);
        c.graph.addEdge(c.graph.getVertexByName("D"), c.graph.getVertexByName("F"), 4);
        c.graph.addEdge(c.graph.getVertexByName("E"), c.graph.getVertexByName("F"), 5);
        
        c.graph.setSource(c.graph.getVertexByName("A"));
        c.graph.setSink(c.graph.getVertexByName("F"));
        
        // TODO review the generated test code and remove the default call to fail.
        
        assertEquals(6, c.graph.getVrtx().size());   
        assertEquals(8, c.graph.getEdges().size());
        assertEquals("F", c.graph.getVrtx().get(5).getName());
        
        c.process();
        assertEquals(8, c.graph.getTotalFlow());
                
        //fail("The test case is a prototype.");
    }
    
}
