package com.example.librarian_module.test;

import com.example.librarian_module.sys.Account;
import com.example.librarian_module.sys.Book;
import com.example.librarian_module.sys.Librarian;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import com.example.librarian_module.sys.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

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
 * The class TestIssueBook is the Junit5 test for testing Issue Book operation.
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

@TestMethodOrder(OrderAnnotation.class)
class TestIssueBook {
    Librarian lbr;

    @BeforeAll
    static void setUp() {
        try {
            new PrintWriter("accountDB.txt").close();
            new PrintWriter("authorDB.txt").close();
            new PrintWriter("bookDB.txt").close();
            new PrintWriter("bookcopyDB.txt").close();
            new PrintWriter("bookeditionDB.txt").close();
            PrintWriter pw = new PrintWriter("librarianDB.txt");
            pw.write("user|abc|NoName|NoName\n");
            pw.close();
        } catch (IOException e) {
            System.out.println("IO Exception: in TestAddBook");
        }
    }

    @Test
    @Order(1)
    @DisplayName("Test add book")
    void testAddBook() {
        assertEquals(Account.LoginStatus.SUCCESS, Account.login("user", "abc"),
                "Login librarian 'user'");
        lbr = Librarian.getActiveLibrarian("user");
        assertNotNull(lbr, "Librarian 'user' is logged in");

        assertEquals(Book.StatusCode.SUCCESS,
                lbr.addBook("001", "Very tough assignment", "Dole", "John", "1",
                        "2314567", "2018", "300", "1", "Horror"),
                "Book #1 added");
        assertEquals(Book.StatusCode.SUCCESS,
                lbr.addBook("002", "No time for fun", "Pan", "Peter", "1",
                        "8745231", "2021", "250", "2", "Default"),
                "Book #3 added");
        assertEquals(Book.StatusCode.SUCCESS,
                lbr.addBook("003", "Tomorrow will be better", "Dole", "John", "1",
                        "123456789", "2022", "600", "3", "Self-help"),
                "Book #4 added, same author as book #1");
        assertEquals(Account.LoginStatus.SUCCESS, Account.logout("user"),
                "Log out librarian  'user''");
    }

    @Test
    @Order(2)
    @DisplayName("Test issue books")
    void testIssueBook() {
        assertEquals(Account.LoginStatus.SUCCESS, Account.login("user", "abc"),
                "Login librarian 'user'");
        lbr = Librarian.getActiveLibrarian("user");
        assertNotNull(lbr, "Librarian 'user' is logged in");

        assertEquals(Book.StatusCode.ITEM_NOT_FOUND, lbr.issueBook("102", "1", "1234"),
                "Book item not exists");
        assertEquals(Book.StatusCode.ITEM_NOT_FOUND, lbr.issueBook("001", "2", "1234"),
                "Book item not exists");
        assertEquals(Book.StatusCode.SUCCESS, lbr.issueBook("002", "1", "1234"),
                "Sign out first copy of Book '002'");
        assertEquals(Book.StatusCode.SUCCESS, lbr.issueBook("002", "1", "2345"),
                "Sign out second copy of Book '002'");
        assertEquals(Book.StatusCode.NO_AVAILABLE_COPY, lbr.issueBook("002", "1", "3456"),
                "No more copies of Book '002' left");
        assertEquals(Book.StatusCode.SUCCESS, lbr.issueBook("003", "1", "4567"),
                "Sign out first copy of Book '003'");

        assertEquals(Account.LoginStatus.SUCCESS, Account.logout("user"),
                "Log out librarian  'user''");
    }

    @Test
    @Order(3)
    @DisplayName("Test read 'bookcopyDB.txt'")
    void testReadBookCopyDB() {
        LocalDate today = LocalDate.now();
        LocalDate due = today.plusDays(21);
        String str1 = today.toString();
        String str2 = due.toString();
        try (BufferedReader br = new BufferedReader(new FileReader("bookcopyDB.txt"))) {
            assertEquals("001        1  1  1 null       null       null      ", br.readLine());
            assertEquals("002        1  1  0 " + str1 + " " + str2 + " 1234      ", br.readLine());
            assertEquals("002        1  2  0 " + str1 + " " + str2 + " 2345      ", br.readLine());
            assertEquals("003        1  1  0 " + str1 + " " + str2 + " 4567      ", br.readLine());
            assertEquals("003        1  2  1 null       null       null      ", br.readLine());
            assertEquals("003        1  3  1 null       null       null      ", br.readLine());
            assertNull(br.readLine());
        } catch (IOException e) {
            System.out.println("IO Exception: in TestAddBook");
        }
    }

    @AfterAll
    static void cleanUp() {
        try {
            new PrintWriter("accountDB.txt").close();
            new PrintWriter("authorDB.txt").close();
            new PrintWriter("bookDB.txt").close();
            new PrintWriter("bookcopyDB.txt").close();
            new PrintWriter("bookeditionDB.txt").close();
            new PrintWriter("librarianDB.txt").close();
        } catch (IOException e) {
            System.out.println("IO Exception: in TestAddBook");
        }
    }
}
