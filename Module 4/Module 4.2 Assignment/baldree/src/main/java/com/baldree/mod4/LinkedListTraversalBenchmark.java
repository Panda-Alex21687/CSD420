package com.baldree.mod4;

import java.util.Iterator;
import java.util.LinkedList;

public class LinkedListTraversalBenchmark {

    public static void main(String[] args) {
        System.out.println("Starting benchmark...");
        warmUpJvm();

        System.out.println("\nTesting 50,000 integers...");
        BenchmarkResult result50k = benchmark(50_000);

        System.out.println("\nTesting 500,000 integers...");
        BenchmarkResult result500k = benchmark(500_000);

        printResult(result50k);
        printResult(result500k);
        printComparison(result50k, result500k);
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

    public static long traverseWithGetIndex(LinkedList<Integer> list) {
        long sum = 0;

        for (int i = 0; i < list.size(); i++) {
            sum += list.get(i);
        }

        return sum;
    }

    public static long expectedSum(int size) {
        return (long) size * (size - 1) / 2;
    }

    public static BenchmarkResult benchmark(int size) {
        System.out.println("Building list...");
        LinkedList<Integer> list = buildList(size);
        long expected = expectedSum(size);

        System.out.println("Traversing with iterator...");
        TimedResult iteratorResult = measureIterator(list);

        System.out.println("Traversing with get(index)...");
        TimedResult getIndexResult = measureGetIndex(list);

        if (iteratorResult.sum != expected) {
            throw new IllegalStateException("Iterator sum incorrect.");
        }

        if (getIndexResult.sum != expected) {
            throw new IllegalStateException("get(index) sum incorrect.");
        }

        return new BenchmarkResult(
                size,
                iteratorResult.nanos / 1_000_000.0,
                getIndexResult.nanos / 1_000_000.0);
    }

    private static void warmUpJvm() {
        LinkedList<Integer> warmUpList = buildList(10_000);
        traverseWithIterator(warmUpList);
        traverseWithGetIndex(warmUpList);
    }

    private static TimedResult measureIterator(LinkedList<Integer> list) {
        long start = System.nanoTime();
        long sum = traverseWithIterator(list);
        long end = System.nanoTime();
        return new TimedResult(sum, end - start);
    }

    private static TimedResult measureGetIndex(LinkedList<Integer> list) {
        long start = System.nanoTime();
        long sum = traverseWithGetIndex(list);
        long end = System.nanoTime();
        return new TimedResult(sum, end - start);
    }

    private static void printResult(BenchmarkResult result) {
        double differenceMs = result.getIndexMs - result.iteratorMs;
        double slowerFactor = result.getIndexMs / result.iteratorMs;

        System.out.printf("%nResults for %,d integers%n", result.size);
        System.out.println("--------------------------------------------------");
        System.out.printf("Iterator traversal time:   %,.3f ms%n", result.iteratorMs);
        System.out.printf("get(index) traversal time: %,.3f ms%n", result.getIndexMs);
        System.out.printf("Difference:                %,.3f ms%n", differenceMs);
        System.out.printf("get(index) was about       %,.2f times slower%n", slowerFactor);
    }

    private static void printComparison(BenchmarkResult small, BenchmarkResult large) {
        double iteratorGrowth = large.iteratorMs / small.iteratorMs;
        double getIndexGrowth = large.getIndexMs / small.getIndexMs;

        System.out.println("\nOverall Comparison");
        System.out.println("==================================================");
        System.out.printf("Iterator growth from %,d to %,d:   %,.2f times%n",
                small.size, large.size, iteratorGrowth);
        System.out.printf("get(index) growth from %,d to %,d: %,.2f times%n",
                small.size, large.size, getIndexGrowth);
    }

    private static class TimedResult {
        private final long sum;
        private final long nanos;

        private TimedResult(long sum, long nanos) {
            this.sum = sum;
            this.nanos = nanos;
        }
    }

    public static class BenchmarkResult {
        private final int size;
        private final double iteratorMs;
        private final double getIndexMs;

        public BenchmarkResult(int size, double iteratorMs, double getIndexMs) {
            this.size = size;
            this.iteratorMs = iteratorMs;
            this.getIndexMs = getIndexMs;
        }
    }
}