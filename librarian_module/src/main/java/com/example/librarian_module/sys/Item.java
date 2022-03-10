package com.example.librarian_module.sys;

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
 * The class Item is the base class of different types of items in the library system.
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

public class Item {
    /**
     * The enum class StatusCode defines the status of Add Book, Issue Book, and Return Book
     * operations of the library system.
     */
    public enum StatusCode {
        SUCCESS, NOT_LOGGED_IN, NO_PRIVILEGE, DUPLICATE_ITEM_ID, ITEM_NOT_FOUND,
        NO_AVAILABLE_COPY, ITEM_NOT_SIGNED_OUT, ITEM_OVERDUE
    }

    private String itemID;
    private String title;

    static ArrayList<Item> items = new ArrayList<>();

    /**
     * Create an empty item.
     */
    public Item() {
    }

    /**
     * Create an item with the specified item ID and name.
     *
     * @param id  ID used as the key of the item.
     * @param tle name if the item.
     */
    public Item(String id, String tle) {
        itemID = id;
        title = tle;
    }

    /**
     * Get the index of the item with specified ID in the items array list. It returns -1 if the
     * item is not found.
     *
     * @param id Unique ID of an item.
     * @return An integer representing the index of the item in the items array list.
     */
    static int findItem(String id) {
        int index = -1;
        for (int i = 0; i < items.size(); i++) {
            if (id.equals(items.get(i).itemID)) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * Get the unique ID of an item.
     *
     * @return An integer representing the ID of an item.
     */
    public String getItemID() {
        return itemID;
    }

    /**
     * Get the name of an item.
     *
     * @return A string representing the name of an item.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the name of an item with the specified value.
     *
     * @param title A string used to modify the name of an item.
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
