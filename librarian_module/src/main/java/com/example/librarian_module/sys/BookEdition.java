package com.example.librarian_module.sys;

import java.time.LocalDate;
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
 * The class BookEdition manages different editions of the books in the library system.
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

public class BookEdition {
    private String bookID;
    private int edition;
    private String isbn;
    private int year;
    private int pages;
    private ArrayList<BookCopy> copies;

    /**
     * Create an empty BookEdition object.
     */
    public BookEdition() {
    }

    /**
     * Create a BookEdition object with the specified parameters. It generates an array list of
     * book copies based on the number of copies passed in.
     *
     * @param bid  ID used as the key of the book.
     * @param ed   An integer representing the edition of the book.
     * @param ISBN International Standard Book Number of the book.
     * @param yr   Published year of the book.
     * @param pgs  Number of pages of the book.
     * @param cps  Number of copies of the book.
     */
    public BookEdition(String bid, String ed, String ISBN, String yr, String pgs, String cps) {
        bookID = bid;
        edition = Integer.parseInt(ed);
        isbn = ISBN;
        year = Integer.parseInt(yr);
        pages = Integer.parseInt(pgs);

        copies = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(cps); i++) {
            BookCopy bkcpy = new BookCopy(bid, ed, i + 1);
            copies.add(new BookCopy(bid, ed, i + 1));
            BookDB.addNewCopy(bid, ed, i + 1);
        }
    }

    /**
     * Issue this edition of a book to the borrower with specified ID.
     *
     * @param bwrid ID of the borrower.
     * @return An enum StatusCode representing the result of the issue book operation.
     */
    public Book.StatusCode issue(String bwrid) {
        return BookCopy.issue(copies, bwrid);
    }

    /**
     * Return this edition of a book from the borrower with specified ID.
     *
     * @param bwrid ID of the borrower.
     * @return An enum StatusCode representing the result of the return book operation.
     */
    public Book.StatusCode returnBack(String bwrid) {
        return BookCopy.returnBack(copies, bwrid);
    }

    /**
     * Find the index of this edition of a book in the editions array list of the book.
     *
     * @param eds Array list that holds all editions of a book.
     * @param ed  An integer that holds the edition of the book.
     * @return An integer representing the index of this edition of a book.
     */
    public static int findEdition(ArrayList<BookEdition> eds, String ed) {
        int index = -1;
        for (int i = 0; i < eds.size(); i++) {
            if (eds.get(i).edition == Integer.parseInt(ed)) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * Get the data of this edition of a book. Data fields are separated by "|".
     *
     * @return A string containing the data of this edition of a book.
     */
    public String getFields() {
        return edition + "|" + isbn + "|" + year + "|" + pages + "|"
                + copies.size() + "|";
    }

    /**
     * Add the book copy to the book copies array list of the book edition of a book.
     *
     * @param bid   ID used as the key of the book.
     * @param ed    An integer representing the edition of the book.
     * @param cid   Copy ID of this copy of the book.
     * @param stat  Login status of this copy of the book.
     * @param begin Sign out date of the book.
     * @param due   Due day of the book.
     * @param bwrid ID of the borrower of the book.
     */
    public void addCopy(String bid, String ed, int cid, boolean stat, LocalDate begin,
                        LocalDate due, String bwrid) {
        copies.add(new BookCopy(bid, ed, cid, stat, begin, due, bwrid));

    }
}
