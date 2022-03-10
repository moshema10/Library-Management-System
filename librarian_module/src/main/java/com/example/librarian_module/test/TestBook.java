package com.example.librarian_module.test;

import com.example.librarian_module.sys.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import com.example.librarian_module.sys.*;

import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;

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
 * The class TestBook is the Junit5 test for class Book. It also tests classes Magazine and Video.
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

@TestMethodOrder(OrderAnnotation.class)
class TestBook {
    @BeforeAll
    static void setUp() {
        try {
            new PrintWriter("authorDB.txt").close();
            new PrintWriter("bookDB.txt").close();
            new PrintWriter("bookcopyDB.txt").close();
            new PrintWriter("bookeditionDB.txt").close();
            PrintWriter pw = new PrintWriter("librarianDB.txt");
            pw.write("user|abc|NoName|NoName\n");
            pw.close();
        } catch (IOException e) {
            System.out.println("IO Exception: in TestBook");
        }
    }

    @Test
    @Order(1)
    @DisplayName("Building items array: test adding Book, Magazine, and Video")
    void testAddItem() {
        Assertions.assertEquals(Book.StatusCode.SUCCESS,
                Book.add("001", "Very tough assignment", "Dole", "John", "1",
                        "2314567", "2018", "300", "1", "Horror"));
        assertEquals(Book.StatusCode.DUPLICATE_ITEM_ID,
                Book.add("001", "Duplicate book item ID", "Cash", "Gordon", "1",
                        "2347556X", "2015", "200", "2", "Romance"));
        Assertions.assertEquals(Book.StatusCode.SUCCESS,
                Magazine.add("002", "Magazine title", "Editor", "Iaman", "2022",
                "3", "8654235Mag", "Mag cat"));
        assertEquals(Book.StatusCode.SUCCESS,
                Book.add("003", "No time for fun", "Pan", "Peter", "1",
                "8745231", "2021", "250", "3", "Default"));
        assertEquals(Book.StatusCode.SUCCESS,
                Book.add("004", "Tomorrow will be better", "Hung", "Mary", "1",
                "123456789", "2022", "600", "2", "Self-help"));
        Assertions.assertEquals(Book.StatusCode.SUCCESS,
                Video.add("005", "Video Title", "Director", "Good", "2020", "Action"));
        assertEquals(Book.StatusCode.DUPLICATE_ITEM_ID,
                Magazine.add("005", "Duplicate magazine ID", "Added", "Not", "2022",
                        "3", "6332332", "Generic"));
        assertEquals(Book.StatusCode.DUPLICATE_ITEM_ID,
                Video.add("004", "Duplicate VID", "Director", "Good", "2013", "Action"));
    }

    @Test
    @Order(2)
    @DisplayName("Issue book #1 which has one copy")
    void testIssue1() {
        assertEquals(Book.StatusCode.ITEM_NOT_FOUND, Book.issue("028", "1", "1234"), "No such book");
        assertEquals(Book.StatusCode.ITEM_NOT_FOUND, Book.issue("001", "2", "1234"), "No such edition");
        assertEquals(Book.StatusCode.SUCCESS, Book.issue("001", "1", "1234"),
                "Sign out first copy of Book '001'");
        assertEquals(Book.StatusCode.NO_AVAILABLE_COPY, Book.issue("001", "1", "2345"),
                "No more copies of Book '001' left");
    }

    @Test
    @Order(3)
    @DisplayName("Issue book #4 which has two copies")
    void testIssue4() {
        assertEquals(Book.StatusCode.ITEM_NOT_FOUND, Book.issue("004", "2", "3456"), "No such edition");
        assertEquals(Book.StatusCode.SUCCESS, Book.issue("004", "1", "3456"),
                "Sign out first copy of Book '004'");
        assertEquals(Book.StatusCode.SUCCESS, Book.issue("004", "1", "4567"),
                "Sign out second copy of Book '004'");
        assertEquals(Book.StatusCode.NO_AVAILABLE_COPY, Book.issue("004", "1", "5678"),
                "No more copies of Book '004' left");
    }

    @Test
    @Order(4)
    @DisplayName("Return one copy of book #1 and #4")
    void testReturn() {
        assertEquals(Book.StatusCode.ITEM_NOT_SIGNED_OUT, Book.returnBack("001", "1", "5678"),
                "No copy signed out by this borrower");
        assertEquals(Book.StatusCode.SUCCESS, Book.returnBack("001", "1", "1234"),
                "Return first copy of Book '001");

        assertEquals(Book.StatusCode.ITEM_NOT_FOUND, Book.returnBack("028", "1", "3456"), "No such book");
        assertEquals(Book.StatusCode.ITEM_NOT_FOUND, Book.returnBack("004", "2", "3456"), "No such edition");
        assertEquals(Book.StatusCode.ITEM_NOT_SIGNED_OUT, Book.returnBack("004", "1", "6789"),
                "No copy signed out by this borrower");
        assertEquals(Book.StatusCode.SUCCESS, Book.returnBack("004", "1", "4567"),
                "Return second copy of Book '004");
    }

    @Test
    @Order(5)
    @DisplayName("Issue book #1 and #4 again")
    void testIssueAgain() {
        assertEquals(Book.StatusCode.SUCCESS, Book.issue("001", "1", "5678"),
                "Sign out first copy of Book '001' again");
        assertEquals(Book.StatusCode.NO_AVAILABLE_COPY, Book.issue("001", "1", "6789"),
                "No more copies of Book '001' left");

        assertEquals(Book.StatusCode.SUCCESS, Book.issue("004", "1", "6789"),
                "Sign out second copy of Book '004' again");
        assertEquals(Book.StatusCode.NO_AVAILABLE_COPY, Book.issue("004", "1", "2345"),
                "No more copies of Book '004' left");
    }

    @Test
    @Order(6)
    @DisplayName("Test get book data and generate data file")
    void viewBooks() {
        Librarian lbr = Librarian.getActiveLibrarian("user");
        assertNull(lbr, "User not logged in yet");

        Account.login("user", "abc");
        lbr = Librarian.getActiveLibrarian("user");
        assertNotNull(lbr, "User is logged in");

        lbr.initReport();
        assertEquals("001|Very tough assignment|John Dole|Horror|1|2314567|2018|300|1|",
                lbr.getNextRec(), "Get data of first book");
        assertEquals("003|No time for fun|Peter Pan|Generic|1|8745231|2021|250|3|",
                lbr.getNextRec(),"Get data of second book");
        assertEquals("004|Tomorrow will be better|Mary Hung|Self-help|1|123456789|2022|600|2|",
                lbr.getNextRec(), "Get data of third book");
        assertNull(lbr.getNextRec(), "No more book");
        lbr.closeReport();
    }

    @AfterAll
    static void cleanUp() {
        try {
            new PrintWriter("authorDB.txt").close();
            new PrintWriter("bookDB.txt").close();
            new PrintWriter("bookcopyDB.txt").close();
            new PrintWriter("bookeditionDB.txt").close();
            new PrintWriter("librarianDB.txt").close();
        } catch (IOException e) {
            System.out.println("IO Exception: in TestBook");
        }
    }
}
