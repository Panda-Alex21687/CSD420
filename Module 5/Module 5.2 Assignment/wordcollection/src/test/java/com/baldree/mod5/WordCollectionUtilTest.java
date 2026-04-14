package com.baldree.mod5;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Test class for WordCollectionUtil.
 */
public class WordCollectionUtilTest {

    @TempDir
    Path tempDir;

    @Test
    void shouldReadUniqueWordsInAscendingOrder() throws IOException {
        Path testFile = tempDir.resolve("collection_of_words.txt");

        Files.writeString(
                testFile,
                "Banana apple orange banana Apple grape",
                StandardCharsets.UTF_8);

        NavigableSet<String> words = WordCollectionUtil.readUniqueWords(testFile);

        List<String> actual = new ArrayList<>(words);
        List<String> expected = List.of("apple", "banana", "grape", "orange");

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnWordsInDescendingOrder() throws IOException {
        Path testFile = tempDir.resolve("collection_of_words.txt");

        Files.writeString(
                testFile,
                "dog cat bird dog ant",
                StandardCharsets.UTF_8);

        NavigableSet<String> words = WordCollectionUtil.readUniqueWords(testFile);

        List<String> actual = new ArrayList<>(words.descendingSet());
        List<String> expected = List.of("dog", "cat", "bird", "ant");

        assertEquals(expected, actual);
    }

    @Test
    void shouldHandlePunctuationCorrectly() throws IOException {
        Path testFile = tempDir.resolve("collection_of_words.txt");

        Files.writeString(
                testFile,
                "Hello, world! Hello again. World of Java.",
                StandardCharsets.UTF_8);

        NavigableSet<String> words = WordCollectionUtil.readUniqueWords(testFile);

        assertTrue(words.contains("hello"));
        assertTrue(words.contains("world"));
        assertTrue(words.contains("again"));
        assertTrue(words.contains("of"));
        assertTrue(words.contains("java"));
        assertEquals(5, words.size());
    }

    @Test
    void shouldThrowExceptionWhenFileDoesNotExist() {
        Path missingFile = tempDir.resolve("missing_file.txt");

        assertThrows(IOException.class, () -> WordCollectionUtil.readUniqueWords(missingFile));
    }
}