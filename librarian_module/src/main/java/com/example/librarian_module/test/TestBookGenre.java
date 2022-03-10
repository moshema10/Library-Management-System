package com.example.librarian_module.test;

import com.example.librarian_module.sys.BookGenre;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
 * The class TestBookGenre is the Junit5 test for class BookGenre.
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

class TestBookGenre {
    @Test
    @DisplayName("Test get book genre ID given genre name, mixing cases")
    void testGetBookGenreID() {
        Assertions.assertEquals(8, BookGenre.getGenreID("Textbook"));
        assertEquals(3, BookGenre.getGenreID("HORROR"));
        assertEquals(11, BookGenre.getGenreID("SeLf-heLp"));
        assertEquals(12, BookGenre.getGenreID("Not found"));
    }

    @Test
    @DisplayName("Test get book genre name given ID")
    void testGetBookGenreName() {
        assertEquals("Action", BookGenre.getGenreName(1));
        assertEquals("Reference book", BookGenre.getGenreName(9));
        assertEquals("Self-help", BookGenre.getGenreName(11));
        assertEquals("Generic", BookGenre.getGenreName(12));
        // invalid genre ID
        assertEquals("", BookGenre.getGenreName(0));
        assertEquals("", BookGenre.getGenreName(-1));
        assertEquals("", BookGenre.getGenreName(20));
    }
}
