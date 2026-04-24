package com.baldree.mod6;

import java.util.Comparator;

/**
 * This class contains two generic bubble sort methods.
 * One method uses the Comparable interface.
 * The other method uses the Comparator interface.
 */
public class Bubble_Sort {

    public static <T extends Comparable<? super T>> void bubbleSort(T[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null.");
        }

        boolean swapped;

        for (int pass = 0; pass < array.length - 1; pass++) {
            swapped = false;

            for (int i = 0; i < array.length - 1 - pass; i++) {
                if (array[i].compareTo(array[i + 1]) > 0) {
                    swap(array, i, i + 1);
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }
    }

    public static <T> void bubbleSort(T[] array, Comparator<? super T> comparator) {
        if (array == null) {
            throw new IllegalArgumentException("Array cannot be null.");
        }

        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null.");
        }

        boolean swapped;

        for (int pass = 0; pass < array.length - 1; pass++) {
            swapped = false;

            for (int i = 0; i < array.length - 1 - pass; i++) {
                if (comparator.compare(array[i], array[i + 1]) > 0) {
                    swap(array, i, i + 1);
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }
    }

    private static <T> void swap(T[] array, int first, int second) {
        T temp = array[first];
        array[first] = array[second];
        array[second] = temp;
    }
}