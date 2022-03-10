package com.example.librarian_module.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.example.librarian_module.sys.Account;

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
 * The class Logout is the GUI for logging out a user.
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

public class Logout extends Application {
    /**
     * The overloaded main entry point of the application.
     *
     * @param stage The default stage of the JavaFX application.
     */
    @Override
    public void start(Stage stage) {
        // get userid, if none passed in by caller, use MainMenu's activeUser
        String userid = null;
        Parameters params = getParameters();
        if (params != null && !params.getRaw().isEmpty()) {
            userid = params.getRaw().get(0);
        }

        if (userid == null) userid = MainMenu.activeUser;
        if (userid == null) {
            replyText(stage, "You are not logged in.");
            return;
        }

        Account.LoginStatus ret = Account.logout(userid);
        if (ret == Account.LoginStatus.NOT_LOGGED_IN) {
            replyText(stage, "You are not logged in.");
        } else {
            replyText(stage, "You are successfully logged out.");
            if (userid.equals(MainMenu.activeUser)) {
                MainMenu.activeUser = null;
            }
         }
    }

    /**
     * The main method of the application. It calls the launch() method to start the application.
     *
     * @param args Unused.
     */
    public static void main(String[] args) {
        launch();
    }

    private static void replyText(Stage stage, String msg) {
        Text text = new Text();
        text.setText(msg);
        text.setX(70);
        text.setY(70);
        Scene scene = new Scene(new Group(text), 300, 150);
        stage.setTitle("Login status");
        stage.setScene(scene);
        stage.show();
    }
}
