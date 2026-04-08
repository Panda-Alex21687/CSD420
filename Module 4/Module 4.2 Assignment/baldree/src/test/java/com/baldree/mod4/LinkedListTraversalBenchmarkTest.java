package com.baldree.mod4;

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