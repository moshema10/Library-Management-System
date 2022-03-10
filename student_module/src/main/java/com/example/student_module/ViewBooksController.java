package com.example.student_module;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


/**
 * Creates a table which shows all the Books and every Books information in rows and columns
 */
public class ViewBooksController implements Initializable {
    @FXML
    private TableView<Books> table;
    @FXML
    private TableColumn<Books, String> BookID;

    @FXML
    private TableColumn<Books, String> Title;

    @FXML
    private TableColumn<Books, String> Author;

    @FXML
    private TableColumn<Books, String> Genre;
    @FXML
    private TableColumn<Books, String> Edition;
    @FXML
    private TableColumn<Books, String> ISBN;

    @FXML
    private TableColumn<Books, String> Year;


    @FXML
    private TableColumn<Books, String> Pages;

    @FXML
    private TableColumn<Books, String> Copies;

    private Books book = new Books();


    ObservableList<Books> books_list = FXCollections.observableArrayList(
          book.list()
    );


    /**
     * Initialize the table in the GUI with every elements of books_list.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        BookID.setCellValueFactory(new PropertyValueFactory<Books, String>("BookID"));
        Title.setCellValueFactory(new PropertyValueFactory<Books, String>("Title"));
        Author.setCellValueFactory(new PropertyValueFactory<Books, String>("Author"));
        Genre.setCellValueFactory(new PropertyValueFactory<Books, String>("Genre"));
        Edition.setCellValueFactory(new PropertyValueFactory<Books, String>("Edition"));
        ISBN.setCellValueFactory(new PropertyValueFactory<Books, String>("ISBN"));
        Year.setCellValueFactory(new PropertyValueFactory<Books, String>("Year"));
        Pages.setCellValueFactory(new PropertyValueFactory<Books, String>("Pages"));
        Copies.setCellValueFactory(new PropertyValueFactory<Books, String>("Copies"));

        table.setItems(books_list);

    }
}

