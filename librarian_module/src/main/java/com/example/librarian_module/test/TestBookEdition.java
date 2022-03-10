package com.example.librarian_module.test;

import com.example.librarian_module.sys.BookEdition;
import com.example.librarian_module.sys.Item;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.io.IOException;
import java.io.PrintWriter;
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
 * The class TestBookEdition is the Junit5 test for class BookEdition.
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

@TestMethodOrder(OrderAnnotation.class)
class TestBookEdition {
    static BookEdition bked, bked2;
    static ArrayList<BookEdition> eds;

    @BeforeAll
    static void setUp() {
        try {
            new PrintWriter("bookcopyDB.txt").close();
            new PrintWriter("bookeditionDB.txt").close();
        } catch (IOException e) {
            System.out.println("IO Exception: in TestBookEdition");
        }
        bked = new BookEdition();
        eds = new ArrayList<>();

        bked = new BookEdition("001", "1", "12345678", "2018", "300", "2");
        eds.add(bked);
        bked2 = new BookEdition("001", "2", "12345928", "2021", "350", "2");
        eds.add(bked2);
    }

    @Test
    @Order(1)
    @DisplayName("Issue book copies of edition #2 of a book")
    void testIssue() {
        Assertions.assertEquals(Item.StatusCode.SUCCESS, bked2.issue("1234"),
                "Sign out first copy");
        assertEquals(Item.StatusCode.SUCCESS, bked2.issue("3456"),
                "Sign out second copy");
        assertEquals(Item.StatusCode.NO_AVAILABLE_COPY, bked2.issue("4567"),
                "No more copies of edition #2 left");
    }

    @Test
    @Order(2)
    @DisplayName("Return book copy of edition #2 of a book")
    void testReturn() {
        assertEquals(Item.StatusCode.ITEM_NOT_SIGNED_OUT, bked2.returnBack("6789"),
                "No copy signed out by this borrower");
        assertEquals(Item.StatusCode.SUCCESS, bked2.returnBack("3456"),
                "Return second copy");
   }

    @Test
    @Order(3)
    @DisplayName("Issue book copy of edition #2ag ain")
    void testIssueAgain() {
        assertEquals(Item.StatusCode.SUCCESS, bked2.issue("3456"),
                "Sign out second copy again");
        assertEquals(Item.StatusCode.NO_AVAILABLE_COPY, bked2.issue("6789"),
                "No more copies of edition #2 left");
        assertEquals(Item.StatusCode.NO_AVAILABLE_COPY, bked2.issue("7890"),
                "No more copies of edition #2 left");
    }

    @AfterAll
    static void cleanUp() {
        try {
            new PrintWriter("bookcopyDB.txt").close();
            new PrintWriter("bookeditionDB.txt").close();
        } catch (IOException e) {
            System.out.println("IO Exception: in TestBookEdition");
        }
    }
}
