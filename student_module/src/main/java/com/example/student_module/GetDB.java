package com.example.student_module;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class GetDB {
    public enum BookStatus {NO_BOOK, NOT_AVAILABLE, AVAILABLE}

    static ArrayList<BookCopyRecord> bookCopyRec = new ArrayList<>();
    static ArrayList<BookRecord> bookRecs = new ArrayList<>();
    static ArrayList<EditionRecord> editionRecs = new ArrayList<>();
    static ArrayList<AuthorRecord> authors = new ArrayList<>();
    static ArrayList<GenreRecord> genres = new ArrayList<>();

    static BookStatus hasbook(String bookID) {
        getBookCopyDB();
        BookStatus res = BookStatus.NO_BOOK;
        for (int i = 0; i < bookCopyRec.size(); i++) {
            BookCopyRecord rec = bookCopyRec.get(i);
            if (rec.bookID.equals(bookID)) {
                res = BookStatus.NOT_AVAILABLE;
            }
            if (rec.bookID.equals(bookID) && rec.available) {
                res = BookStatus.AVAILABLE;
                break;
            }
        }
        return res;
    }

    static ArrayList<Books> getBorrowedBooks(String studentID) {
        ArrayList<Books> rec = new ArrayList<>();
        getBookCopyDB();
        ArrayList<Books> books = new Books().list();
        for (int i = 0; i < bookCopyRec.size(); i++) {
            if (studentID.equals(bookCopyRec.get(i).borrowerID)) {
                for (int j = 0; j < books.size(); j++) {
                    if (bookCopyRec.get(i).bookID.equals(books.get(j).getBookID())) {
                        rec.add(books.get(j));
                        break;
                    }
                }
            }
        }
        return rec;
    }

    static void getBookCopyDB() {
        bookCopyRec.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("bookcopyDB.txt"))) {
            String record;
            while ((record = br.readLine()) != null) {
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
                bookCopyRec.add(new BookCopyRecord(id, ed, cid, status, begin, due, bwrid));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void getDB() {
        bookRecs.clear();
        editionRecs.clear();
        try (BufferedReader brBook = new BufferedReader(new FileReader("bookDB.txt"));
             BufferedReader brEdition = new BufferedReader(new FileReader("bookeditionDB.txt"));
             BufferedReader brAuthor = new BufferedReader(new FileReader("authorDB.txt"));
             BufferedReader brGenre = new BufferedReader(new FileReader("bookgenreDB.txt"))) {
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

            while ((record = brAuthor.readLine()) != null) {
                Scanner in = new Scanner(record).useDelimiter("\\|");
                authors.add(new AuthorRecord(in.nextInt(), in.next(), in.next()));
            }

            while ((record = brGenre.readLine()) != null) {
                Scanner in = new Scanner(record).useDelimiter("\\|");
                genres.add(new GenreRecord(in.nextInt(), in.next()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class BookCopyRecord {
    String bookID;
    int edition;
    int copyID;
    boolean available;
    LocalDate issueDate;
    LocalDate dueDate;
    String borrowerID;

    BookCopyRecord(String bid, int ed, int cid, boolean status, LocalDate begin, LocalDate due, String bwrid) {
        bookID = bid;
        edition = ed;
        copyID = cid;
        available = status;
        issueDate = begin;
        dueDate = due;
        borrowerID = bwrid;
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

class AuthorRecord {
    int authorID;
    String lastName;
    String firstName;

    AuthorRecord(int id, String lname, String fname) {
        authorID = id;
        lastName = lname;
        firstName = fname;
    }
}

class GenreRecord {
    int genreID;
    String genreName;

    GenreRecord(int id, String name) {
        genreID = id;
        genreName = name;
    }
}
