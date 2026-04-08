package com.baldree.mod4;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Assignment:
 * Stores integers in a LinkedList and compares traversal time using
 * an Iterator versus get(index).
 *
 * Discussion:
 * The iterator approach is much faster for LinkedList traversal because it
 * moves through the list one node at a time in sequence. The get(index)
 * approach is slower because each call must search through the LinkedList
 * to find the requested position. When get(index) is used repeatedly in a
 * loop, the total time grows much more quickly, especially with large lists.
 */
public class LinkedListTraversalBenchmark {

    public static void main(String[] args) {
        System.out.println("Starting benchmark...");
        warmUpJvm();

        System.out.println("\nTesting 50,000 integers...");
        BenchmarkResult result50k = benchmark(50_000);

        System.out.println("\nTesting 500,000 integers...");
        BenchmarkResult result500k = benchmark(500_000);

        printComparison(result50k, result500k);
    }

    /**
     * Builds a LinkedList containing values from 0 to size - 1.
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
     *
     * @param list the LinkedList to traverse
     * @return sum of all values in the list
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
     *
     * @param list the LinkedList to traverse
     * @return sum of all values in the list
     */
    public static long traverseWithGetIndex(LinkedList<Integer> list) {
        long sum = 0;

        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i);
        }

        return sum;
    }

    /**
     * Returns the expected sum for integers 0 through size - 1.
     *
     * @param size number of integers
     * @return expected arithmetic sum
     */
    public static long expectedSum(int size) {
        return (long) size * (size - 1) / 2;
    }

    /**
     * Runs the benchmark for one list size.
     *
     * @param size list size to test
     * @return BenchmarkResult containing measured times
     */
    public static BenchmarkResult benchmark(int size) {
        System.out.println("Building list...");
        LinkedList<Integer> list = buildList(size);
        long expected = expectedSum(size);

        System.out.println("Traversing with iterator...");
        long iteratorStart = System.nanoTime();
        long iteratorSum = traverseWithIterator(list);
        long iteratorEnd = System.nanoTime();
        double iteratorMs = (iteratorEnd - iteratorStart) / 1_000_000.0;
        System.out.printf("Iterator traversal time for %,d: %,.3f ms%n", size, iteratorMs);

        System.out.println("Traversing with get(index)...");
        long getIndexStart = System.nanoTime();
        long getIndexSum = traverseWithGetIndex(list);
        long getIndexEnd = System.nanoTime();
        double getIndexMs = (getIndexEnd - getIndexStart) / 1_000_000.0;
        System.out.printf("get(index) traversal time for %,d: %,.3f ms%n", size, getIndexMs);

        if (iteratorSum != expected) {
            throw new IllegalStateException(
                    "Iterator sum incorrect. Expected " + expected + " but got " + iteratorSum);
        }

        if (getIndexSum != expected) {
            throw new IllegalStateException(
                    "get(index) sum incorrect. Expected " + expected + " but got " + getIndexSum);
        }

        return new BenchmarkResult(size, iteratorMs, getIndexMs);
    }

    /**
     * Small warm-up run to reduce JVM startup effects.
     */
    private static void warmUpJvm() {
        LinkedList<Integer> warmUpList = buildList(10_000);
        traverseWithIterator(warmUpList);
        traverseWithGetIndex(warmUpList);
    }

    /**
     * Prints final comparison between both test sizes.
     *
     * @param small result for 50,000 integers
     * @param large result for 500,000 integers
     */
    private static void printComparison(BenchmarkResult small, BenchmarkResult large) {
        System.out.println("\nOverall Comparison");
        System.out.println("==================================================");
        System.out.printf("%,d iterator:    %,.3f ms%n", small.getSize(), small.getIteratorMs());
        System.out.printf("%,d get(index):  %,.3f ms%n", small.getSize(), small.getGetIndexMs());
        System.out.printf("%,d iterator:    %,.3f ms%n", large.getSize(), large.getIteratorMs());
        System.out.printf("%,d get(index):  %,.3f ms%n", large.getSize(), large.getGetIndexMs());

        double iteratorGrowth = large.getIteratorMs() / small.getIteratorMs();
        double getIndexGrowth = large.getGetIndexMs() / small.getGetIndexMs();

        System.out.println("\nGrowth from 50,000 to 500,000");
        System.out.println("--------------------------------------------------");
        System.out.printf("Iterator growth:   %,.2f times%n", iteratorGrowth);
        System.out.printf("get(index) growth: %,.2f times%n", getIndexGrowth);
    }

    /**
     * Stores benchmark output for one list size.
     */
    public static class BenchmarkResult {
        private final int size;
        private final double iteratorMs;
        private final double getIndexMs;

        public BenchmarkResult(int size, double iteratorMs, double getIndexMs) {
            this.size = size;
            this.iteratorMs = iteratorMs;
            this.getIndexMs = getIndexMs;
        }

        public int getSize() {
            return size;
        }

        public double getIteratorMs() {
            return iteratorMs;
        }

        public double getGetIndexMs() {
            return getIndexMs;
        }
    }
}