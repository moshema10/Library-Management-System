package com.example.librarian_module.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.*;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.io.IOException;

import static com.example.librarian_module.sys.BookGenre.genreTypes;

import com.example.librarian_module.sys.Account;
import com.example.librarian_module.sys.Book;
import com.example.librarian_module.sys.Item;
import com.example.librarian_module.sys.Librarian;

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
 * The class AddBook is the GUI for adding a book to the library system. It displays the
 * Add Book form and upon submission, check if the user is logged in and has enough privilege
 * to perform the operation before proceeding.
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

public class AddBook extends Application {
    /**
     * The overloaded main entry point of the application.
     *
     * @param ps The default stage of the JavaFX application.
     */
    @Override
    public void start(Stage ps) throws IOException {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 500, 500);
        Text formTitle = new Text("Add Book");
        formTitle.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        grid.add(formTitle, 1, 0, 2, 1);

        Label bookIDText = new Label("Book ID:");
        grid.add(bookIDText, 0, 1);

        TextField bookID = new TextField();
        grid.add(bookID, 1, 1);

        Label titleText = new Label("Title:");
        grid.add(titleText, 0, 2);

        TextField title = new TextField();
        grid.add(title, 1, 2);

        Label authorText = new Label("Author:");
        grid.add(authorText, 0, 3);

        TextField firstName = new TextField();
        firstName.setPromptText("First name");
        grid.add(firstName, 1, 3);

        TextField lastName = new TextField();
        lastName.setPromptText("Last name");
        grid.add(lastName, 2, 3);

        Label genreText = new Label("Category:");
        grid.add(genreText, 0, 4);

        final ComboBox genre = new ComboBox();
        for (var e : genreTypes) {
            genre.getItems().add(e);
        }
        grid.add(genre, 1, 4);

        Label editionText = new Label("Edition:");
        grid.add(editionText, 0, 5);

        TextField edition = new TextField();
        grid.add(edition, 1, 5);

        Label isbnText = new Label("ISBN:");
        grid.add(isbnText, 0, 6);

        TextField isbn = new TextField();
        grid.add(isbn, 1, 6);

        Label yearText = new Label("Published year:");
        grid.add(yearText, 0, 7);

        TextField year = new TextField();
        grid.add(year, 1, 7);

        Label pagesText = new Label("No. of pages:");
        grid.add(pagesText, 0, 8);

        TextField pages = new TextField();
        grid.add(pages, 1, 8);

        Label separator = new Label("-----------------------------------------------------");
        grid.add(separator, 0, 9, 3, 1);

        Label copiesText = new Label("No. of copies:");
        grid.add(copiesText, 0, 10);

        TextField copies = new TextField();
        grid.add(copies, 1, 10);

        Button submit = new Button("Add book");
        Button reset = new Button("Clear form");
        HBox hbBtn = new HBox(10);
        // hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(submit, reset);
        grid.add(hbBtn, 1, 12);

        final Text replyText = new Text();
        grid.add(replyText, 1, 14, 2, 1);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // replyText.setFill(Color.FIREBRICK);
                String bid = bookID.getText().trim();
                String tle = title.getText().trim();
                String fname = firstName.getText().trim();
                String lname = lastName.getText().trim();
                String ed = edition.getText().trim();
                String ISBN = isbn.getText().trim();
                String yr = year.getText().trim();
                String pgs = pages.getText().trim();
                String cps = copies.getText().trim();
                if ("".equals(bid) || "".equals(tle) || "".equals(fname) || "".equals(lname)
                        || "".equals(ed) || "".equals(ISBN) || "".equals(yr) || "".equals(pgs)
                        || "".equals(cps) || genre.getValue() == null) {
                    replyText.setText("You must enter a value for all the fields.");
                } else {
                    // get userid, if none passed in by caller, use MainMenu's activeUser
                    String userid = null;
                    Parameters params = getParameters();
                    if (params != null && !params.getRaw().isEmpty()) {
                        userid = params.getRaw().get(0);
                    }

                    if (userid == null) userid = MainMenu.activeUser;
                    if (userid == null) {
                        replyText.setText("You are not logged in.");
                        return;
                    }

                    Librarian lbr = Librarian.getActiveLibrarian(userid);
                    if (lbr == null) {
                        replyText.setText("You are not authorized to add a book.");
                        return;
                    }

                    // set last access time to avoid auto-logout
                    Account.setLastAccessed(userid, System.currentTimeMillis());
                    Book.StatusCode status = lbr.addBook(bid, tle, lname, fname, ed, ISBN, yr,
                            pgs, cps, genre.getValue().toString());
                    if (status == Item.StatusCode.SUCCESS) {
                        replyText.setText("Book successfully added to the library.");
                    } else if (status == Item.StatusCode.DUPLICATE_ITEM_ID) {
                        replyText.setText("Book ID already exists in the library.");
                    } else if (status == Item.StatusCode.NOT_LOGGED_IN) {
                        replyText.setText("You are not logged in.");
                    } else if (status == Item.StatusCode.NO_PRIVILEGE) {
                        replyText.setText("You are not authorized to add a book.");
                    } else {
                        replyText.setText("Unknown error.");
                    }
                }
            }
        });

        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                bookID.clear();
                title.clear();
                firstName.clear();
                lastName.clear();
                edition.clear();
                isbn.clear();
                year.clear();
                pages.clear();
                copies.clear();
                genre.valueProperty().set(null);
                replyText.setText("");
            }
        });

        ps.setScene(scene);
        ps.show();
    }

    /**
     * The main method of the application. It calls the launch() method to start the application.
     *
     * @param args Unused.
     */
    public static void main(String[] args) {
        launch();
    }
}

