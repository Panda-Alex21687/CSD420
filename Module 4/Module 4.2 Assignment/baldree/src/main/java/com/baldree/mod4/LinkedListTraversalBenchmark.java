package com.baldree.mod4;

import java.util.Iterator;
import java.util.LinkedList;

public class LinkedListTraversalBenchmark {

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

    public static LinkedList<Integer> buildList(int size) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
        return list;
    }

    public static long traverseWithIterator(LinkedList<Integer> list) {
        long sum = 0;
        Iterator<Integer> iterator = list.iterator();

        while (iterator.hasNext()) {
            sum += iterator.next();
        }

        return sum;
    }

    public static long traverseWithGet(LinkedList<Integer> list) {
        long sum = 0;

        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i);
        }

        return sum;
    }

    public static long expectedSum(int size) {
        return (long) size * (size - 1) / 2;
    }

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

    private static void warmUp() {
        LinkedList<Integer> warmUpList = buildList(1_000);
        traverseWithIterator(warmUpList);
        traverseWithGet(warmUpList);
    }

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