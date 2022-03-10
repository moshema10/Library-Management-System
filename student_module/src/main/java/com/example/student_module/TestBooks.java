package com.example.student_module;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class TestBooks {
    static Books abook;

    @BeforeAll
    static void setUp() {
        abook = new Books();
    }

    @Test
    @DisplayName("Test set/get attributes of the book")
    void testGetValue() {
        abook.setBookID("1001");
        abook.setTitle("This is a book");
        abook.setAuthor("John Lang");
        abook.setGenre("Horror");
        abook.setEdition("1");
        abook.setISBN("1234567x");
        abook.setYear("2021");
        abook.setPages("200");
        assertEquals("1001", abook.getBookID());
        assertEquals("This is a book", abook.getTitle());
        assertEquals("John Lang", abook.getAuthor());
        assertEquals("Horror", abook.getGenre());
        assertEquals("1", abook.getEdition());
        assertEquals("1234567x", abook.getISBN());
        assertEquals("2021", abook.getYear());
        assertEquals("200", abook.getPages());
    }

    @Test
    @DisplayName("Test book list size")
    void testBookListSize() {
        assertEquals(3, abook.list().size());
    }

    @Test
    @DisplayName("Test check book exists")
    void testBookExists() {
        assertTrue(abook.getBookID("1001"));
        assertTrue(abook.getBookID("1003"));
        assertFalse(abook.getBookID("1000"));
        assertFalse(abook.getBookID("2001"));

    }
}
