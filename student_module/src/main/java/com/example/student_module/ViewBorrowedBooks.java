package com.example.student_module;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ViewBorrowedBooks extends Application {
    /**
     * The overloaded main entry point of the application.
     *
     * @param ps The default stage of the JavaFX application.
     */
    @Override
    public void start(Stage ps) throws IOException {
        GridPane grid = new GridPane();
         grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 600, 400);
        Text title = new Text("View Borrowed Book");
        title.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        grid.add(title, 0, 0, 2, 1);

        Label studentIDText = new Label("Student ID:");
        grid.add(studentIDText, 0, 2);

        TextField studentID = new TextField();
        grid.add(studentID, 1, 2);

        Button submit = new Button("View Borrowed book");
        grid.add(submit, 1, 4);

        final Text replyText = new Text();
        replyText.setFont(Font.font("Consolas", FontWeight.THIN, 13));
        grid.add(replyText, 0, 6, 3,1);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String sid = studentID.getText().trim();
                if ("".equals(sid)) {
                    replyText.setText("You must enter a student ID.");
                } else if (!Student.validateID(sid)) {
                    replyText.setText("Student with ID \"" + sid + "\" does not exist.");
                } else {
                    ArrayList<Books> books = GetDB.getBorrowedBooks(sid);
                    if (books.size() == 0) {
                        replyText.setText("Student with ID \"" + sid + "\" has no borrowed books.");
                    } else {
                        String str = String.format("%-12s %-30s %-20s %-15s%n", "Book ID",
                                "Title", "Author", "Genre");
                        for (int i = 0; i < books.size(); i++) {
                            str += String.format("%-12s %-30s %-20s %-15s%n",
                                    books.get(i).getBookID(), books.get(i).getTitle(),
                                    books.get(i).getAuthor(), books.get(i).getGenre());
                        }
                        replyText.setText(str);
                    }
                }
            }
        });

        ps.setScene(scene);
        ps.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
