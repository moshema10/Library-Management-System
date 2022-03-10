package com.example.librarian_module.test;

import com.example.librarian_module.sys.BookCopy;
import com.example.librarian_module.sys.BookDB;
import com.example.librarian_module.sys.Item;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;

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
 * The class TestBookCopy is the Junit5 test for class BookCopy.
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

@TestMethodOrder(OrderAnnotation.class)
class TestBookCopy {
    static BookCopy bkcpy;
    static ArrayList<BookCopy> copies;

    @BeforeAll
    static void setUp() {
        try {
            new PrintWriter("bookcopyDB.txt").close();
        } catch (IOException e) {
            System.out.println("IO Exception: in TestBookCopy");
        }
        bkcpy = new BookCopy();
        copies = new ArrayList<>();

        bkcpy = new BookCopy("001", "1", 1);
        copies.add(bkcpy);
        BookDB.addNewCopy("001", "1", 1);
        bkcpy = new BookCopy("001", "1", 2);
        copies.add(bkcpy);
        BookDB.addNewCopy("001", "1", 2);
    }

    @Test
    @Order(1)
    @DisplayName("Issue book copy")
    void testIssue() {
        Assertions.assertEquals(Item.StatusCode.SUCCESS, BookCopy.issue(copies, "1234"),
                "Check out first copy");
        assertEquals(Item.StatusCode.SUCCESS, BookCopy.issue(copies, "3456"),
                "Check out second copy");
        assertEquals(Item.StatusCode.NO_AVAILABLE_COPY, BookCopy.issue(copies, "5678"),
                "No more copies left");
    }

    @Test
    @Order(2)
    @DisplayName("Return book copy")
    void testReturn() {
        assertEquals(Item.StatusCode.ITEM_NOT_SIGNED_OUT, BookCopy.returnBack(copies, "6789"),
                "No copy signed out by this borrower");
        assertEquals(Item.StatusCode.SUCCESS, BookCopy.returnBack(copies, "1234"),
                "Return first copy");
    }

    @Test
    @Order(3)
    @DisplayName("Return book copy that's overdue")
    void testReturnOverdueBook() {
        bkcpy.setDueDate(LocalDate.now().minusDays(5));
        assertEquals(Item.StatusCode.ITEM_OVERDUE, BookCopy.returnBack(copies, "3456"),
                "Return second copy that's overdue");
    }

    @Test
    @Order(4)
    @DisplayName("Issue book copy again")
    void testIssueAgain() {
        assertEquals(Item.StatusCode.SUCCESS, BookCopy.issue(copies, "4567"),
                "Check out first copy again");
        assertEquals(Item.StatusCode.SUCCESS, BookCopy.issue(copies, "3456"),
                "Check out second copy again");
        assertEquals(Item.StatusCode.NO_AVAILABLE_COPY, BookCopy.issue(copies, "5678"),
                "No more copies left");
    }

    @AfterAll
    static void cleanUp() {
        try {
            new PrintWriter("bookcopyDB.txt").close();
        } catch (IOException e) {
            System.out.println("IO Exception: in TestBookCopy");
        }
    }
}
