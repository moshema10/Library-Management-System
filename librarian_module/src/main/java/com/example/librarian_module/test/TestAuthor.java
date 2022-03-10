package com.example.librarian_module.test;

import com.example.librarian_module.sys.Author;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.io.IOException;
import java.io.PrintWriter;

/**********************************************
 Assignment 1 - Librarian Module
 Course: BTP400 Object-Oriented Software Development II – Java – Winter 2022
 Last Name: Yeung
 First Name: Jackson
 ID: 110018207
 Section: NAA
 This assignment represents my own work in accordance with Seneca Academic Policy.
 Date: March 4, 2022
 **********************************************/

/**
 * The class TestAuthor is the Junit5 test for class Author.
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

@TestMethodOrder(OrderAnnotation.class)
class TestAuthor {
    static Author author;
    static String DBfile = "authorDB.txt";

    @BeforeAll
    static void setUp() {
        try {
            new PrintWriter(DBfile).close();
        } catch (IOException e) {
            System.out.println("IO Exception: " + DBfile);
        }
        author = new Author();
        author = new Author("Dole", "John");
    }

    @Test
    @Order(1)
    @DisplayName("Test create new authors, returning index of author created")
    void testCreateAuthor() {
        assertEquals(0, author.insertAuthor(), "Create first author");
        assertEquals(1, Author.insertAuthor("Hung", "Mary"),
                "Create second author");
    }

    @Test
    @Order(2)
    @DisplayName("Test get author IDs")
    void testGetAuthorID() {
        assertEquals(1, Author.getAuthorID("Dole", "John"),
                "Get first author ID");
        assertEquals(3, Author.getAuthorID("Grant", "Peter"),
                "Author not yet exists, create new (third) author");
        assertEquals(2, Author.getAuthorID("HuNg", "MaRy"),
                "Get second author ID, mixing cases of author name");
    }

    @Test
    @Order(3)
    @DisplayName("Test get author's full name")
    void testGetAuthorName() {
        assertEquals("John Dole", Author.getAuthorName(1),
                "Get first author full name");
        assertEquals("Mary Hung", Author.getAuthorName(2),
                "Get second author full name");
        assertEquals("Peter Grant", Author.getAuthorName(3),
                "Get third author full name");
        assertEquals("", Author.getAuthorName(5),
                "Non-existing author");
        assertEquals("", Author.getAuthorName(0),
                "Invalid author ID");
        assertEquals("", Author.getAuthorName(-1),
                "Invalid author ID");
    }

    @AfterAll
    static void cleanUp() {
        try {
            new PrintWriter(DBfile).close();
        } catch (IOException e) {
            System.out.println("IO Exception: " + DBfile);
        }
    }
}
