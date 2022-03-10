package com.example.librarian_module.test;

import com.example.librarian_module.sys.Account;
import com.example.librarian_module.sys.Book;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import com.example.librarian_module.sys.*;

import java.io.IOException;
import java.io.PrintWriter;

import com.example.librarian_module.sys.Librarian;

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
 * The class TestAccessControl is the Junit5 test for controlling access to library operations.
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

@TestMethodOrder(OrderAnnotation.class)
class TestAccessControl {
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
            System.out.println("IO Exception: in TestAccessControl");
        }

        Account.insertAccount("user2", "abc2", Account.AccessLevel.GRAD_STUDENT);
    }

    @Test
    @Order(1)
    @DisplayName("Test access of 'user', a librarian who is logged in")
    void testLibrarianLoggedIn() {
        lbr = Librarian.getActiveLibrarian("user");
        assertNull(lbr, "Librarian 'user' not logged in");

        assertEquals(Account.LoginStatus.SUCCESS, Account.login("user", "abc"),
                "Login librarian 'user'");
        lbr = Librarian.getActiveLibrarian("user");
        assertNotNull(lbr, "Librarian 'user' is logged in");

        assertEquals(Book.StatusCode.SUCCESS,
            lbr.addBook("001", "Very tough assignment", "Dole", "John", "1",
                "2314567", "2018", "300", "2", "Horror"),
                "Librarian 'user' adds book");
        assertEquals(Book.StatusCode.SUCCESS,
                lbr.issueBook("001", "1", "1234"),
                "Librarian 'user' issues book");
        assertEquals(Book.StatusCode.SUCCESS,
                lbr.returnBook("001", "1", "1234"),
                "Librarian 'user' returns book");
        assertEquals(Account.LoginStatus.SUCCESS, Account.logout("user"),
                "Log out librarian  'user''");
    }

    @Test
    @Order(2)
    @DisplayName("Test access of 'user', a librarian who is not logged in")
    void testLibrarianLoggedOut() {
        assertEquals(Account.LoginStatus.SUCCESS, Account.login("user", "abc"),
                "Login librarian 'user'");
        lbr = Librarian.getActiveLibrarian("user");
        assertNotNull(lbr, "Librarian 'user' is logged in");
        assertEquals(Account.LoginStatus.SUCCESS, Account.logout("user"),
                "Log out librarian  'user''");

        assertEquals(Book.StatusCode.NOT_LOGGED_IN,
                lbr.addBook("002", "No time for fun", "Pan", "Peter", "1",
                        "8745231", "2021", "250", "3", "Default"),
                "Librarian 'user' can't add book since he's not logged in");
        assertEquals(Book.StatusCode.NOT_LOGGED_IN,
                lbr.issueBook("001", "1", "1234"),
                "Librarian 'user' can't issue book since he's not logged in");
        assertEquals(Book.StatusCode.NOT_LOGGED_IN,
                lbr.returnBook("001", "1", "1234"),
                "Librarian 'user' can't return book since he's not logged in");
    }

    @Test
    @Order(3)
    @DisplayName("Test access of 'user2', a graduate student")
    void testGraduateStudent() {
        assertEquals(Account.LoginStatus.SUCCESS, Account.login("user2", "abc2"),
                "Login Graduate student 'user2'");
        lbr = Librarian.getActiveLibrarian("user2");
        assertNull(lbr, "Graduate student 'user2' is not a librarian");
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
            System.out.println("IO Exception: in TestAccessControl");
        }
    }
}
