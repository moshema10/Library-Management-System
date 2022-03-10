package com.example.admin_module;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(OrderAnnotation.class)
class TestLibrarians {
    static Librarians lbr;

    @BeforeAll
    static void setUp() {
        lbr = new Librarians();
        lbr = new Librarians("rupin", "admin", "Sharma", "Rupin");
    }

    @Test
    @Order(1)
    @DisplayName("Test get attributes of the librarian")
    void testGetValue() {
        assertEquals("rupin", lbr.getId());
        assertEquals("admin", lbr.getPassword());
        assertEquals("Sharma", lbr.getLastName());
        assertEquals("Rupin", lbr.getFirstName());
    }

    @Test
    @Order(2)
    @DisplayName("Test set attributes of the librarian")
    void testSetValue() {
        lbr.setPassword("abc");
        lbr.setLastName("Newlastname");
        lbr.setFirstName("Newfirstname");
        assertEquals("rupin", lbr.getId());
        assertEquals("abc", lbr.getPassword());
        assertEquals("Newlastname", lbr.getLastName());
        assertEquals("Newfirstname", lbr.getFirstName());
    }
}
