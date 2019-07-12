package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Before;

import model.Graph;
import model.FordFulkerson;

public class AlgorithmTest 
{
    private Graph target;

    @Test
    public void test1() {
		target = new Graph('a', 'd');
    	target.addEdge('a', 'b', 8, 0);
    	target.addEdge('b', 'c', 10, 0);
    	target.addEdge('c', 'd', 10, 0);
    	target.addEdge('h', 'c', 10, 0);
    	target.addEdge('e', 'f', 8, 0);
    	target.addEdge('g', 'h', 11, 0);
    	target.addEdge('b', 'e', 8, 0);
    	target.addEdge('a', 'g', 10, 0);
    	target.addEdge('f', 'd', 8, 0);

        assertEquals(FordFulkerson.process(target), 18);
    }
    @Test
	public void test2() {
		target = new Graph('a', 'e');
		target.addEdge('a', 'b', 20, 0);
		target.addEdge('b', 'a', 20, 0);
		target.addEdge('a', 'd', 10, 0);
		target.addEdge('d', 'a', 10, 0);
		target.addEdge('a', 'c', 30, 0);
		target.addEdge('c', 'a', 30, 0);
		target.addEdge('b', 'c', 40, 0);
		target.addEdge('c', 'b', 40, 0);
		target.addEdge('c', 'd', 10, 0);
		target.addEdge('d', 'c', 10, 0);
		target.addEdge('c', 'e', 20, 0);
		target.addEdge('e', 'c', 20, 0);
		target.addEdge('b', 'e', 30, 0);
		target.addEdge('e', 'b', 30, 0);
		target.addEdge('d', 'e', 10, 0);
		target.addEdge('e', 'd', 10, 0);

		assertEquals(FordFulkerson.process(target), 60);
	}
	@Test
	public void test3() {
		target = new Graph('a', 'c');
		target.addEdge('a', 'b', 7, 0);
		target.addEdge('a', 'c', 6, 0);
		target.addEdge('b', 'c', 4, 0);

		assertEquals(FordFulkerson.process(target), 10);
	}
	@Test
	public void test4() {
		target = new Graph('1', '4');
		target.addEdge('1', '2', 7, 0);
		target.addEdge('1', '3', 6, 0);
		target.addEdge('2', '4', 8, 0);
		target.addEdge('2', '5', 1, 0);
		target.addEdge('3', '5', 2, 0);
		target.addEdge('3', '6', 4, 0);
		target.addEdge('6', '5', 7, 0);
		target.addEdge('5', '4', 6, 0);

		assertEquals(FordFulkerson.process(target), 13);
	}
	@Test
	public void test5() {
		target = new Graph('a', 'f');
		target.addEdge('a', 'b', 7, 0);
		target.addEdge('a', 'c', 6, 0);
		target.addEdge('b', 'd', 6, 0);
		target.addEdge('c', 'f', 9, 0);
		target.addEdge('d', 'e', 3, 0);
		target.addEdge('d', 'f', 4, 0);
		target.addEdge('e', 'c', 2, 0);

		assertEquals(FordFulkerson.process(target), 12);
	}
	@Test
	public void testOneVertex() {
		Graph target = new Graph('a', 'a');
		target.addEdge('a', 'b', 1, 0);
		try {
			FordFulkerson.process(target);
			assertTrue(false);
		}
		catch(Exception ex) {
			assertEquals(ex.getMessage(), "Wrong source or sink.");
		}
	}

}
