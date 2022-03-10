package com.example.librarian_module.sys;

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
 * The class Account manages user accounts to control access to different functionality of
 * the library system. Each account is assigned different access level defining the
 * operations that user are authorized to perform. An account that has been inactive for a
 * specific time period will be automatically logged out.
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

public class Account {
    /**
     * The enum class LoginStatus defines the status of a login operation.
     *
     */
    public enum LoginStatus {SUCCESS, NO_ACCOUNT, BAD_PASSWORD, NOT_LOGGED_IN}

    /**
     * The enum class AccessLevel defines the access level of different type of actors in
     * the library system. It's in decreasing order of privilege.
     */
    public enum AccessLevel {MANAGER, LIBRARIAN, GRAD_STUDENT, UNDERGRAD_STUDENT, ALUMNI}

    private String userid;
    private String password;
    private AccessLevel level;
    private boolean status;
    private long lastAccessed;

    static ArrayList<Account> accounts = new ArrayList<>();
    private static long timeout = 30 * 60 * 1000;

    static {
        AccountDB.load();
    }

    /**
     * Create an empty user account object.
     */
    public Account() {
    }

    /**
     * Create a user account object with the specified user ID, password, and access level. Creating
     * the object does not mean it has been added to the library system.
     *
     * @param uid User ID used as the key of the account.
     * @param pwd Password used to log in the account.
     * @param lvl Access level of the account.
     */
    public Account(String uid, String pwd, AccessLevel lvl) {
        userid = uid;
        password = pwd;
        level = lvl;
        status = false;
        lastAccessed = 0;
    }

    /**
     * Log in a user with the specified user ID and password.
     *
     * @param uid User ID used as the key of the account.
     * @param pwd Password used to log in the account.
     * @return An enum type LoginStatus representing the result of the login operation.
     */
    public static LoginStatus login(String uid, String pwd) {
        Librarian.loadLibrarianDB();    // sync with Admin module
        int index = findAccount(uid);
        if (index == -1) {
            // account not found
            return LoginStatus.NO_ACCOUNT;
        } else if (!pwd.equals(accounts.get(index).password)) {
            // wrong password
            return LoginStatus.BAD_PASSWORD;
        } else {
            accounts.get(index).status = true;
            accounts.get(index).lastAccessed = System.currentTimeMillis();
            LibLogger.log(Level.INFO, "Account " + uid + " is logged in");
            return LoginStatus.SUCCESS;
        }
    }

    /**
     * Log in a user with the specified user ID.
     *
     * @param uid User ID used as the key of the account.
     * @return An enum type LoginStatus representing the result of the logout operation.
     */

    public static LoginStatus logout(String uid) {
        int index = findAccount(uid);
        if (index == -1) {
            return LoginStatus.NO_ACCOUNT;
        } if (!accounts.get(index).status) {
            return LoginStatus.NOT_LOGGED_IN;
        } else {
            accounts.get(index).status = false;
            accounts.get(index).lastAccessed = 0;
            LibLogger.log(Level.INFO, "Account " + uid + " is logged out");
            return LoginStatus.SUCCESS;
        }
    }

    /**
     * Check if the user with the specified user ID is logged in or not.
     *
     * @param uid User ID used as the key of the account.
     * @return A boolean value representing if the user is logged in or not.
     */
    public static boolean isLogin(String uid) {
        int index = findAccount(uid);
        if (index == -1) {
            return false;
        } else {
            return accounts.get(index).isLogin();
        }
    }

    /**
     * Check if the user is logged in or not.
     *
     * @return A boolean value representing if the user is logged in or not.
     */
    public boolean isLogin() {
        if (status == false
                || System.currentTimeMillis() - lastAccessed > timeout) {
            status = false;
            return false;
        } else {
            return true;
        }
    }

    /**
     * Check if the user with the specified user ID is authorized to perform management operations
     * of the library system.
     *
     * @param uid User ID used as the key of the account.
     * @return A boolean value representing if the user is authorized to perform management
     * operations of the library system.
     */
    public static boolean hasLibPrivilege(String uid) {
        int index = findAccount(uid);
        if (index == -1) {
            return false;
        } else {
            return accounts.get(index).hasLibPrivilege();
        }
    }

    /**
     * Check if the user is authorized to perform management operations of the library system.
     *
     * @return A boolean value representing if the user is authorized to perform management
     * operations of the library system.
     */
    public boolean hasLibPrivilege() {
        if (level.ordinal() > AccessLevel.LIBRARIAN.ordinal()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Add a user account with the specified user ID, password, and access level to the library system.
     *
     * @param uid User ID used as the key of the account.
     * @param pwd Password used to log in the account.
     * @param lvl Access level of the account.
     */
    public static void insertAccount(String uid, String pwd, AccessLevel lvl) {
        int index = findAccount(uid);
        if (index == -1) {
            accounts.add(new Account(uid, pwd, lvl));
            AccountDB.addAccount(uid, pwd, lvl);
            LibLogger.log(Level.INFO, "Add account " + uid);
        }
    }

    /**
     * Add the current account object to the library system.
     */
    public void insertAccount() {
        insertAccount(userid, password, level);
    }

    /**
     * Remove the account with the specified user ID from the library system.
     *
     * @param uid User ID used as the key of the account.
     */
    public static void deleteAccount(String uid) {
        int index = findAccount(uid);
        if (index != -1) {
            accounts.remove(index);
            AccountDB.deleteAccount(uid);
            LibLogger.log(Level.INFO, "Delete account " + uid);

        }
    }

    /**
     * Remove the current account object from the library system
     */
    public void deleteAccount() {
        deleteAccount(userid);
    }

    /**
     * Set the last accessed time of the account with the specified user ID
     *
     * @param uid  User ID used as the key of the account.
     * @param time A long integer used to modify the last accessed time of the account.
     */
    public static void setLastAccessed(String uid, long time) {
        int index = findAccount(uid);
        if (index != -1) {
            accounts.get(index).lastAccessed = time;
        }
    }

    private static int findAccount(String uid) {
        int index = -1;
        for (int i = 0; i < accounts.size(); i++) {
            if (uid.equals(accounts.get(i).userid)) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * Get the user ID of the current account object.
     *
     * @return A string representing the user ID of the account.
     */
    public String getUserid() {
        return userid;
    }

    /**
     * Get the password of the current account object.
     *
     * @return A string representing the password of the account.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password of the current account object.
     *
     * @param password A string used to modify the password of the account.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the access level of the current account object.
     *
     * @return An enum type AccessLevel representing the access level of the account.
     */
    public AccessLevel getLevel() {
        return level;
    }

    /**
     * Set the access level of the current account object.
     *
     * @param level An enum type AccessLevel used to modify the access level of the account.
     */
    public void setLevel(AccessLevel level) {
        this.level = level;
    }

    /**
     * Get the login status of the current account object.
     *
     * @return A boolean value representing the login status of the account
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * Set the login status of the current account object.
     *
     * @param status A boolean value used to modify the login status of the account.
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Get the last accessed time of the current account object.
     *
     * @return A long integer representing the last accessed time of the account.
     */
    public long getLastAccessed() {
        return lastAccessed;
    }

    /**
     * Set the last accessed time of the current account object.
     *
     * @param time A long integer used to modify the last accessed time of the account.
     */
    public void setLastAccessed(long time) {
        lastAccessed = time;
    }
}
