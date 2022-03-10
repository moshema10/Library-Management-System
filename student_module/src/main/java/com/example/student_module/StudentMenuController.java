package com.example.student_module;

import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Controls the GUI Students Menu Button and connects the button to their particular window
 */
public class StudentMenuController {
    @FXML
    public void onViewBooks() throws Exception {
        Stage stage = new Stage();
        ViewBooks viewBooks = new ViewBooks();
        viewBooks.start(stage);
    }
    public void onViewBorrowedBooks() throws Exception {
        Stage stage = new Stage();
        ViewBorrowedBooks viewBorrowedBooks = new ViewBorrowedBooks();
        viewBorrowedBooks.start(stage);
    }
    public void onSearch() throws IOException {
        Stage stage = new Stage();
        SearchBookApplication IS = new SearchBookApplication();
        IS.start(stage);
    }

    public void onIssueBooks()
    {
        System.out.println("Issue books is clicked");
    }
}
