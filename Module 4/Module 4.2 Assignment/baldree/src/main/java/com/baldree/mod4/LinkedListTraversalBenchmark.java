import java.util.Iterator;
import java.util.LinkedList;

/**
 * Assignment:
 * Store integers in a LinkedList and compare traversal time using:
 * 1) an Iterator
 * 2) the get(index) method
 *
 * This program tests both 50,000 and 500,000 integers.
 */

public class LinkedListTraversalBenchmark {

    /**
     * Holds the benchmark results for one list size.
     */
    public static class BenchmarkResult {
        private final int size;
        private final long expectedSum;
        private final long iteratorSum;
        private final long getSum;
        private final long iteratorTimeNanos;
        private final long getTimeNanos;

        public BenchmarkResult(int size, long expectedSum, long iteratorSum, long getSum,
                long iteratorTimeNanos, long getTimeNanos) {
            this.size = size;
            this.expectedSum = expectedSum;
            this.iteratorSum = iteratorSum;
            this.getSum = getSum;
            this.iteratorTimeNanos = iteratorTimeNanos;
            this.getTimeNanos = getTimeNanos;
        }

        public int getSize() {
            return size;
        }

        public long getExpectedSum() {
            return expectedSum;
        }

        public long getIteratorSum() {
            return iteratorSum;
        }

        public long getGetSum() {
            return getSum;
        }

        public long getIteratorTimeNanos() {
            return iteratorTimeNanos;
        }

        public long getGetTimeNanos() {
            return getTimeNanos;
        }
    }

    public static void main(String[] args) {
        warmUp();

        int[] sizes = { 50_000, 500_000 };

        for (int size : sizes) {
            BenchmarkResult result = runBenchmark(size);
            printResult(result);
        }
    }

    /**
     * Creates a LinkedList containing values from 0 up to size - 1.
     *
     * @param size number of integers to store
     * @return populated LinkedList
     */
    public static LinkedList<Integer> buildList(int size) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        return list;
    }

    /**
     * Traverses the LinkedList using an Iterator.
     * A long is used so the sum does not overflow for large list sizes.
     *
     * @param list LinkedList to traverse
     * @return sum of all elements
     */
    public static long traverseWithIterator(LinkedList<Integer> list) {
        long sum = 0;
        Iterator<Integer> iterator = list.iterator();

        while (iterator.hasNext()) {
            sum += iterator.next();
        }

        return sum;
    }

    /**
     * Traverses the LinkedList using get(index).
     * This is intentionally slower for LinkedList and is included for comparison.
     *
     * @param list LinkedList to traverse
     * @return sum of all elements
     */
    public static long traverseWithGet(LinkedList<Integer> list) {
        long sum = 0;

        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i);
        }

        return sum;
    }

    /**
     * Calculates the expected sum of 0 through size - 1.
     *
     * @param size number of elements
     * @return expected sum
     */
    public static long expectedSum(int size) {
        return (long) size * (size - 1) / 2;
    }

    /**
     * Runs the full benchmark for one list size.
     *
     * @param size list size
     * @return benchmark result object
     */
    public static BenchmarkResult runBenchmark(int size) {
        LinkedList<Integer> list = buildList(size);
        long expected = expectedSum(size);

        long startIterator = System.nanoTime();
        long iteratorSum = traverseWithIterator(list);
        long iteratorTime = System.nanoTime() - startIterator;

        long startGet = System.nanoTime();
        long getSum = traverseWithGet(list);
        long getTime = System.nanoTime() - startGet;

        if (iteratorSum != expected || getSum != expected) {
            throw new IllegalStateException("Traversal produced an incorrect result.");
        }

        return new BenchmarkResult(size, expected, iteratorSum, getSum, iteratorTime, getTime);
    }

    /**
     * Small warm-up run to help the JVM optimize code before the real benchmark.
     */
    private static void warmUp() {
        LinkedList<Integer> warmUpList = buildList(1_000);
        traverseWithIterator(warmUpList);
        traverseWithGet(warmUpList);
    }

    /**
     * Prints one formatted benchmark report.
     *
     * @param result benchmark result
     */
    private static void printResult(BenchmarkResult result) {
        double iteratorMs = result.getIteratorTimeNanos() / 1_000_000.0;
        double getMs = result.getGetTimeNanos() / 1_000_000.0;
        double slowerFactor = (result.getIteratorTimeNanos() == 0)
                ? 0.0
                : (double) result.getGetTimeNanos() / result.getIteratorTimeNanos();

        System.out.println("==================================================");
        System.out.println("LinkedList size: " + result.getSize());
        System.out.println("Expected sum: " + result.getExpectedSum());
        System.out.println("Iterator sum: " + result.getIteratorSum());
        System.out.println("get(index) sum: " + result.getGetSum());
        System.out.printf("Iterator traversal time: %.3f ms%n", iteratorMs);
        System.out.printf("get(index) traversal time: %.3f ms%n", getMs);
        System.out.printf("get(index) was %.2f times slower than iterator.%n", slowerFactor);
        System.out.println("==================================================");
        System.out.println();
    }
}