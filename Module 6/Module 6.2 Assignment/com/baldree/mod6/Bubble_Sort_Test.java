package com.baldree.mod6;

import java.util.Arrays;
import java.util.Comparator;

/**
 * This class tests the Bubble_Sort methods.
 */
public class Bubble_Sort_Test {

    static class Student {
        private String name;
        private double gpa;

        public Student(String name, double gpa) {
            this.name = name;
            this.gpa = gpa;
        }

        public String getName() {
            return name;
        }

        public double getGpa() {
            return gpa;
        }
    }

    public static void main(String[] args) {
        testComparableWithIntegers();
        testComparableWithStrings();
        testComparatorWithStudentsByGpa();
        testComparatorWithStudentsByName();
        testEmptyArray();

        System.out.println("All tests passed successfully.");
    }

    public static void testComparableWithIntegers() {
        Integer[] numbers = { 5, 2, 9, 1, 3 };
        Integer[] expected = { 1, 2, 3, 5, 9 };

        Bubble_Sort.bubbleSort(numbers);
        checkArray(expected, numbers, "Comparable integer test");
    }

    public static void testComparableWithStrings() {
        String[] words = { "pear", "apple", "orange", "banana" };
        String[] expected = { "apple", "banana", "orange", "pear" };

        Bubble_Sort.bubbleSort(words);
        checkArray(expected, words, "Comparable string test");
    }

    public static void testComparatorWithStudentsByGpa() {
        Student[] students = {
                new Student("Alex", 3.2),
                new Student("Jordan", 3.9),
                new Student("Taylor", 2.8)
        };

        Bubble_Sort.bubbleSort(students, Comparator.comparingDouble(Student::getGpa));

        String[] actual = {
                students[0].getName(),
                students[1].getName(),
                students[2].getName()
        };

        String[] expected = { "Taylor", "Alex", "Jordan" };
        checkArray(expected, actual, "Comparator GPA test");
    }

    public static void testComparatorWithStudentsByName() {
        Student[] students = {
                new Student("Chris", 3.1),
                new Student("Alex", 3.8),
                new Student("Brianna", 3.5)
        };

        Bubble_Sort.bubbleSort(students, Comparator.comparing(Student::getName));

        String[] actual = {
                students[0].getName(),
                students[1].getName(),
                students[2].getName()
        };

        String[] expected = { "Alex", "Brianna", "Chris" };
        checkArray(expected, actual, "Comparator name test");
    }

    public static void testEmptyArray() {
        Integer[] numbers = {};
        Integer[] expected = {};

        Bubble_Sort.bubbleSort(numbers);
        checkArray(expected, numbers, "Empty array test");
    }

    public static <T> void checkArray(T[] expected, T[] actual, String testName) {
        if (Arrays.equals(expected, actual)) {
            System.out.println(testName + " passed.");
        } else {
            System.out.println(testName + " failed.");
            System.out.println("Expected: " + Arrays.toString(expected));
            System.out.println("Actual:   " + Arrays.toString(actual));
        }
    }
}