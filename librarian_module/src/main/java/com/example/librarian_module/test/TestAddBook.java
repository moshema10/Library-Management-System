package com.example.librarian_module.test;

import com.example.librarian_module.sys.Account;
import com.example.librarian_module.sys.Author;
import com.example.librarian_module.sys.Book;
import com.example.librarian_module.sys.Librarian;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import com.example.librarian_module.sys.*;

import java.io.*;

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
 * The class TestAddBook is the Junit5 test for testing Add Book operation.
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

@TestMethodOrder(OrderAnnotation.class)
class TestAddBook {
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
                        "2314567", "2018", "300", "2", "Horror"),
                "Book #1 added");
        assertEquals(Book.StatusCode.DUPLICATE_ITEM_ID,
                lbr.addBook("001", "Duplicate book ID", "Dole", "John", "1",
                        "2347865GH", "2020", "250", "2", "Horror"),
                "Book #2 not added, duplicate book ID");
        assertEquals(Book.StatusCode.SUCCESS,
                lbr.addBook("002", "No time for fun", "Pan", "Peter", "1",
                        "8745231", "2021", "250", "1", "Default"),
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
    @DisplayName("Test read book data in RAM after adding books")
    void testReadBooksInRAM() {
        assertEquals(Account.LoginStatus.SUCCESS, Account.login("user", "abc"),
                "Login librarian 'user'");
        lbr = Librarian.getActiveLibrarian("user");
        assertNotNull(lbr, "Librarian 'user' is logged in");

        lbr.initReport();
        assertEquals("001|Very tough assignment|John Dole|Horror|1|2314567|2018|300|2|", lbr.getNextRec());
        assertEquals("002|No time for fun|Peter Pan|Generic|1|8745231|2021|250|1|", lbr.getNextRec());
        assertEquals("003|Tomorrow will be better|John Dole|Self-help|1|123456789|2022|600|3|", lbr.getNextRec());
        assertNull(lbr.getNextRec());
        lbr.closeReport();

        assertEquals(Account.LoginStatus.SUCCESS, Account.logout("user"),
                "Log out librarian  'user''");
    }

    @Test
    @Order(3)
    @DisplayName("Test read author data in RAM after adding books")
    void testReadAuthorsInRAM() {
        assertEquals("John Dole", Author.getAuthorName(1),
                "Get first author full name");
        assertEquals("Peter Pan", Author.getAuthorName(2),
                "Get second author full name");
        assertEquals("", Author.getAuthorName(3),
                "Only two authors");
    }

    @Test
    @Order(4)
    @DisplayName("Test read 'bookDB.txt'")
    void testReadBookDB() {
        try (BufferedReader br = new BufferedReader(new FileReader("bookDB.txt"))) {
            assertEquals("001|Very tough assignment|1|3|", br.readLine());
            assertEquals("002|No time for fun|2|12|", br.readLine());
            assertEquals("003|Tomorrow will be better|1|11|", br.readLine());
            assertNull(br.readLine());
        } catch (IOException e) {
            System.out.println("IO Exception: in TestAddBook");
        }
    }

    @Test
    @Order(5)
    @DisplayName("Test read 'bookeditionDB.txt'")
    void testReadBookEditionDB() {
        try (BufferedReader br = new BufferedReader(new FileReader("bookEditionDB.txt"))) {
            assertEquals("001|1|2314567|2018|300|", br.readLine());
            assertEquals("002|1|8745231|2021|250|", br.readLine());
            assertEquals("003|1|123456789|2022|600|", br.readLine());
            assertNull(br.readLine());
        } catch (IOException e) {
            System.out.println("IO Exception: in TestAddBook");
        }
    }

    @Test
    @Order(6)
    @DisplayName("Test read 'bookcopyDB.txt'")
    void testReadBookCopyDB() {
        try (BufferedReader br = new BufferedReader(new FileReader("bookcopyDB.txt"))) {
            assertEquals("001        1  1  1 null       null       null      ", br.readLine());
            assertEquals("001        1  2  1 null       null       null      ", br.readLine());
            assertEquals("002        1  1  1 null       null       null      ", br.readLine());
            assertEquals("003        1  1  1 null       null       null      ", br.readLine());
            assertEquals("003        1  2  1 null       null       null      ", br.readLine());
            assertEquals("003        1  3  1 null       null       null      ", br.readLine());
            assertNull(br.readLine());
        } catch (IOException e) {
            System.out.println("IO Exception: in TestAddBook");
        }
    }

    @Test
    @Order(7)
    @DisplayName("Test read 'authorDB.txt'")
    void testReadAuthorDB() {
        try (BufferedReader br = new BufferedReader(new FileReader("authorDB.txt"))) {
            assertEquals("1|Dole|John|", br.readLine());
            assertEquals("2|Pan|Peter|", br.readLine());
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
