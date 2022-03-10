package com.example.librarian_module.test;

import com.example.librarian_module.sys.Account;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.io.IOException;
import java.io.PrintWriter;

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
 * The class TestAccount is the Junit5 test for class Account.
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

@TestMethodOrder(OrderAnnotation.class)
class TestAccount {
    static Account account;
    static String DBfile = "accountDB.txt";

    /**
     * Set up the data for tests
     */
    @BeforeAll
    static void setUp() {
        try {
            new PrintWriter(DBfile).close();
        } catch (IOException e) {
            System.out.println("IO Exception: " + DBfile);
        }
        account = new Account();
        account = new Account("user", "abc", Account.AccessLevel.LIBRARIAN);
        account.insertAccount();
        Account.insertAccount("user2", "abc2", Account.AccessLevel.LIBRARIAN);
    }

    @Test
    @Order(1)
    @DisplayName("Test login with different userid and password")
    void testLogin() {
        assertEquals(Account.LoginStatus.NO_ACCOUNT, Account.login("uid2", "pwd"),
                "Login with non-existent userid");
        assertEquals(Account.LoginStatus.BAD_PASSWORD, Account.login("user", "pwd"),
                "Login with wrong password");
        assertEquals(Account.LoginStatus.SUCCESS, Account.login("user", "abc"),
                "Login with correct password");
    }

    @Test
    @Order(2)
    @DisplayName("Test login status of different userid")
    void testStatus() {
        assertFalse(Account.isLogin("uid"), "Check non-existent userid");
        assertTrue(Account.isLogin("user"), "Check userid that's logged in");
        assertFalse(Account.isLogin("user2"), "Check userid that's not logged in");
    }

    @Test
    @Order(3)
    @DisplayName("Test auto-logout userid that has been inactive over time limit")
    void testSAutoTimeOut() {
        assertEquals(Account.LoginStatus.SUCCESS, Account.login("user2", "abc2"),
                "Login userid 'user2'");

        Account.setLastAccessed("user2", System.currentTimeMillis() - 60 * 60 * 1000);
        assertFalse(Account.isLogin("user2"), "Timeout a logged-in userid");
    }

    @Test
    @Order(4)
    @DisplayName("Test logout with different userid with different status")
    void testLogout() {
        assertEquals(Account.LoginStatus.NO_ACCOUNT, Account.logout("uid"),
                "Log out a non-existent userid");
        assertEquals(Account.LoginStatus.SUCCESS, Account.logout("user"),
                "Log out a user that's logged in");
        assertEquals(Account.LoginStatus.NOT_LOGGED_IN, Account.logout("user2"),
                "Log out a user that's not logged in");
    }

    @Test
    @Order(5)
    @DisplayName("Test delete account")
    void testDeleteAccount() {
        account.deleteAccount();
        assertEquals(Account.LoginStatus.NO_ACCOUNT, Account.login("user", "abc"),
                "Login with a deleted userid");
    }

    @AfterAll
    static void cleanUp() {
        try {
            new PrintWriter(DBfile).close();
        } catch (IOException e) {
            System.out.println("IO Exception: " + DBfile);
        }
    }
}
