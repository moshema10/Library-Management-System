package com.example.librarian_module.gui;

import com.example.admin_module.AdminModule;
import com.example.student_module.StudentMenuApplication;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.event.ActionEvent;

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
 * The class MainMenu is the main GUI of the library system. It provides a menu for the user to access
 * other GUIs of the system.
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

public class MainMenu extends Application {
    /**
     * The active user currently using the GUI of the library system
     */
    public static String activeUser = null;

    /**
     * The overloaded main entry point of the application.
     *
     * @param stage The default stage of the JavaFX application.
     */
    @Override
    public void start(Stage stage) {
        stage.setTitle("Library Management System");
        MenuBar menuBar = new MenuBar();

        Label label10 = new Label("Account");
        label10.setFont(new Font("Arial", 20));

        Label label11 = new Label("Login");
        label11.setFont(new Font("Arial", 18));
        label11.setStyle("-fx-text-fill: black");

        Label label12 = new Label("Logout");
        label12.setFont(new Font("Arial", 18));
        label12.setStyle("-fx-text-fill: black");

        MenuItem menuItem11 = new MenuItem("", label11);
        menuItem11.addEventHandler(ActionEvent.ACTION, (e) -> {
            try {
                // run another JavaFX app in a new stage, launch() not work
                Application app = Login.class.getDeclaredConstructor().newInstance();
                app.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        MenuItem menuItem12 = new MenuItem("", label12);
        menuItem12.addEventHandler(ActionEvent.ACTION, (e) -> {
            try {
                // run another JavaFX app in a new stage, launch() not work
                Application app = Logout.class.getDeclaredConstructor().newInstance();
                app.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Menu menu1 = new Menu("", label10);
        menu1.getItems().add(menuItem11);
        menu1.getItems().add(menuItem12);
        menuBar.getMenus().add(menu1);

        Label label20 = new Label("Admin");
        label20.setFont(new Font("Arial", 20));
        label20.setOnMouseClicked(mouseEvent->{
            try {
                // run another JavaFX app in a new stage, launch() not work
                Application app = AdminModule.class.getDeclaredConstructor().newInstance();
                app.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        Menu menu2 = new Menu("", label20);
        menuBar.getMenus().add(menu2);

        Label label30 = new Label("Librarian");
        label30.setFont(new Font("Arial", 20));

        Label label31 = new Label("Add Book");
        label31.setFont(new Font("Arial", 18));
        label31.setStyle("-fx-text-fill: black");

        Label label32 = new Label("View Books");
        label32.setFont(new Font("Arial", 18));
        label32.setStyle("-fx-text-fill: black");

        Label label33 = new Label("Issue Book");
        label33.setFont(new Font("Arial", 18));
        label33.setStyle("-fx-text-fill: black");

        Label label34 = new Label("Return Book");
        label34.setFont(new Font("Arial", 18));
        label34.setStyle("-fx-text-fill: black");

        MenuItem menuItem31 = new MenuItem("", label31);
        menuItem31.addEventHandler(ActionEvent.ACTION, (e) -> {
            try {
                Application app = AddBook.class.getDeclaredConstructor().newInstance();
                app.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        MenuItem menuItem32 = new MenuItem("", label32);
        menuItem32.addEventHandler(ActionEvent.ACTION, (e) -> {
            try {
                // run another JavaFX app in a new stage, launch() not work
                Application app = ViewBooks.class.getDeclaredConstructor().newInstance();
                app.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        MenuItem menuItem33 = new MenuItem("", label33);
        menuItem33.addEventHandler(ActionEvent.ACTION, (e) -> {
            try {
                Application app = IssueBook.class.getDeclaredConstructor().newInstance();
                app.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        MenuItem menuItem34 = new MenuItem("", label34);
        menuItem34.addEventHandler(ActionEvent.ACTION, (e) -> {
            try {
                Application app = ReturnBook.class.getDeclaredConstructor().newInstance();
                app.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Menu menu3 = new Menu("", label30);
        menu3.getItems().add(menuItem31);
        menu3.getItems().add(menuItem32);
        menu3.getItems().add(menuItem33);
        menu3.getItems().add(menuItem34);
        menuBar.getMenus().add(menu3);

        Label label40 = new Label("Student");
        label40.setFont(new Font("Arial", 20));
        label40.setOnMouseClicked(mouseEvent->{
            try {
                // run another JavaFX app in a new stage, launch() not work
                Application app = StudentMenuApplication.class.getDeclaredConstructor().newInstance();
                app.start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        Menu menu4 = new Menu("", label40);
        menuBar.getMenus().add(menu4);

        VBox vBox = new VBox(menuBar);
        Scene scene = new Scene(vBox, 500, 300);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method of the application. It calls the launch() method to start the application.
     *
     * @param args Unused.
     */
    public static void main(String[] args) {
        launch(args);
    }
}