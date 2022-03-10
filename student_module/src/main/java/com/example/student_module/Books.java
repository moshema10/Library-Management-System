package com.example.student_module;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This a Book class which holds all the information of a book.
 */
public class Books {

    private String BookID;
    private String Title;
    private String Author;
    private String Genre;
    private String Edition;
    private String ISBN;
    private String Year;
    private String Pages;
    private String Copies;
    private ArrayList<Books> Book_lists = new ArrayList<>();
    private FileReader in;

    private Scanner AllBooks;

    Books() {
    }

    /**
     * The list() function stores all the book from file into the Book_lists.
     * @return It returns ArrayList of type Books. It returns the list of all the books in the program.
     */
    public ArrayList<Books> list() {
        GetDB.getBookCopyDB();
        GetDB.getDB();

        for (int i = 0; i < GetDB.bookRecs.size(); i++) {
            //System.out.println(line.length());
            Books abook = new Books();
            abook.BookID = GetDB.bookRecs.get(i).itemID;
            abook.Title = GetDB.bookRecs.get(i).title;
            int idx = GetDB.bookRecs.get(i).authorID - 1;
            abook.Author = GetDB.authors.get(idx).firstName + " " + GetDB.authors.get(idx).lastName;
            idx = GetDB.bookRecs.get(i).genreID - 1;
            abook.Genre = GetDB.genres.get(idx).genreName;
            abook.Edition = String.valueOf(GetDB.editionRecs.get(i).edition);
            abook.ISBN = GetDB.editionRecs.get(i).isbn;
            abook.Year = String.valueOf(GetDB.editionRecs.get(i).year);
            abook.Pages = String.valueOf(GetDB.editionRecs.get(i).pages);

            int copies = 0;
            for (int j = 0; j < GetDB.bookCopyRec.size(); j++) {
                if (abook.BookID.equals(GetDB.bookCopyRec.get(j).bookID)) {
                    copies++;
                }
            }
            abook.Copies = String.valueOf(copies);
            Book_lists.add(abook);
        }

        return Book_lists;
    }

    /**
     * This function checks if the argument BookID matches with the list of books.
     * @param m_id BookID that user provided
     * @return True if the m_id is found in the list of books.
     */

    public boolean getBookID(String m_id) {

        list();
        for (int i = 0; i < Book_lists.size(); i++) {
            if (Book_lists.get(i).BookID.equals(m_id)) {
                this.setTitle(Book_lists.get(i).Title);
                this.setAuthor(Book_lists.get(i).Author);
                this.setGenre(Book_lists.get(i).Genre);
                this.setISBN(Book_lists.get(i).ISBN);
                return true;

            }
        }
        return false;

    }


    /**
     * Getter() function
     * @return BookID
     */
    public String getBookID() {
        return BookID;
    }

    /**
     * Setter() Function
     * @param bookID value is assigned to Book.BookID.
     */

    public void setBookID(String bookID) {
        BookID = bookID;
    }
    /**
     * Getter() function
     * @return Book Title
     */

    public String getTitle() {
        return Title;
    }

    /**
     * Setter() Function
     * @param title value is assigned to Book.Title.
     */

    public void setTitle(String title) {
        Title = title;
    }
    /**
     * Getter() function
     * @return Book Author
     */
    public String getAuthor() {
        return Author;
    }

    /**
     * Setter() Function
     * @param author value is assigned to Book.Autor.
     */

    public void setAuthor(String author) {
        Author = author;
    }
    /**
     * Getter() function
     * @return Book Genre
     */
    public String getGenre() {
        return Genre;
    }

    /**
     * Setter() Function
     * @param genre value is assigned to Book.Genre.
     */

    public void setGenre(String genre) {
        Genre = genre;
    }
    /**
     * Getter() function
     * @return Book Edition
     */
    public String getEdition() {
        return Edition;
    }

    /**
     * Setter() Function
     * @param edition value is assigned to Book.Edition.
     */

    public void setEdition(String edition) {
        Edition = edition;
    }
    /**
     * Getter() function
     * @return Book ISBN Number
     */
    public String getISBN() {
        return ISBN;
    }
    /**
     * Setter() Function
     * @param ISBN value is assigned to Book.isbn.
     */
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
    /**
     * Getter() function
     * @return Book publishing year
     */
    public String getYear() {
        return Year;
    }
    /**
     * Setter() Function
     * @param year value is assigned to Book.year.
     */
    public void setYear(String year) {
        Year = year;
    }
    /**
     * Getter() function
     * @return Book's total pages.
     */
    public String getPages() {
        return Pages;
    }
    /**
     * Setter() Function
     * @param pages value is assigned to Book.pages.
     */
    public void setPages(String pages) {
        Pages = pages;
    }
    /**
     * Getter() function
     * @return Book number of copies
     */
    public String getCopies() {
        return Copies;
    }

    /**
     * Setter() Function
     * @param copies value is assigned to Book.Copies
     */

    public void setCopies(String copies) {
        Copies = copies;
    }


}
