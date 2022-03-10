package com.example.librarian_module.sys;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

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
 * The class BookDB manages book database files and synchronizes their content with the data in
 * system memory. There are three database files: book, book edition, and book copy. The first
 * two files have variable-length records to save space. The book copy file have fixed-length
 * records, so it is easier and more efficient to update its data when needed.
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

public class BookDB {
    private static final String bookdb = "bookDB.txt";
    private static final String bookeditiondb = "bookeditionDB.txt";
    private static final String bookcopydb = "bookcopyDB.txt";

    private static ArrayList<BookRecord> bookRecs = new ArrayList<>();
    private static ArrayList<EditionRecord> editionRecs = new ArrayList<>();
    private static ArrayList<CopyRecord> copyRecs = new ArrayList<>();

    /**
     * Load the data in the book, book edition, book copy database files into system memory
     * at startup.
     */
    public static void load() {
        try (BufferedReader brBook = new BufferedReader(new FileReader(bookdb));
             BufferedReader brEdition = new BufferedReader(new FileReader(bookeditiondb));
             BufferedReader brCopy = new BufferedReader(new FileReader(bookcopydb))) {
            String record;
            while ((record = brBook.readLine()) != null) {
                Scanner in = new Scanner(record).useDelimiter("\\|");
                bookRecs.add(new BookRecord(in.next(), in.next(), in.nextInt(), in.nextInt()));
            }

            while ((record = brEdition.readLine()) != null) {
                Scanner in = new Scanner(record).useDelimiter("\\|");
                editionRecs.add(new EditionRecord(in.next(), in.nextInt(), in.next(), in.nextInt(),
                        in.nextInt()));
            }

            while ((record = brCopy.readLine()) != null) {
                Scanner in = new Scanner(record);
                String id = in.next();
                int ed = in.nextInt();
                int cid = in.nextInt();
                boolean status = in.nextInt() == 1;
                String str = in.next();
                LocalDate begin = "null".equals(str) ? null : LocalDate.parse(str);
                str = in.next();
                LocalDate due = "null".equals(str) ? null : LocalDate.parse(str);
                str = in.next();
                String bwrid = "null".equals(str) ? null : str;
                copyRecs.add(new CopyRecord(id, ed, cid, status, begin, due, bwrid));
            }
        } catch (IOException e) {
            e.printStackTrace();
            LibLogger.log(e);
        }

        for (int i = 0; i < bookRecs.size(); i++) {
            // assume a book has one edition for now
            int index = -1;
            for (int j = 0; j < editionRecs.size(); j++) {
                if (bookRecs.get(i).itemID.equals(editionRecs.get(j).bookID)) {
                    index = j;
                    break;
                }
            }

            BookRecord bkrec = bookRecs.get(i);
            EditionRecord edrec = editionRecs.get(index);
            Book book = new Book(bkrec.itemID, bkrec.title, bkrec.authorID,
                    Integer.toString(edrec.edition), edrec.isbn, Integer.toString(edrec.year),
                    Integer.toString(edrec.pages), "0", bkrec.genreID);
            Item.items.add(book);

            // assume a book has one edition for now
            BookEdition edition = book.getEditions().get(0);

            for (int j = 0; j < copyRecs.size(); j++) {
                CopyRecord cpyrec = copyRecs.get(j);
                if (cpyrec.bookID.equals(bkrec.itemID)
                        && cpyrec.edition == edrec.edition) {
                    edition.addCopy(bkrec.itemID, Integer.toString(edrec.edition), cpyrec.copyID,
                            cpyrec.available, cpyrec.issueDate, cpyrec.dueDate, cpyrec.borrowerID);
                }
            }
        }
    }

    /**
     * Add a book with specified parameters to the database files.
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
    public static void addBook(String bid, String tle, String lname, String fname, String ed,
                               String ISBN, String yr, String pgs, String cps, String cat) {
        int aid = Author.getAuthorID(lname, fname);
        int gid = BookGenre.getGenreID(cat);

        try (BufferedWriter bwBook = new BufferedWriter(new FileWriter(bookdb, true));
             BufferedWriter bwEdition = new BufferedWriter(new FileWriter(bookeditiondb, true))) {
            bwBook.write(bid + "|" + tle + "|" + aid + "|" + gid + "|\n");
            bwEdition.write(bid + "|" + ed + "|" + ISBN + "|" + yr + "|" + pgs + "|\n");
        } catch (IOException e) {
            e.printStackTrace();
            LibLogger.log(e);
        }
    }

    /**
     * Add a new copy of a book with the specified book ID, edition, and Copy ID to the database file.
     *
     * @param bid ID used as the key of the book.
     * @param ed  An integer representing the edition of the book.
     * @param cid Copy ID of this copy of the book.
     */
    public static void addNewCopy(String bid, String ed, int cid) {
        copyRecs.add(new CopyRecord(bid, Integer.parseInt(ed), cid, true, null,
                null, null));

        try (BufferedWriter bwCopy = new BufferedWriter(new FileWriter(bookcopydb, true))) {
            bwCopy.write(String.format("%-10s %-2s %-2s %-1s %-10s %-10s %-10s%n", bid, ed, cid,
                    "1", "null", "null", "null"));
        } catch (IOException e) {
            e.printStackTrace();
            LibLogger.log(e);
        }
    }

    /**
     * Update the data of the copy of the book with the specified parameters in the database.
     *
     * @param bid    ID used as the key of the book.
     * @param ed     An integer representing the edition of the book.
     * @param cid    Copy ID of this copy of the book.
     * @param status Login status of this copy of the book.
     * @param begin  Sign out date of the book.
     * @param due    Due day of the book.
     * @param bwrid  ID of the borrower of the book.
     */
    public static void updateCopy(String bid, int ed, int cid, boolean status,
                                  LocalDate begin, LocalDate due, String bwrid) {
        String str = String.format("%-10s %-2s %-2s %-1s %-10s %-10s %-10s%n", bid, ed, cid,
                status ? "1" : "0",
                begin == null ? "null" : begin.toString(),
                due == null ? "null" : due.toString(),
                bwrid == null ? "null" : bwrid);
        int reclen = str.length();

        int offset = -1;
        for (int i = 0; i < copyRecs.size(); i++) {
            if (bid.equals(copyRecs.get(i).bookID) && ed == copyRecs.get(i).edition
                    && cid == copyRecs.get(i).copyID) {
                offset = i;
                break;
            }
        }

        try (RandomAccessFile raf = new RandomAccessFile(bookcopydb, "rw")) {
            raf.seek(offset * reclen);
            raf.write(str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            LibLogger.log(e);
        }
    }
}

/**
 * The class BookRecord specifies the fields of a record in the book database file.
 */
class BookRecord {
    String itemID;
    String title;
    int authorID;
    int genreID;

    /**
     * Create a book record with the specified parameters.
     *
     * @param id  ID of the book.
     * @param tle Title of the book.
     * @param aid Author ID of the book.
     * @param gid An integer representing the Genre ID of the book.
     */
    BookRecord(String id, String tle, int aid, int gid) {
        itemID = id;
        title = tle;
        authorID = aid;
        genreID = gid;
    }
}

/**
 * The class BookRecord specifies the fields of a record in the book edition database file.
 */
class EditionRecord {
    String bookID;
    int edition;
    String isbn;
    int year;
    int pages;

    /**
     * Create a book edition record with the specified parameters.
     *
     * @param bid  ID of the book.
     * @param ed   An integer representing the edition of the book.
     * @param ISBN International Standard Book Number of the book.
     * @param yr   Published year of the book.
     * @param pgs  Number of pages of the book.
     */
    EditionRecord(String bid, int ed, String ISBN, int yr, int pgs) {
        bookID = bid;
        edition = ed;
        isbn = ISBN;
        year = yr;
        pages = pgs;
    }
}

/**
 * The class BookRecord specifies the fields of a record in the book copy database file.
 */
class CopyRecord {
    String bookID;
    int edition;
    int copyID;
    boolean available;
    LocalDate issueDate;
    LocalDate dueDate;
    String borrowerID;

    /**
     * Create a book copy record with the specified parameters.
     *
     * @param bid    ID of the book.
     * @param ed     An integer representing the edition of the book.
     * @param cid    Copy ID of the book.
     * @param status Login status of this copy of the book.
     * @param begin  Sign out date of the book.
     * @param due    Due day of the book.
     * @param bwrid  ID of the borrower of the book.
     */
    CopyRecord(String bid, int ed, int cid, boolean status, LocalDate begin, LocalDate due, String bwrid) {
        bookID = bid;
        edition = ed;
        copyID = cid;
        available = status;
        issueDate = begin;
        dueDate = due;
        borrowerID = bwrid;
    }
}
