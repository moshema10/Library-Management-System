package com.example.student_module;

import org.junit.jupiter.api.*;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class TestGetDB {
    @Test
    @DisplayName("Test if a book is available for loan")
    void testHasBook() {
        assertEquals(GetDB.BookStatus.AVAILABLE, GetDB.hasbook("1001"));
        assertEquals(GetDB.BookStatus.NOT_AVAILABLE, GetDB.hasbook("1002"));
        assertEquals(GetDB.BookStatus.AVAILABLE, GetDB.hasbook("1003"));
        assertEquals(GetDB.BookStatus.NO_BOOK, GetDB.hasbook("1004"));
    }

    @Test
    @DisplayName("Test number of borrowed books of student")
    void testBorrowedBooks() {
        assertEquals(2, GetDB.getBorrowedBooks("1234").size());
        assertEquals(1, GetDB.getBorrowedBooks("4567").size());
        assertEquals(0, GetDB.getBorrowedBooks("2345").size());
    }

    @Test
    @DisplayName("Test Book ID of borrowed books of student")
    void testPlaceRequest() {
        ArrayList<Books> books = GetDB.getBorrowedBooks("1234");
        assertEquals("1002", books.get(0).getBookID());
        assertEquals("1003", books.get(1).getBookID());
    }
}
