package com.example.student_module;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
class TestStudent {
    static Student student;

    @BeforeAll
    static void setUp() {
        try {
            new PrintWriter("requestDB.txt").close();
        } catch (IOException e) {
            System.out.println("IO Exception: requestDB.txt");
        }
        student = new Student();
    }

    @Test
    @Order(1)
    @DisplayName("Test set/get attributes of the student")
    void testGetValue() {
        student.setStudentId("test");
        assertEquals("test", student.getStudentId());
    }

    @Test
    @Order(2)
    @DisplayName("Test validate student")
    void testValidateStudent() {
        assertTrue(student.validateID("1234"));
        assertTrue(student.validateID("6789"));
        assertFalse(student.validateID("4321"));
        assertFalse(student.validateID("9876"));
    }

    @Test
    @Order(3)
    @DisplayName("Test place request")
    void testPlaceRequest() {
        student.writeRequestFile("1001");
        assertTrue(student.checkStatus("1001"));
        assertFalse(student.checkStatus("1002"));
        student.writeRequestFile("1002");
        assertTrue(student.checkStatus("1002"));
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
