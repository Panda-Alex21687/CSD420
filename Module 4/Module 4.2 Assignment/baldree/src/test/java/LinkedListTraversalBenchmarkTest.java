import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for LinkedListTraversalBenchmark.
 * These tests focus on correctness, not performance.
 */
public class LinkedListTraversalBenchmarkTest {

    @Test
    void buildListCreatesCorrectSizeAndValues() {
        LinkedList<Integer> list = LinkedListTraversalBenchmark.buildList(5);

        assertEquals(5, list.size());
        assertEquals(0, list.getFirst());
        assertEquals(4, list.getLast());
    }

    @Test
    void expectedSumIsCorrect() {
        assertEquals(10, LinkedListTraversalBenchmark.expectedSum(5));
        assertEquals(1249975000L, LinkedListTraversalBenchmark.expectedSum(50_000));
    }

    @Test
    void iteratorTraversalReturnsCorrectSum() {
        LinkedList<Integer> list = LinkedListTraversalBenchmark.buildList(10_000);
        long expected = LinkedListTraversalBenchmark.expectedSum(10_000);

        long actual = LinkedListTraversalBenchmark.traverseWithIterator(list);

        assertEquals(expected, actual);
    }

    @Test
    void getTraversalReturnsCorrectSum() {
        LinkedList<Integer> list = LinkedListTraversalBenchmark.buildList(10_000);
        long expected = LinkedListTraversalBenchmark.expectedSum(10_000);

        long actual = LinkedListTraversalBenchmark.traverseWithGet(list);

        assertEquals(expected, actual);
    }

    @Test
    void bothTraversalMethodsReturnSameSum() {
        LinkedList<Integer> list = LinkedListTraversalBenchmark.buildList(10_000);

        long iteratorSum = LinkedListTraversalBenchmark.traverseWithIterator(list);
        long getSum = LinkedListTraversalBenchmark.traverseWithGet(list);

        assertEquals(iteratorSum, getSum);
    }

    @Test
    void runBenchmarkProducesValidResults() {
        LinkedListTraversalBenchmark.BenchmarkResult result = LinkedListTraversalBenchmark.runBenchmark(1_000);

        assertEquals(1_000, result.getSize());
        assertEquals(result.getExpectedSum(), result.getIteratorSum());
        assertEquals(result.getExpectedSum(), result.getGetSum());
        assertTrue(result.getIteratorTimeNanos() >= 0);
        assertTrue(result.getGetTimeNanos() >= 0);
    }
}