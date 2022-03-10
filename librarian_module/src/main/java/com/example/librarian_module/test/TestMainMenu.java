package com.example.librarian_module.test;

import com.example.librarian_module.gui.MainMenu;
import com.example.librarian_module.sys.Account;
import com.example.librarian_module.sys.LibLogger;
import javafx.application.Application;

import java.io.*;
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
 * The class TestMainMenu launches the main GUI of the Librarian module of library system.
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

public class TestMainMenu {
    /**
     * The main method that launches the GUI of the Librarian module of library system.
     *
     * @param args Unused.
     */
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("librarianDB.txt"));
            BufferedWriter bw = new BufferedWriter(new FileWriter("librarianDB.txt", true))) {
                String record;
                boolean found = false;
                while ((record = br.readLine()) != null) {
                    Scanner in = new Scanner(record).useDelimiter("\\|");
                    if ("user".equals(in.next())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    bw.write("user|abc|NoName|NoName\n");
                }
        } catch (IOException e) {
            e.printStackTrace();
            LibLogger.log(e);
        }

        Account.insertAccount("user2", "abc", Account.AccessLevel.GRAD_STUDENT);
        Application.launch(MainMenu.class);
    }
}
