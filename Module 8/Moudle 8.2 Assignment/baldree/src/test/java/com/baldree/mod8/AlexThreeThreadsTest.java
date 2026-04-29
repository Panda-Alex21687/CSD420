package com.baldree.mod8;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class AlexThreeThreadsTest {

    @Test
    public void testRandomLetterProducesOnlyLetters() {
        Random random = new Random();

        for (int i = 0; i < 1000; i++) {
            char result = AlexThreeThreads.randomLetter(random);
            assertTrue(AlexThreeThreads.isValidLetter(result));
        }
    }

    @Test
    public void testRandomDigitProducesOnlyDigits() {
        Random random = new Random();

        for (int i = 0; i < 1000; i++) {
            char result = AlexThreeThreads.randomDigit(random);
            assertTrue(AlexThreeThreads.isValidDigit(result));
        }
    }

    @Test
    public void testRandomSpecialCharacterProducesOnlySpecialCharacters() {
        Random random = new Random();

        for (int i = 0; i < 1000; i++) {
            char result = AlexThreeThreads.randomSpecialCharacter(random);
            assertTrue(AlexThreeThreads.isValidSpecialCharacter(result));
        }
    }

    @Test
    public void testCharacterCountRequirement() {
        assertEquals(10_000, AlexThreeThreads.CHARACTERS_PER_THREAD);
    }
}