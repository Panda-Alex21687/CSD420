package com.baldree.mod4;

/*
Discussion:

The test results showed that the iterator approach was significantly faster than
the get(index) approach for both 50,000 and 500,000 integers. With 50,000
integers, the iterator took about ______ ms, while get(index) took about
______ ms. With 500,000 integers, the iterator took about ______ ms, while
get(index) took about ______ ms.

The main reason is that LinkedList is designed for sequential access, not fast
random access by position. An iterator walks through the list one element at a
time, so it only needs one pass through the structure. However, get(index)
must repeatedly search through the LinkedList to locate each position. Because
of that, every call to get(index) adds extra work, and repeating it in a loop
causes the total time to grow much faster as the list becomes larger.

When the number of stored integers increased from 50,000 to 500,000, the
difference between the two methods became much greater. The iterator remained
relatively efficient, but the get(index) method became extremely slow. This
demonstrates that although both methods produce the same result, iterator
traversal is the better choice for LinkedList because it is more efficient and
scales better with larger collections.
*/

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class LinkedListTraversalBenchmarkTest {

    @Test
    void buildListCreatesCorrectSizeAndValues() {
        LinkedList<Integer> list = LinkedListTraversalBenchmark.buildList(10);

        assertEquals(10, list.size());
        assertEquals(0, list.getFirst());
        assertEquals(9, list.getLast());
    }

    @Test
    void expectedSumIsCorrect() {
        assertEquals(45, LinkedListTraversalBenchmark.expectedSum(10));
        assertEquals(1249975000L, LinkedListTraversalBenchmark.expectedSum(50_000));
    }

    @Test
    void iteratorTraversalReturnsCorrectSum() {
        LinkedList<Integer> list = LinkedListTraversalBenchmark.buildList(10);

        long sum = LinkedListTraversalBenchmark.traverseWithIterator(list);

        assertEquals(45, sum);
    }

    @Test
    void getIndexTraversalReturnsCorrectSum() {
        LinkedList<Integer> list = LinkedListTraversalBenchmark.buildList(10);

        long sum = LinkedListTraversalBenchmark.traverseWithGetIndex(list);

        assertEquals(45, sum);
    }

    @Test
    void bothTraversalMethodsMatchFor50000Items() {
        LinkedList<Integer> list = LinkedListTraversalBenchmark.buildList(50_000);

        long iteratorSum = LinkedListTraversalBenchmark.traverseWithIterator(list);
        long getIndexSum = LinkedListTraversalBenchmark.traverseWithGetIndex(list);
        long expected = LinkedListTraversalBenchmark.expectedSum(50_000);

        assertEquals(expected, iteratorSum);
        assertEquals(expected, getIndexSum);
        assertEquals(iteratorSum, getIndexSum);
    }
}