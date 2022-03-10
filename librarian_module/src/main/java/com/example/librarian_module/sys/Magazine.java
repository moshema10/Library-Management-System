package com.example.librarian_module.sys;

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
 * The class Magazine manages the magazines in the library system. It inherits the class Item.
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

public class Magazine extends Item {
    private int editorID;
    private int genreID;
    private int year;
    private int month;
    private String isbn;

    /**
     * Create an empty magazine.
     */
    public Magazine() {
    }

    /**
     * Create a Magazine object with the specified parameters. Creating the object does not mean
     * it has been added to the library system.
     *
     * @param mid   Unique magazine ID.
     * @param tle   Name of the magazine.
     * @param lname Last name of the editor.
     * @param fname First name of the editor.
     * @param yr    Year of the magazine.
     * @param mth   Month of the magazine.
     * @param ISBN  International Standard Book Number of the magazine.
     * @param cat   Genre of the magazine.
     */
    public Magazine(String mid, String tle, String lname, String fname, String yr, String mth,
                    String ISBN, String cat) {
        super(mid, tle);
        editorID = Author.getAuthorID(lname, fname);
        genreID = BookGenre.getGenreID(cat);
        year = Integer.parseInt(yr);
        month = Integer.parseInt(mth);
        isbn = ISBN;
    }

    /**
     * Add the magazine with the specified parameters to the library system.
     *
     * @param mid   Unique magazine ID.
     * @param tle   Name of the magazine.
     * @param lname Last name of the editor.
     * @param fname First name of the editor.
     * @param yr    Year of the magazine.
     * @param mth   Month of the magazine.
     * @param ISBN  International Standard Book Number of the magazine.
     * @param cat   Genre of the magazine.
     * @return An enum StatusCode representing the result of the add magazine operation.
     */
    public static StatusCode add(String mid, String tle, String lname, String fname,
                                 String yr, String mth, String ISBN, String cat) {
        int index = findItem(mid);
        if (index == -1) {
            items.add(new Magazine(mid, tle, lname, fname, yr, mth, ISBN, cat));
            return StatusCode.SUCCESS;
        } else {
            return StatusCode.DUPLICATE_ITEM_ID;
        }
    }
}
