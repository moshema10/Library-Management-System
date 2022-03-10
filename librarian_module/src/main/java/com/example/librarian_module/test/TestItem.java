package com.example.librarian_module.test;

import com.example.librarian_module.sys.Item;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

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
 * The class TestItem is the Junit5 test for class Item.
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

@TestMethodOrder(OrderAnnotation.class)
class TestItem {
    static Item item;

    @BeforeAll
    static void setUp() {
        item = new Item();
        item = new Item("001", "Item name number #1");
    }

    @Test
    @Order(1)
    @DisplayName("Test get item ID and name")
    void testGetItem() {
        assertEquals("001", item.getItemID());
        assertEquals("Item name number #1", item.getTitle());
    }

    @Test
    @Order(2)
    @DisplayName("Test set name")
    void testSetItemName() {
        item.setTitle("Name has been changed");

        assertEquals("001", item.getItemID());
        assertEquals("Name has been changed", item.getTitle());
    }
}
