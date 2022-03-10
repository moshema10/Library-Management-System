package com.example.librarian_module.sys;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import com.example.librarian_module.gui.AddBook;
import com.example.librarian_module.gui.IssueBook;
import com.example.librarian_module.gui.ReturnBook;
import com.example.librarian_module.gui.ViewBooks;
import javafx.application.Application;

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
 * The class Librarian manages the librarians in the library system.
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

public class Librarian {
    private String librarianID;
    private String userid;
    //private String password;
    //private String lastName;
    //private String firstName;

    private static final ArrayList<Librarian> librarians = new ArrayList<>();
    private static BufferedWriter bw;
    private static final String bookfile = "books.txt";
    private static final String libfile = "librarianDB.txt";

    static {
        loadLibrarianDB();
    }

    /**
     * Create an empty librarian.
     */
    public Librarian() {

    }

    /**
     * Create a librarian object with the specified parameters. Creating the object does not
     * mean it has been added to the system.
     *
     * @param lid Unique ID of a librarian.
     * @param uid User ID of login account.
     * @param pwd Password of the login account.
     */
    public Librarian(String lid, String uid, String pwd) {
        librarianID = lid;
        userid = uid;
    }

    /**
     * Add the librarian with the specified parameters to the library system.
     *
     * @param lid Unique ID of a librarian.
     * @param uid User ID of login account.
     * @param pwd Password of the login account.
     * @param lvl Access level of the librarian.
     */
    public static void insertLibrarian(String lid, String uid, String pwd, Account.AccessLevel lvl) {
        int index = -1;
        for (int i = 0; i < librarians.size(); i++) {
            if (lid.equals(librarians.get(i).librarianID)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            librarians.add(new Librarian(lid, uid, pwd));
            Account.insertAccount(uid, pwd, lvl);
        }
    }

    /**
     * Delete the librarian with the specified librarian ID.
     *
     * @param lid Unique ID of a librarian.
     */
    public static void deleteLibrarian(String lid) {
        int index = -1;
        for (int i = 0; i < librarians.size(); i++) {
            if (lid.equals(librarians.get(i).librarianID)) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            librarians.remove(index);
            Account.deleteAccount(librarians.get(index).userid);
        }
    }

    /**
     * Get the librarian object who is logged in with the specified userid.
     *
     * @param uid User ID of login account of a librarian.
     * @return A Librarian object representing the logged in librarian.
     */
    public static Librarian getActiveLibrarian(String uid) {
        int index = -1;
        for (int i = 0; i < librarians.size(); i++) {
            if (uid.equals(librarians.get(i).userid)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            return null;
        } else if (Account.isLogin(librarians.get(index).userid)) {
            return librarians.get(index);
        } else {
            return null;
        }
    }

    /**
     * load the data in librarian database file into RAM.
     */
    public static void loadLibrarianDB() {
        librarians.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(libfile))) {
            String record;
            while ((record = br.readLine()) != null) {
                Scanner in = new Scanner(record).useDelimiter("\\|");;
                String lid = in.next();
                insertLibrarian(lid, lid, in.next(), Account.AccessLevel.LIBRARIAN);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LibLogger.log(e);
        }
    }

    /**
     * Launch the add book Gui.
     *
     * @return An enum StatusCode representing the result of the operation.
     */
    public Book.StatusCode addBookGui() {
        if (!Account.isLogin(userid)) {
            return Book.StatusCode.NOT_LOGGED_IN;
        } else if (!Account.hasLibPrivilege(userid)) {
            return Item.StatusCode.NO_PRIVILEGE;
        } else {
            // use this overloaded lauch(), else won't work
            Application.launch(AddBook.class, userid);
            return Book.StatusCode.SUCCESS;
        }
    }

    /**
     * Add a book with the specified parameters to the library system. It calls Book.add()
     * to perform the actual operation.
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
    public Book.StatusCode addBook(String bid, String tle, String lname, String fname, String ed,
                                   String ISBN, String yr, String pgs, String cps, String cat) {
        if (!Account.isLogin(userid)) {
            return Book.StatusCode.NOT_LOGGED_IN;
        } else if (!Account.hasLibPrivilege(userid)) {
            return Item.StatusCode.NO_PRIVILEGE;
        } else {
            return Book.add(bid, tle, lname, fname, ed, ISBN, yr, pgs, cps, cat);
        }
    }

    /**
     * Launch the view book Gui.
     *
     * @return An enum StatusCode representing the result of the operation.
     */
    public Book.StatusCode viewBooksGui() {
        if (!Account.isLogin(userid)) {
            return Book.StatusCode.NOT_LOGGED_IN;
        } else if (!Account.hasLibPrivilege(userid)) {
            return Item.StatusCode.NO_PRIVILEGE;
        } else {
            // use this overloaded lauch(), else won't work
            Application.launch(ViewBooks.class, userid);
            return Book.StatusCode.SUCCESS;
        }
    }

    /**
     * Launch the issue book Gui.
     *
     * @return An enum StatusCode representing the result of the operation.
     */
    public Book.StatusCode issueBookGui() {
        if (!Account.isLogin(userid)) {
            return Book.StatusCode.NOT_LOGGED_IN;
        } else if (!Account.hasLibPrivilege(userid)) {
            return Item.StatusCode.NO_PRIVILEGE;
        } else {
            Application.launch(IssueBook.class, userid);
            return Book.StatusCode.SUCCESS;
        }
    }

    /**
     * Issue a book with the specified book ID and edition to a borrower with the specified ID.
     * It calls Book.issue() to perform the actual operation.
     *
     * @param bid   ID used as the key of the book.
     * @param ed    An integer representing the edition of the book.
     * @param bwrid ID of the borrower of the book.
     * @return An enum type StatusCode representing the result of the issue book operation.
     */
    public Book.StatusCode issueBook(String bid, String ed, String bwrid) {
        if (!Account.isLogin(userid)) {
            return Book.StatusCode.NOT_LOGGED_IN;
        } else if (!Account.hasLibPrivilege(userid)) {
            return Item.StatusCode.NO_PRIVILEGE;
        } else {
            return Book.issue(bid, ed, bwrid);
        }
    }

    /**
     * Launch the return book Gui.
     *
     * @return An enum StatusCode representing the result of the operation.
     */
    public Book.StatusCode returnBookGui() {
        if (!Account.isLogin(userid)) {
            return Book.StatusCode.NOT_LOGGED_IN;
        } else if (!Account.hasLibPrivilege(userid)) {
            return Item.StatusCode.NO_PRIVILEGE;
        } else {
            Application.launch(ReturnBook.class, userid);
            return Book.StatusCode.SUCCESS;
        }
    }

    /**
     * Return a book with the specified book ID and edition from the borrower with the
     * specified ID. It calls Book.returnBack() to perform the actual operation.
     *
     * @param bid   ID used as the key of the book.
     * @param ed    An integer representing the edition of the book.
     * @param bwrid ID of the borrower of the book.
     * @return An enum type StatusCode representing the result of the return book operation.
     */
    public Book.StatusCode returnBook(String bid, String ed, String bwrid) {
        if (!Account.isLogin(userid)) {
            return Book.StatusCode.NOT_LOGGED_IN;
        } else if (!Account.hasLibPrivilege(userid)) {
            return Item.StatusCode.NO_PRIVILEGE;
        } else {
            return Book.returnBack(bid, ed, bwrid);
        }
    }

    /**
     * Preparation to write book data to book data file.
     */
    public void initReport() {
        Book.resetRecPtr();
        try {
            bw = new BufferedWriter(new FileWriter(bookfile));
            String formatStr = "%-8s %-30s %-20s %-18s %-10s %-18s %-8s %-8s %-8s%n";
            bw.write(String.format(formatStr, "BookID", "Title", "Author", "Genre",
                    "Edition", "ISBN", "Year", "Pages", "Copies"));
        } catch (IOException e) {
            e.printStackTrace();
            LibLogger.log(e);
        }
    }

    /**
     * Get book data referred to by the record pointer of the books array list. The fields are
     * separated by "|". It also writes the book data to the book data file.
     *
     * @return A string representing the book data.
     */
    public String getNextRec() {
        String record = Book.getNextRec();
        try {
            if (record != null) {
                String formatStr = "%-8s %-30s %-20s %-18s %-10s %-18s %-8s %-8s %-8s%n";
                Scanner in = new Scanner(record).useDelimiter("\\|");
                bw.write(String.format(formatStr, in.next(), in.next(), in.next(), in.next(),
                        in.next(), in.next(), in.next(), in.next(), in.next()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            LibLogger.log(e);
        }
        return record;
    }

    /**
     * Close the book data file.
     */
    public void closeReport() {
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            LibLogger.log(e);
        }
    }
}
