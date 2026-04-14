package com.baldree.mod5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for reading words from a file or resource,
 * removing duplicates, and storing them in sorted order.
 */
public final class WordCollectionUtil {

    private static final Pattern WORD_PATTERN = Pattern.compile("[A-Za-z']+");

    private WordCollectionUtil() {
        // Prevent instantiation
    }

    /**
     * Reads words from a file path and returns a sorted set of unique words.
     *
     * @param filePath the file path to read from
     * @return sorted set of unique words
     * @throws IOException if the file cannot be read
     */
    public static NavigableSet<String> readUniqueWords(Path filePath) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {
            return extractWords(reader);
        }
    }

    /**
     * Reads words from a classpath resource and returns a sorted set of unique
     * words.
     *
     * @param resourceName the resource file name
     * @return sorted set of unique words
     * @throws IOException if the resource cannot be found or read
     */
    public static NavigableSet<String> readUniqueWordsFromResource(String resourceName) throws IOException {
        InputStream inputStream = WordCollectionUtil.class.getClassLoader().getResourceAsStream(resourceName);

        if (inputStream == null) {
            throw new IOException("Resource not found: " + resourceName);
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return extractWords(reader);
        }
    }

    /**
     * Extracts words from a BufferedReader, converts them to lowercase,
     * removes duplicates, and stores them in ascending order.
     *
     * @param reader the reader containing text
     * @return sorted set of unique words
     * @throws IOException if an I/O error occurs
     */
    private static NavigableSet<String> extractWords(BufferedReader reader) throws IOException {
        NavigableSet<String> uniqueWords = new TreeSet<>();
        String line;

        while ((line = reader.readLine()) != null) {
            Matcher matcher = WORD_PATTERN.matcher(line.toLowerCase());

            while (matcher.find()) {
                uniqueWords.add(matcher.group());
            }
        }

        return uniqueWords;
    }
}