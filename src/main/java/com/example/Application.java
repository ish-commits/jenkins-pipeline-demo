package com.example;

import java.util.Scanner;

/**
 * Simple Java Application for Jenkins Pipeline Demo
 */
public class Application {
    
    public static void main(String[] args) {
        System.out.println("=== Jenkins Pipeline Demo Application ===");
        
        Application app = new Application();
        
        // Demo some functionality
        String message = app.generateWelcomeMessage("Jenkins Pipeline");
        System.out.println(message);
        
        int result = app.calculateSum(10, 20);
        System.out.println("Sum of 10 + 20 = " + result);
        
        boolean isEven = app.isEven(result);
        System.out.println("Is " + result + " even? " + isEven);
        
        System.out.println("Application executed successfully!");
    }
    
    /**
     * Generate a welcome message
     */
    public String generateWelcomeMessage(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Welcome, Guest!";
        }
        return "Welcome to " + name + "!";
    }
    
    /**
     * Calculate sum of two numbers
     */
    public int calculateSum(int a, int b) {
        return a + b;
    }
    
    /**
     * Check if a number is even
     */
    public boolean isEven(int number) {
        return number % 2 == 0;
    }
    
    /**
     * Get current timestamp
     */
    public long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }
} 