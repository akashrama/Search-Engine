package datastructures.sorting;

import misc.BaseTest;
import misc.Searcher;

import org.junit.Test;

import datastructures.concrete.ArrayHeap;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import datastructures.interfaces.IPriorityQueue;

import static org.junit.Assert.assertTrue;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestSortingStress extends BaseTest {
    
    @Test(timeout=10*SECOND)
    public void testHeap() {
        IPriorityQueue<Integer> heap = new ArrayHeap<>();
        for (int i = 0; i < 20000000; i++) {
            heap.insert(i);
            assertEquals(i + 1, heap.size());
        }
        for (int k = 0; k < heap.size(); k++) {
            heap.peekMin();
            heap.removeMin();
        }
        assertTrue(true);
    }
    
    @Test(timeout=10*SECOND)
    public void testTopK() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20000000; i++) {
            list.add(i);
        }
        IList<Integer> top = Searcher.topKSort(5, list);
        assertEquals(19999995, top.get(0));
        assertEquals(19999996, top.get(1));
        assertEquals(19999997, top.get(2));
        assertEquals(19999998, top.get(3));
        assertEquals(19999999, top.get(4));
        assertTrue(true);
    }  
}
