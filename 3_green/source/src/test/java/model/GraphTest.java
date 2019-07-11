package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Before;

import model.Graph;
import model.BFS;
import model.Vertex;
import model.Edge;

public class GraphTest 
{
    private Graph target;

	@Before
	public void setUp() {
		target = new Graph('a', 'c');
	}

    public void setNumericGraph() {
        target.addEdge('1', '2', 7, 0);
        target.addEdge('1', '3', 6, 0);
        target.addEdge('2', '4', 8, 0);
        target.addEdge('2', '5', 1, 0);
        target.addEdge('3', '5', 2, 0);
        target.addEdge('3', '6', 4, 0);
        target.addEdge('6', '5', 7, 0);
        target.addEdge('5', '4', 6, 0);
    }

	@Test
	public void testPathes() {
		target.addEdge('a', 'b', 8, 0);
    	target.addEdge('b', 'c', 10, 0);
    	target.addEdge('c', 'd', 10, 0);
    	target.addEdge('h', 'c', 10, 0);
    	target.addEdge('e', 'f', 8, 0);
    	target.addEdge('g', 'h', 11, 0);
    	target.addEdge('b', 'e', 8, 0);
    	target.addEdge('a', 'g', 10, 0);
    	target.addEdge('f', 'd', 8, 0);

        //From a to any:
    	assertEquals(BFS.findPath(target.findVertex('a'), target.findVertex('b')), "ab");
        assertEquals(BFS.findPath(target.findVertex('a'), target.findVertex('c')), "abc");
        assertEquals(BFS.findPath(target.findVertex('a'), target.findVertex('g')), "ag");
        assertEquals(BFS.findPath(target.findVertex('a'), target.findVertex('h')), "agh");
        assertEquals(BFS.findPath(target.findVertex('a'), target.findVertex('d')), "abcd");
        assertEquals(BFS.findPath(target.findVertex('a'), target.findVertex('e')), "abe");
        assertEquals(BFS.findPath(target.findVertex('a'), target.findVertex('f')), "abef");
        //From e to ...:
        assertEquals(BFS.findPath(target.findVertex('e'), target.findVertex('f')), "ef");
        assertEquals(BFS.findPath(target.findVertex('e'), target.findVertex('d')), "efd");
        //Null pathes:
        assertEquals(BFS.findPath(target.findVertex('f'), target.findVertex('b')), null);
        assertEquals(BFS.findPath(target.findVertex('e'), target.findVertex('c')), null);
	}
    @Test
    public void testVertices() {
        target.addEdge('a', 'b', 7, 0);
        target.addEdge('a', 'c', 6,0);
        target.addEdge('b', 'c', 4, 0);

        assertEquals(target.findVertex('a').name(), 'a');
        assertEquals(target.findVertex('b').name(), 'b');
        assertEquals(target.findVertex('c').name(), 'c');
        //Null vertex:
        assertEquals(target.findVertex('d'), null);
    }
    @Test
    public void testLoop() {
        try {
            target.addEdge('a', 'a', 3, 0);
            assertTrue(false);
        }
        catch(Exception ex) {
            assertEquals(ex.getMessage(), "Loop is not allowed.");
        }

    }
    @Test
    public void testEdgesNumber() {
        setNumericGraph();

        assertEquals(target.findVertex('1').outEdges().size(), 2);
        assertEquals(target.findVertex('2').outEdges().size(), 2);
        assertEquals(target.findVertex('3').outEdges().size(), 2);
        assertEquals(target.findVertex('4').outEdges().size(), 0);
        assertEquals(target.findVertex('5').outEdges().size(), 1);
        assertEquals(target.findVertex('6').outEdges().size(), 1);
    }
    @Test
    public void testEdgesCapacity() {
        setNumericGraph();

        assertEquals(target.findVertex('3').outEdges().get(0).getCapacity(), 2);
        assertEquals(target.findVertex('3').outEdges().get(1).getCapacity(), 4);
    }
    @Test
    public void testEdges() {
        setNumericGraph();

        assertEquals(target.isEdge('1','3'), true);
        assertEquals(target.isEdge('1','2'), true);
        assertEquals(target.isEdge('1', '5'), false);
    }
    @Test
    public void testEdgesNegative() {
        setNumericGraph();

        try {
            target.addEdge('3', '2', -3, 0);
            assertTrue(false);
        }
        catch(Exception ex) {
            assertEquals(ex.getMessage(), "Negative weight of edge.");
        }
    }
    @Test
    public void testEdgeAddExist() {
        setNumericGraph();

        target.addEdge('3', '5', 20, 0);
        assertEquals(target.findVertex('3').outEdges().size(), 2);
        assertEquals(target.findVertex('3').outEdges().get(0).getCapacity(), 2);
    }
}