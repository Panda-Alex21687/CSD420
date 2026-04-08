package com.baldree.mod4;

/*
Results Discussion:

After running the tests, I found that the iterator approach was significantly
faster than the get(index) approach for both 50,000 and 500,000 integers. With
50,000 integers, the iterator took about 1.615 ms, while get(index) took about
933.897 ms. With 500,000 integers, the iterator took about 2.742 ms, while
get(index) took about 98,059.807 ms.

From my results, I can see that LinkedList works much better with sequential
access than with random access by position. When I use an iterator, the list is
traversed one element at a time in order, so it only takes one pass through the
structure. When I use get(index), the program has to keep searching
through the LinkedList to find each position. Because of that, every call to
get(index) adds more work, and using it repeatedly inside a loop causes the
execution time to increase very quickly as the list gets larger.

When I increased the number of stored integers from 50,000 to 500,000, the
difference between the two methods became much more noticeable. The iterator
remained efficient, but the get(index) method became extremely slow. This showed
me that even though both methods produce the same result, iterator traversal is
the better choice for a LinkedList because it is much more efficient and scales
better with larger collections.

In my test, the iterator time only increased from 1.615 ms to 2.742 ms, which
was about 1.70 times slower. In contrast, the get(index) approach increased
from 933.897 ms to 98,059.807 ms, which was about 105.00 times slower. To me,
this clearly shows how inefficient get(index) becomes when the LinkedList grows
larger.

Test Code Results:

My JUnit test code ran successfully. All 5 tests passed with no failures or
errors. The test run completed in about 0.970 seconds, and the total Maven
build completed in about 1.898 seconds. The benchmark program also completed
successfully, and the total execution time for the benchmark run was about
1 minute and 39 seconds.
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