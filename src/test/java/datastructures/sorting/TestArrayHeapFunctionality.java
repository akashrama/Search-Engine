package datastructures.sorting;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import misc.BaseTest;
import misc.exceptions.EmptyContainerException;
import datastructures.concrete.ArrayHeap;
import datastructures.interfaces.IPriorityQueue;
import org.junit.Test;

/**
 * See spec for details on what kinds of tests this class should include.
 */
public class TestArrayHeapFunctionality extends BaseTest {
    protected <T extends Comparable<T>> IPriorityQueue<T> makeInstance() {
        return new ArrayHeap<>();
    }

    @Test(timeout=SECOND)
    public void testBasicSize() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        assertEquals(1, heap.size());
        assertTrue(!heap.isEmpty());
    }
    
    @Test(timeout=SECOND)
    public void testInsertAndArray() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        int[] copy = {1, 2, 3};
        heap.insert(3);
        heap.insert(2);
        heap.insert(1);
        for (int i = 0; i < copy.length; i++) {
        assertEquals(copy[i], heap.removeMin());   
        }
    }
    
    @Test(timeout=SECOND)
    public void testSizeWhen0() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(1);
        heap.insert(2);
        heap.removeMin();
        assertEquals(1, heap.size());
        heap.removeMin();
        assertEquals(0, heap.size());
    }
    
    @Test(timeout=SECOND)
    public void testSize() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 50; i > 0; i--) {
            heap.insert(i);
        }
        for (int i = 50; i > 0; i--) {
            heap.removeMin();
            assertEquals(i - 1, heap.size());
        }
    }
    
    @Test(timeout=SECOND)
    public void testRemoveMin() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        heap.insert(2);
        heap.insert(1);
        assertEquals(1, heap.removeMin());
        assertEquals(2, heap.removeMin());
        assertEquals(3, heap.removeMin());
    }
    
    @Test(timeout=SECOND)
    public void testPeakMin() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        heap.insert(3);
        heap.insert(2);
        heap.insert(1);
        assertEquals(1, heap.peekMin());
        assertEquals(1, heap.removeMin());
        assertEquals(2, heap.peekMin());
        assertEquals(2, heap.removeMin());
        assertEquals(3, heap.peekMin());
        assertEquals(3, heap.removeMin());
    }
    
    @Test(timeout=SECOND)
    public void testBasicRemove() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 0; i < 5; i++) {
                heap.insert(2*i);
        }
        assertEquals(0, heap.removeMin());
        assertEquals(2, heap.removeMin());
        int[] copy = {4, 6, 8};
        assertEquals(4, heap.peekMin());
        assertEquals(3, heap.size());
        assertTrue(copy.length == heap.size());
    }
    
    @Test(timeout=SECOND)
    public void testDuplicateInsertandRemove() {
            int[] copy = {1, 2, 3, 3, 7, 7, 7, 7, 10};
            IPriorityQueue<Integer> heap = this.makeInstance();
            heap.insert(1);
            heap.insert(2);
            heap.insert(10);
            assertEquals(1, heap.peekMin());
            heap.insert(3);
            heap.insert(3);
            for (int i = 0; i< 4; i++) {
                heap.insert(7);
            }
            for (int i = 0; i < 6; i++) {
                assertEquals(copy[i], heap.removeMin());
            }
    }
    
    @Test(timeout=SECOND)
    public void testOne() {
            IPriorityQueue<Integer> heap = this.makeInstance();
            heap.insert(32);
            assertTrue(heap.removeMin() == 32);
            assertTrue(0 == heap.size());
    }
    
    @Test(timeout=SECOND)
    public void testManyDuplicateInsertandRemove() {
            IPriorityQueue<Integer> heap = this.makeInstance();
            for (int i = 0; i < 20; i++) {
                heap.insert(4);
                heap.insert(6);
                if (i%3 == 0) {
                    heap.insert(3);
                }
                heap.insert(7);
            }
            heap.insert(1);
            assertEquals(68, heap.size());
            for (int i = 0; i < 68; i++) {
                int number = heap.removeMin();
                   if (i == 0) {
                       assertEquals(1, number);
                   } else if (i <= 7) {
                       assertEquals(3, number);
                   } else if (i <= 27) {
                       assertEquals(4, number);
                   } else if (i <= 47) {
                       assertEquals(6, number);
                   } else {
                       assertEquals(7, number);
                   }
           }
    }
    
    @Test(timeout=SECOND)
    public void testException() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        try {
            heap.peekMin();
            // We didn't throw an exception? Fail now.
            fail("Expected EmptyContainerException");
        } catch (EmptyContainerException ex) {
            // Do nothing: this is ok
        }
        heap.insert(1);
        heap.removeMin();
        try {
            heap.removeMin();
            // We didn't throw an exception? Fail now.
            fail("Expected EmptyContainerException");
        } catch (EmptyContainerException ex) {
            // Do nothing: this is ok
        }
        try {
            heap.insert(null);
            // We didn't throw an exception? Fail now.
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            // Do nothing: this is ok
        }
    }
    
    @Test(timeout=SECOND)
    public void letters() {
        IPriorityQueue<String> heap = this.makeInstance();
        heap.insert("a");
        heap.insert("e");
        heap.insert("i");
        heap.insert("o");
        heap.insert("u");  
        assertEquals("a", heap.removeMin());
        assertEquals("e", heap.removeMin());
        assertEquals("i", heap.removeMin());
        assertEquals("o", heap.removeMin());
        assertEquals("u", heap.removeMin());      
    }
    
    
    public void testRemoveMultipleNegative() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        int i = -100;
        while (i < 0) {
                i++;
                heap.insert(i);
                if (i % 2 == 0) {
                    heap.insert(Math.abs(i));
                }
        }
        for (int j = 0; j < 50; j++) {
            heap.removeMin();
        }
        assertEquals(-49, heap.peekMin());
        assertEquals(100, heap.size());
    }
    
    @Test(timeout=SECOND)
    public void testBig() {
        IPriorityQueue<Integer> heap = this.makeInstance();
        for (int i = 100; i > 50; i--) {
            heap.insert(i);
        }
        for (int i = 0; i < 51; i++) {
            heap.insert(i);
        }
        assertEquals(0, heap.removeMin());
        assertEquals(100, heap.size());
        for (int i = 1; i < 62; i++) {
           heap.removeMin();
        }
        assertEquals(62, heap.removeMin());
        for (int i = 62; i < 99; i++) {
            heap.removeMin();
        }
        assertEquals(100, heap.removeMin());
    }
}
