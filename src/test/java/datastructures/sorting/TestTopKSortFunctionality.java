package datastructures.sorting;

import misc.BaseTest;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;
import misc.Searcher;

import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestTopKSortFunctionality extends BaseTest {
    @Test(timeout=SECOND)
    public void testSimpleUsage() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i);
        }

        IList<Integer> top = Searcher.topKSort(5, list);
        assertEquals(5, top.size());
        for (int i = 0; i < top.size(); i++) {
            assertEquals(15 + i, top.get(i));
        }
    }
    
    @Test(timeout=SECOND)
    public void testRandom() {
        IList<Integer> list = new DoubleLinkedList<>();
        list.add(2);
        list.add(7);
        list.add(1);
        list.add(6);
        list.add(9);
        list.add(5);
        IList<Integer> top = Searcher.topKSort(3, list);
        assertEquals(3, top.size());
        assertEquals(6, top.get(0));
        assertEquals(7, top.get(1));
        assertEquals(9, top.get(2));
    }
    
    @Test(timeout=SECOND)
    public void testDuplicates() {
        IList<Integer> list = new DoubleLinkedList<>();
        for (int i = 0; i < 3; i++) {
            list.add(9);
            list.add(6);
            list.add(0);
        }
        IList<Integer> top = Searcher.topKSort(4, list);
        assertEquals(6, top.get(0));
        assertEquals(9, top.get(1));
        assertEquals(9, top.get(2));
        assertEquals(9, top.get(3));
    }
    
    @Test(timeout=SECOND)
    public void testLargeK() {
        IList<Integer> list = new DoubleLinkedList<>();
        list.add(5);
        list.add(2);
        list.add(7);
        IList<Integer> top = Searcher.topKSort(5, list);
        assertEquals(2, top.get(0));
        assertEquals(5, top.get(1));
        assertEquals(7, top.get(2));
    }
    
    @Test(timeout=SECOND)
    public void testException() {
        IList<Integer> list = new DoubleLinkedList<>();
        list.add(1);
        try {
            IList<Integer> top = Searcher.topKSort(-4, list);
            // We didn't throw an exception? Fail now.
            fail("Expected IllegalArgumentException");
            assertEquals(1, top.get(0));
        } catch (IllegalArgumentException ex) {
            // Do nothing: this is ok
        }
    }  
    
    @Test(timeout=SECOND)
    public void testEmpty() {
        IList<Integer> list = new DoubleLinkedList<>();
        IList<Integer> top = Searcher.topKSort(0, list);
        assertEquals(0, top.size());
    }
   
}
