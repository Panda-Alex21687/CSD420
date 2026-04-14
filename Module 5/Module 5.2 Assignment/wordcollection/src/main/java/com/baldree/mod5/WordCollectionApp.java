package com.baldree.mod5;

import java.io.IOException;
import java.util.NavigableSet;

/**
 * Main program that reads words from collection_of_words.txt
 * and displays the non-duplicate words in ascending and descending order.
 */
public class WordCollectionApp {

    private static final String FILE_NAME = "collection_of_words.txt";

    public static void main(String[] args) {
        try {
            NavigableSet<String> words = WordCollectionUtil.readUniqueWordsFromResource(FILE_NAME);

            System.out.println("Non-duplicate words in ascending order:");
            for (String word : words) {
                System.out.println(word);
            }

            System.out.println();
            System.out.println("Non-duplicate words in descending order:");
            for (String word : words.descendingSet()) {
                System.out.println(word);
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}