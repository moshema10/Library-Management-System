package com.example.librarian_module.sys;

import java.util.ArrayList;
import java.util.logging.Level;

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
 * The class Book manages the books in the library system. It inherits the class Item. A book
 * can have one more editions (restricted to one for now). Each edition can have one or more
 * copies. Author of the book is saved as an integer representing the author ID (implemented as
 * a separate class Author). Genre is saved as an integer representing the genre ID (implemented
 * as a separate class BookGenre).
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

public class Book extends Item {
    private int authorID;
    private int genreID;
    private ArrayList<BookEdition> editions;

    private static int recptr;
    private static final String bookdb = "bookDB.txt";

    static {
        BookDB.load();
    }

    /**
     * Create an empty Book object.
     */
    public Book() {
    }

    /**
     * Create a Book object with the specified parameters. Creating the object does not mean it
     * has been added to the library system.
     *
     * @param bid   ID used as the key of the book.
     * @param tle   Title of the book.
     * @param lname Last name of the author of the book.
     * @param fname First name of the author of the book.
     * @param ed    An integer representing the edition of the book.
     * @param ISBN  International Standard Book Number of the book.
     * @param yr    Published year of the book.
     * @param pgs   Number of pages of the book.
     * @param cps   Number of copies of the book.
     * @param cat   Genre of the book.
     */
    public Book(String bid, String tle, String lname, String fname, String ed,
                String ISBN, String yr, String pgs, String cps, String cat) {
        super(bid, tle);
        authorID = Author.getAuthorID(lname, fname);
        genreID = BookGenre.getGenreID(cat);
        editions = new ArrayList<>();
        editions.add(new BookEdition(bid, ed, ISBN, yr, pgs, cps));
    }

    /**
     * Overloaded constructor to create a Book object with the specified parameters. Creating
     * the object does not mean it has been added to the library system.
     *
     * @param bid   ID used as the key of the book.
     * @param tle   Title of the book.
     * @param aid   Author ID of the book.
     * @param ed    An integer representing the edition of the book.
     * @param ISBN  International Standard Book Number of the book.
     * @param yr    Published year of the book.
     * @param pgs   Number of pages of the book.
     * @param cps   Number of copies of the book.
     * @param catid An integer representing the Genre ID of the book.
     */
    public Book(String bid, String tle, int aid, String ed, String ISBN, String yr,
                String pgs, String cps, int catid) {
        super(bid, tle);
        authorID = aid;
        genreID = catid;
        editions = new ArrayList<>();
        editions.add(new BookEdition(bid, ed, ISBN, yr, pgs, cps));
    }

    /**
     * Add the book with specified parameters to the library system.
     *
     * @param bid   ID used as the key of the book.
     * @param tle   Title of the book.
     * @param lname Last name of the author of the book.
     * @param fname First name of the author of the book.
     * @param ed    An integer representing the edition of the book.
     * @param ISBN  International Standard Book Number of the book.
     * @param yr    Published year of the book.
     * @param pgs   Number of pages of the book.
     * @param cps   Number of copies of the book.
     * @param cat   Genre of the book.
     * @return An enum type StatusCode representing the result of the add book operation.
     */
    public static StatusCode add(String bid, String tle, String lname, String fname, String ed,
                                 String ISBN, String yr, String pgs, String cps, String cat) {
        int index = findItem(bid);
        if (index == -1) {
            items.add(new Book(bid, tle, lname, fname, ed, ISBN, yr, pgs, cps, cat));
            BookDB.addBook(bid, tle, lname, fname, ed, ISBN, yr, pgs, cps, cat);
            LibLogger.log(Level.INFO, "Add book \"" + tle + "\" (ID: " + bid + ")");
            return StatusCode.SUCCESS;
        } else {
            return StatusCode.DUPLICATE_ITEM_ID;
        }
    }

    /**
     * Issue a book with the specified book ID and edition to a borrower with the specified ID.
     *
     * @param bid   ID used as the key of the book.
     * @param ed    An integer representing the edition of the book.
     * @param bwrid ID of the borrower of the book.
     * @return An enum type StatusCode representing the result of the issue book operation.
     */
    public static StatusCode issue(String bid, String ed, String bwrid) {
        int index = findBook(bid);
        if (index == -1) {
            return StatusCode.ITEM_NOT_FOUND;
        } else {
            Book bk = (Book) items.get(index);
            int index2 = BookEdition.findEdition(bk.editions, ed);
            if (index2 == -1) {
                return StatusCode.ITEM_NOT_FOUND;
            } else {
                StatusCode status = bk.editions.get(index2).issue(bwrid);
                return status;
            }
        }
    }

    /**
     * Return a book with the specified book ID and edition from the borrower with the specified ID.
     *
     * @param bid   ID used as the key of the book.
     * @param ed    An integer representing the edition of the book.
     * @param bwrid ID of the borrower of the book.
     * @return An enum type StatusCode representing the result of the return book operation.
     */
    public static StatusCode returnBack(String bid, String ed, String bwrid) {
        int index = findBook(bid);
        if (index == -1) {
            return StatusCode.ITEM_NOT_FOUND;
        } else {
            Book bk = (Book) items.get(index);
            int index2 = BookEdition.findEdition(bk.editions, ed);
            if (index2 == -1) {
                return StatusCode.ITEM_NOT_FOUND;
            } else {
                StatusCode status = bk.editions.get(index2).returnBack(bwrid);
                return status;
            }
        }
    }

    /**
     * Reset the record pointer of the books array list.
     */
    public static void resetRecPtr() {
        recptr = 0;
    }

    /**
     * Get data of the book referred to by the record pointer. Data fields are separated by "|".
     *
     * @return A string holding data of the book referred to by the record pointer of the
     * books array list.
     */
    public static String getNextRec() {
        String str = null;
        while (recptr < items.size()) {
            if (!(items.get(recptr) instanceof Book)) {
                recptr++;
            } else {
                Book bk = (Book) items.get(recptr);
                str = bk.getItemID() + "|" + bk.getTitle() + "|"
                        + Author.getAuthorName(bk.authorID) + "|"
                        + BookGenre.getGenreName(bk.genreID) + "|"
                        + bk.editions.get(0).getFields();
                recptr++;
                break;
            }
        }
        return str;
    }

    private static int findBook(String bid) {
        int index = -1;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) instanceof Book
                    && bid.equals(items.get(i).getItemID())) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * Get the reference of the array list that holds the different editions of the book. Currently,
     * it's assumed that a book has one edition.
     *
     * @return A reference that refers to the array list holding the different editions of the book.
     */
    public ArrayList<BookEdition> getEditions() {
        return editions;
    }
}
