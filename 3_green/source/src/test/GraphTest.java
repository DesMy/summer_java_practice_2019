import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.Before;

import model.Graph;
import model.FordFulkerson;

public class GraphTest 
{
    Graph target;

    @Before
    public void setUp() {
        target = new Graph();
    }

    @Test
    public void test1() {
    	char src = 'a';
    	char snk = 'd';
    	target.addPair('a', 'b', 8);
    	target.addPair('b', 'c', 10);
    	target.addPair('c', 'd', 10);
    	target.addPair('h', 'c', 10);
    	target.addPair('e', 'f', 8);
    	target.addPair('g', 'h', 11);
    	target.addPair('b', 'e', 8);
    	target.addPair('a', 'g', 10);
    	target.addPair('f', 'd', 8);

        assertEquals(FordFulkerson.process(target, src, snk), 18);
    }
    @Test
	public void test2() {
		char src = 'a';
		char snk = 'e';
		target.addPair('a', 'b', 20);
		target.addPair('b', 'a', 20);
		target.addPair('a', 'd', 10);
		target.addPair('d', 'a', 10);
		target.addPair('a', 'c', 30);
		target.addPair('c', 'a', 30);
		target.addPair('b', 'c', 40);
		target.addPair('c', 'b', 40);
		target.addPair('c', 'd', 10);
		target.addPair('d', 'c', 10);
		target.addPair('c', 'e', 20);
		target.addPair('e', 'c', 20);
		target.addPair('b', 'e', 30);
		target.addPair('e', 'b', 30);
		target.addPair('d', 'e', 10);
		target.addPair('e', 'd', 10);

		assertEquals(FordFulkerson.process(target, src, snk), 60);
	}
	@Test
	public void test3() {
		char src = 'a';
		char snk = 'c';
		target.addPair('a', 'b', 7);
		target.addPair('a', 'c', 6);
		target.addPair('b', 'c', 4);

		assertEquals(FordFulkerson.process(target, src, snk), 10);
	}
	@Test
	public void test4() {
		char src = '1';
		char snk = '4';
		target.addPair('1', '2', 7);
		target.addPair('1', '3', 6);
		target.addPair('2', '4', 8);
		target.addPair('2', '5', 1);
		target.addPair('3', '5', 2);
		target.addPair('3', '6', 4);
		target.addPair('6', '5', 7);
		target.addPair('5', '4', 6);

		assertEquals(FordFulkerson.process(target, src, snk), 13);
	}
	@Test
	public void test5() {
		char src = 'a';
		char snk = 'f';
		target.addPair('a', 'b', 7);
		target.addPair('a', 'c', 6);
		target.addPair('b', 'd', 6);
		target.addPair('c', 'f', 9);
		target.addPair('d', 'e', 3);
		target.addPair('d', 'f', 4);
		target.addPair('e', 'c', 2);

		assertEquals(FordFulkerson.process(target, src, snk), 12);
	}

}
