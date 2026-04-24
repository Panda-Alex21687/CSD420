package com.baldree.mod6;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;

public class Bubble_Sort_Test {

    static class Student {
        private final String name;
        private final double gpa;

        Student(String name, double gpa) {
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

    @Test
    void testComparableWithIntegers() {
        Integer[] numbers = { 5, 2, 9, 1, 3 };
        Integer[] expected = { 1, 2, 3, 5, 9 };

        Bubble_Sort.bubbleSort(numbers);

        assertArrayEquals(expected, numbers);
    }

    @Test
    void testComparableWithStrings() {
        String[] words = { "pear", "apple", "orange", "banana" };
        String[] expected = { "apple", "banana", "orange", "pear" };

        Bubble_Sort.bubbleSort(words);

        assertArrayEquals(expected, words);
    }

    @Test
    void testComparatorWithStudentsByGpa() {
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
        assertArrayEquals(expected, actual);
    }

    @Test
    void testComparatorWithStudentsByName() {
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
        assertArrayEquals(expected, actual);
    }

    @Test
    void testEmptyArray() {
        Integer[] numbers = {};
        Integer[] expected = {};

        Bubble_Sort.bubbleSort(numbers);

        assertArrayEquals(expected, numbers);
    }
}