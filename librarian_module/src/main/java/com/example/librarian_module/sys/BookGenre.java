package com.example.librarian_module.sys;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
 * The class BookGenre manages the genres of books in the library system.
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

public class BookGenre {
    /**
     * A string array of the names of different genre types.
     */
    public static String[] genreTypes;

    private int genreID;
    private String genreName;

    private static ArrayList<BookGenre> bookGenres = new ArrayList<>();
    private static final String bookgenredb = "bookgenreDB.txt";

    static {
        try (BufferedReader br = new BufferedReader(new FileReader(bookgenredb))) {
            String record;
            while ((record = br.readLine()) != null) {
                Scanner in = new Scanner(record).useDelimiter("\\|");
                bookGenres.add(new BookGenre(in.nextInt(), in.next()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            LibLogger.log(e);
        }

        genreTypes = new String[bookGenres.size()];
        for (int i = 0; i < genreTypes.length; i++) {
            genreTypes[i] = bookGenres.get(i).genreName;
        }
    }

    private BookGenre() {
    }

    private BookGenre(int id, String name) {
        genreID = id;
        genreName = name;
    }

    /**
     * Get the ID of a book genre given the genre name.
     *
     * @param cat Name of a book genre.
     * @return An integer representing the unique ID of the book genre.
     */
    public static int getGenreID(String cat) {
        int index = findGenre(cat);
        return bookGenres.get(index).genreID;
    }

    /**
     * Get the genre name given genre ID.
     *
     * @param id An integer representing a genre ID.
     * @return A string representing the name of the genre.
     */
    public static String getGenreName(int id) {
        if (id > 0 && id <= bookGenres.size()) {
            return bookGenres.get(id - 1).genreName;
        } else {
            return "";
        }
    }

    private static int findGenre(String cat) {
        // last entry "Generic" reserved for not found genre
        int index = -1;
        for (int i = 0; i < bookGenres.size() - 1; i++) {
            if (cat.equalsIgnoreCase(bookGenres.get(i).genreName)) {
                index = i;
                break;
            }
        }
        if (index == -1) index = bookGenres.size() - 1;
        return index;
    }
}
