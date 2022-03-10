package com.example.librarian_module.sys;

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
 * The class AccountDB manages the user account database file and synchronizes its content
 * with the data in system memory. The database file has fixed-length records, so it is
 * easier and more efficient to update its data when needed.
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

public class AccountDB {
    private static final String accoutdb = "accountDB.txt";

    /**
     * Load the data in the user account database file into system memory at startup.
     */
    public static void load() {
        try (BufferedReader br = new BufferedReader(new FileReader(accoutdb))) {
            String record;
            while ((record = br.readLine()) != null) {
                Scanner in = new Scanner(record);
                // enum AccessLevel saved as int
                Account.accounts.add(new Account(in.next(), in.next(),
                        Account.AccessLevel.values()[in.nextInt()]));
            }
        } catch (IOException e) {
            e.printStackTrace();
            LibLogger.log(e);
        }
    }

    /**
     * Add the new user account with specified user ID, password, and access level to the user
     * account database file. Access level is saved as integer in the database file.
     *
     * @param uid User ID used as the key of the account.
     * @param pwd Password used to log in the account.
     * @param lvl Access level of the account.
     */
    public static void addAccount(String uid, String pwd, Account.AccessLevel lvl) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(accoutdb, true))) {
            bw.write(String.format("%-20s %-20s %2d%n", uid, pwd, lvl.ordinal()));
        } catch (IOException e) {
            e.printStackTrace();
            LibLogger.log(e);
        }
    }

    /**
     * Delete the user account with specified user ID from the user account database file.
     *
     * @param uid User ID used as the key of the account.
     */
    public static void deleteAccount(String uid) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(accoutdb))) {
            for (int i = 0; i < Account.accounts.size(); i++) {
                // enum AccessLevel saved as int
                Account account = Account.accounts.get(i);
                bw.write(String.format("%-20s %-20s %2d%n", account.getUserid(),
                        account.getPassword(), account.getLevel().ordinal()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            LibLogger.log(e);
        }
    }
}
