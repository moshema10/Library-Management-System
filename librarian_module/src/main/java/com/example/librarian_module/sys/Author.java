package com.example.librarian_module.sys;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
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
 * The class Author maintains a list of authors in the library system.
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

public class Author {
    private int authorID;
    private String lastName;
    private String firstName;

    private static ArrayList<Author> authors = new ArrayList<>();
    private static final String authordb = "authorDB.txt";

    static {
        try (BufferedReader br = new BufferedReader(new FileReader(authordb))) {
            String record;
            while ((record = br.readLine()) != null) {
                Scanner in = new Scanner(record).useDelimiter("\\|");
                authors.add(new Author(in.nextInt(), in.next(), in.next()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            LibLogger.log(e);
        }
    }

    /**
     * Create an empty Author object.
     */
    public Author() {
    }

    /**
     * Create an Author object with the specified last name and first name. Author ID is
     * auto-generated. Creating the object does not mean it has been added to the library system.
     *
     * @param lname Last name of the author.
     * @param fname First name of the author.
     */
    public Author(String lname, String fname) {
        authorID = authors.size() + 1;
        lastName = lname;
        firstName = fname;
    }

    /**
     * Create an Author object with the specified author ID, last name, and first name.
     *
     * @param aid   ID used as the key of the author.
     * @param lname Last name of the author.
     * @param fname First name of the author.
     */
    public Author(int aid, String lname, String fname) {
        authorID = aid;
        lastName = lname;
        firstName = fname;
    }

    /**
     * Get the author ID given the author's last name and first name.
     *
     * @param lname Last name of the author.
     * @param fname First name of the author.
     * @return An integer representing the ID of the author.
     */
    public static int getAuthorID(String lname, String fname) {
        int index = insertAuthor(lname, fname);
        return authors.get(index).authorID;
    }

    /**
     * Get the full name of the author of the specified author ID.
     *
     * @param id ID used as the key of the author.
     * @return A string representing the full name of the author.
     */
    public static String getAuthorName(int id) {
        if (id > 0 && id <= authors.size()) {
            return authors.get(id - 1).firstName + " " + authors.get(id - 1).lastName;
        } else {
            return "";
        }
    }

    /**
     * Add the author with the specified last name and first name to the library system if it does
     * not exist.
     *
     * @param lname Last name of the author.
     * @param fname First name of the author.
     * @return An integer representing the index of the author in the authors array list.
     */
    public static int insertAuthor(String lname, String fname) {
        int index = findAuthor(lname, fname);
        if (index == -1) {
            index = authors.size();
            Author au = new Author(lname, fname);
            authors.add(au);
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(authordb, true))) {
                bw.write(au.authorID + "|" + lname + "|" + fname + "|\n");
            } catch (IOException e) {
                e.printStackTrace();
                LibLogger.log(e);
            }
            LibLogger.log(Level.INFO, "Add author \"" + fname + " " + lname
                    + "\" (ID: " + au.authorID + ")");

        }
        return index;
    }

    /**
     * Add the current author object to the library system.
     *
     * @return An integer representing the index of the author in the authors array list.
     */
    public int insertAuthor() {
        return insertAuthor(lastName, firstName);
    }

    private static int findAuthor(String lname, String fname) {
        int index = -1;
        for (int i = 0; i < authors.size(); i++) {
            if (lname.equalsIgnoreCase(authors.get(i).lastName)
                    && fname.equalsIgnoreCase(authors.get(i).firstName)) {
                index = i;
                break;
            }
        }
        return index;
    }
}
