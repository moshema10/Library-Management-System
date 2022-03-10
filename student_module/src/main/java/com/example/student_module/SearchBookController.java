
package com.example.student_module;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;

/**
 * SearchBookController class controls the Searching Book Gui. It connects the GUI to the Classes for every actions.
 */

public class SearchBookController {


    @FXML
    private Text author;

    @FXML
    private TextField book_id;

    @FXML
    private Text genre;

    @FXML
    private Text isbn;

    @FXML
    private Text name;

    @FXML
    private TextArea message;

    @FXML
    private TextField studentID;

    /**
     * Executes After User clicks the Search Books button.
     */
    @FXML
    void onSearchBook(ActionEvent event) {


        Books abook = new Books();
        String m_id = book_id.getText();


        if(abook.getBookID(m_id))
        {
            name.setText(abook.getTitle());
            System.out.println("Book name: " + name.getText());
            author.setText(abook.getAuthor());
            genre.setText(abook.getGenre());
            isbn.setText(abook.getISBN());
        }
        else
        {
            name.setText("");
            author.setText("No Book Found");
            genre.setText("");
            isbn.setText("");
        }


    }

    /**
     * Clears the BookID input form and Book result section
     */
    @FXML
    void onClear(ActionEvent event) {
        name.setText("");
        author.setText("");
        genre.setText("");
        isbn.setText("");
        book_id.setText("");
    }

    /**
     * Executes after user clicks Issue Book button. It takes all the user inputs and operates the issuing book operation
     */

    @FXML
    void onIssueBook(ActionEvent event) throws FileNotFoundException {
        String s_ID = studentID.getText();
        String b_ID = book_id.getText();
        Student s = new Student();
        if(Student.validateID(s_ID))
        {
            //Search Student from DB()
            System.out.println("Student validated");
            s.setStudentId(s_ID);

            if (GetDB.hasbook(b_ID) == GetDB.BookStatus.NO_BOOK) {
                message.setText("The book does not exist.");
            } else if (GetDB.hasbook(b_ID) == GetDB.BookStatus.AVAILABLE) {
                message.setText("The book is available for loan.");
            } else if (s.checkStatus(b_ID)) {
                message.setText("You already have a waiting ticket for this book.");
            } else {
                s.writeRequestFile(b_ID);
                message.setText("You're issued a waiting ticket for this book.");
            }
        }
        else
        {
            message.setText("Student with ID does not exist.");
        }

    }

}
