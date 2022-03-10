package com.example.librarian_module.sys;

import java.time.LocalDate;
import java.time.Period;
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
 * The class BookCopy manages different copies of the books in the library system.
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

public class BookCopy {
    private String bookID;
    private int edition;
    private int copyID;
    private boolean available;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private String borrowerID;

    private static int borrowDays = 21;    // sign out book for 21 days

    /**
     * Create an empty BookCopy object.
     */
    public BookCopy() { }

    /**
     * Create a BookCopy object with the specified book ID, edition, and copy ID. Creating the object
     * does not mean it has been added to the library system.
     *
     * @param bid ID used as the key of the book.
     * @param ed An integer representing the edition of the book.
     * @param cid Copy ID of the book.
     */
    public BookCopy(String bid, String ed, int cid) {
        bookID = bid;
        edition = Integer.parseInt(ed);
        copyID = cid;
        available = true;
        issueDate = null;
        dueDate = null;
        borrowerID = null;
    }

    /**
     * Create a BookCopy object with the full set of parameters. Creating the object does not
     * mean it has been added to the library system.
     *
     * @param bid ID used as the key of the book.
     * @param ed An integer representing the edition of the book.
     * @param cid Copy ID of the book.
     * @param stat Login status of this copy of the book.
     * @param start Sign out date of the book.
     * @param due Due day of the book.
     * @param bwrid ID of the borrower of the book.
     */
    public BookCopy(String bid, String ed, int cid, boolean stat, LocalDate start,
                    LocalDate due, String bwrid) {
        bookID = bid;
        edition = Integer.parseInt(ed);
        copyID = cid;
        available = stat;
        issueDate = start;
        dueDate = due;
        borrowerID = bwrid;
    }

    /**
     * Issue a copy of the book to the specified borrower ID.
     *
     * @param copies An array list of copies of the book.
     * @param bwrid ID of the borrower.
     * @return An enum StatusCode representing the result of the issue book operation.
     */
    public static Book.StatusCode issue(ArrayList<BookCopy> copies, String bwrid) {
        int index = -1;
        for (int i = 0; i < copies.size(); i++) {
            if (copies.get(i).available) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            return Item.StatusCode.NO_AVAILABLE_COPY;
        }
        else {
            LocalDate today = LocalDate.now();
            BookCopy bkcpy = copies.get(index);
            bkcpy.available = false;
            bkcpy.issueDate = today;
            bkcpy.dueDate = today.plusDays(borrowDays);
            bkcpy.borrowerID = bwrid;
            // update the database file
            BookDB.updateCopy(bkcpy.bookID, bkcpy.edition, bkcpy.copyID, bkcpy.available,
                    bkcpy.issueDate, bkcpy.dueDate, bkcpy.borrowerID);
            // log the event to log file
            LibLogger.log(Level.INFO, "Issue book (bookID: " + bkcpy.bookID + ", edition: "
                    + bkcpy.edition +", copyID: " + bkcpy.copyID + ") to " + bkcpy.borrowerID);
            return Item.StatusCode.SUCCESS;
        }
    }

    /**
     * Return a copy of the book from the specified borrower ID.
     *
     * @param copies An array list of copies of the book.
     * @param bwrid ID of the borrower.
     * @return An enum StatusCode representing the result of the return book operation.
     */
    public static Book.StatusCode returnBack(ArrayList<BookCopy> copies, String bwrid) {
        int index = -1;
        for (int i = 0; i < copies.size(); i++) {
            if (bwrid.equals(copies.get(i).borrowerID)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            return Item.StatusCode.ITEM_NOT_SIGNED_OUT;
        }
        else {
            LocalDate today = LocalDate.now();
            BookCopy bkcpy = copies.get(index);
            bkcpy.available = true;
            bkcpy.issueDate = null;
            bkcpy.borrowerID = null;
            Period diff = Period.between(today, copies.get(index).dueDate);
            bkcpy.dueDate = null;
            BookDB.updateCopy(bkcpy.bookID, bkcpy.edition, bkcpy.copyID, bkcpy.available,
                    bkcpy.issueDate, bkcpy.dueDate, bkcpy.borrowerID);
            LibLogger.log(Level.INFO, "Return book (bookID: " + bkcpy.bookID + ", edition: "
                    + bkcpy.edition +", copyID: " + bkcpy.copyID + ") from " + bwrid);

            if (diff.getDays() >= 0) {
                return Item.StatusCode.SUCCESS;
            } else {
                return Item.StatusCode.ITEM_OVERDUE;
            }
        }
    }

    /**
     * Renew the copy of the book, i.e., extending the due date.
     */
    public void extend() {
        setDueDate(dueDate.plusDays(borrowDays));
    }

    /**
     * Set the due date of the copy of the book.
     *
     * @param date A LocalDate object used to set the new due date of the copy of the book.
     */
    public void setDueDate(LocalDate date) {
        dueDate = date;
    }
}
