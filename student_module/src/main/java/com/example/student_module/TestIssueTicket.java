package com.example.student_module;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
class TestIssueTicket {
    static Student student1, student2;
    static Books abook;
    static String book1 = "1001";
    static String book2 = "1002";
    static String book3 = "1005";

    @BeforeAll
    static void setUp() {
        try {
            new PrintWriter("requestDB.txt").close();
        } catch (IOException e) {
            System.out.println("IO Exception: requestDB.txt");
        }
        abook = new Books();
        student1 = new Student();
        student1.setStudentId("1234");
        student2 = new Student();
        student2.setStudentId("Non-existing");
    }

    @Test
    @Order(1)
    @DisplayName("Test validate student")
    void testValidateStudent() {
        assertTrue(Student.validateID(student1.getStudentId()));
        assertFalse(Student.validateID(student2.getStudentId()));
    }

    @Test
    @Order(2)
    @DisplayName("Test validate book")
    void testBookExists() {
        assertTrue(abook.getBookID(book1));
        assertTrue(abook.getBookID(book2));
        assertFalse(abook.getBookID(book3));
    }

    @Test
    @Order(3)
    @DisplayName("Test if a book is available for loan")
    void testHasBook() {
        assertEquals(GetDB.BookStatus.AVAILABLE, GetDB.hasbook(book1));
        assertEquals(GetDB.BookStatus.NOT_AVAILABLE, GetDB.hasbook(book2));
    }

    @Test
    @Order(4)
    @DisplayName("Test place request")
    void testPlaceRequest() {
        assertFalse(student1.checkStatus(book1));
        student1.writeRequestFile(book1);
        assertTrue(student1.checkStatus(book1));
    }

    @AfterAll
    static void cleanUp() {
        try {
            new PrintWriter("requestDB.txt").close();
        } catch (IOException e) {
            System.out.println("IO Exception: requestDB.txt");
        }
    }
}
