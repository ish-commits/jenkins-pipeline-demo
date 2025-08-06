package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Application
 */
public class ApplicationTest {
    
    private Application application;
    
    @BeforeEach
    void setUp() {
        application = new Application();
    }
    
    @Test
    @DisplayName("Test welcome message generation with valid name")
    void testGenerateWelcomeMessageWithValidName() {
        String result = application.generateWelcomeMessage("Jenkins Pipeline");
        assertEquals("Welcome to Jenkins Pipeline!", result);
    }
    
    @Test
    @DisplayName("Test welcome message generation with null name")
    void testGenerateWelcomeMessageWithNullName() {
        String result = application.generateWelcomeMessage(null);
        assertEquals("Welcome, Guest!", result);
    }
    
    @Test
    @DisplayName("Test welcome message generation with empty name")
    void testGenerateWelcomeMessageWithEmptyName() {
        String result = application.generateWelcomeMessage("");
        assertEquals("Welcome, Guest!", result);
    }
    
    @Test
    @DisplayName("Test welcome message generation with whitespace name")
    void testGenerateWelcomeMessageWithWhitespaceName() {
        String result = application.generateWelcomeMessage("   ");
        assertEquals("Welcome, Guest!", result);
    }
    
    @Test
    @DisplayName("Test sum calculation with positive numbers")
    void testCalculateSumPositiveNumbers() {
        int result = application.calculateSum(10, 20);
        assertEquals(30, result); // FIXED: Correct expected value restored
    }
    
    @Test
    @DisplayName("Test sum calculation with negative numbers")
    void testCalculateSumNegativeNumbers() {
        int result = application.calculateSum(-5, -10);
        assertEquals(-15, result);
    }
    
    @Test
    @DisplayName("Test sum calculation with zero")
    void testCalculateSumWithZero() {
        int result = application.calculateSum(0, 25);
        assertEquals(25, result);
    }
    
    @Test
    @DisplayName("Test even number detection - even number")
    void testIsEvenWithEvenNumber() {
        assertTrue(application.isEven(4));
        assertTrue(application.isEven(0));
        assertTrue(application.isEven(-2));
    }
    
    @Test
    @DisplayName("Test even number detection - odd number")
    void testIsEvenWithOddNumber() {
        assertFalse(application.isEven(3));
        assertFalse(application.isEven(1));
        assertFalse(application.isEven(-1));
    }
    
    @Test
    @DisplayName("Test current timestamp generation")
    void testGetCurrentTimestamp() {
        long timestamp1 = application.getCurrentTimestamp();
        long timestamp2 = application.getCurrentTimestamp();
        
        // Timestamps should be positive and reasonably close
        assertTrue(timestamp1 > 0);
        assertTrue(timestamp2 >= timestamp1);
    }
} 