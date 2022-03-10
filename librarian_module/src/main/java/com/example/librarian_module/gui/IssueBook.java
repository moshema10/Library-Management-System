package com.example.librarian_module.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.io.IOException;

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
 * The class IssueBook is the GUI for issuing a book to a borrower. It displays the Issue Book
 * form and upon submission, check if the user is logged in and has enough privilege to perform
 * the operation before proceeding.
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

public class IssueBook extends Application {
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

        Scene scene = new Scene(grid, 400, 260);
        Text title = new Text("Issue Book");
        title.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        grid.add(title, 1, 0, 2, 1);

        Label bookidText = new Label("Book ID:");
        grid.add(bookidText, 0, 1);

        TextField bookid = new TextField();
        grid.add(bookid, 1, 1);

        Label editionText = new Label("Edition:");
        grid.add(editionText, 0, 2);

        TextField edition = new TextField();
        grid.add(edition, 1, 2);

        Label borroweridText = new Label("Borrower ID:");
        grid.add(borroweridText, 0, 3);

        TextField borrowerid = new TextField();
        grid.add(borrowerid, 1, 3);

        Button submit = new Button("Issue book");
        Button reset = new Button("Clear form");
        HBox hbBtn = new HBox(10);
        hbBtn.getChildren().addAll(submit, reset);
        grid.add(hbBtn, 1, 5);

        final Text replyText = new Text();
        grid.add(replyText, 1, 7);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // replyText.setFill(Color.FIREBRICK);
                String bid = bookid.getText().trim();
                String ed = edition.getText().trim();
                String bwrid = borrowerid.getText().trim();
                if ("".equals(bid) || "".equals(ed) || "".equals(bwrid)) {
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

                    // set last access time to avoid auto-logout
                    Account.setLastAccessed(userid, System.currentTimeMillis());
                    Librarian lbr = Librarian.getActiveLibrarian(userid);
                    if (lbr == null) {
                        replyText.setText("You are not authorized to issue a book.");
                        return;
                    }

                    Book.StatusCode status = lbr.issueBook(bid, ed, bwrid);
                    if (status == Item.StatusCode.SUCCESS) {
                        replyText.setText("Book successfully issued to the borrower.");
                    } else if (status == Item.StatusCode.ITEM_NOT_FOUND) {
                        replyText.setText("Could not find the book in the library.");
                    } else if (status == Item.StatusCode.NO_AVAILABLE_COPY) {
                        replyText.setText("No available copies, all are signed out.");
                    } else if (status == Item.StatusCode.NOT_LOGGED_IN) {
                        replyText.setText("You are not logged in.");
                    } else if (status == Item.StatusCode.NO_PRIVILEGE) {
                        replyText.setText("You are not authorized to issue a book.");
                    } else {
                        replyText.setText("Unknown error.");
                    }
                }
            }
        });

        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                bookid.clear();
                edition.clear();
                borrowerid.clear();
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

