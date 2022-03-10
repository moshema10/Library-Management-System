package com.example.librarian_module.sys;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

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
 * The class LibLogger write logging data of the library system to a log file.
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

public class LibLogger {
    private final static Logger log = Logger.getLogger(LibLogger.class.getName());
    private final static String LoggerName = Logger.GLOBAL_LOGGER_NAME;
    private static final String logfile = "librarian.log";

    /**
     * Write the specified logging data of the library system to a log file.
     *
     * @param level Logging level of the log message to be written.
     * @param msg   Log message to be written.
     */
    public static void log(Level level, String msg) {
        try {
            FileHandler fh = new FileHandler(System.getProperty("user.dir")
                    + System.getProperty("file.separator") + logfile, true);
            log.addHandler(fh);
            SimpleFormatter sf = new SimpleFormatter();
            fh.setFormatter(sf);
            log.log(level, msg);
            fh.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.log(Level.WARNING, "Exception :: ", e);
        }
    }

    /**
     * Overloaded logging method to log exception events.
     *
     * @param exc Exception thrown by the operations of the library system.
     */
    public static void log(Exception exc) {
        try {
            FileHandler fh = new FileHandler(System.getProperty("user.dir")
                    + System.getProperty("file.separator") + logfile, true);
            log.addHandler(fh);
            SimpleFormatter sf = new SimpleFormatter();
            fh.setFormatter(sf);
            log.log(Level.WARNING, "Exception :: ", exc);
            fh.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.log(Level.WARNING, "Exception :: ", e);
        }
    }
}
