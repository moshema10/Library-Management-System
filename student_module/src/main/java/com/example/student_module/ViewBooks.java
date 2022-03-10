package com.example.student_module;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Sets up the viewing books gui scene by loading the ViewBooks.fxml
 */

public class ViewBooks extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxml = new FXMLLoader(ViewBooks.class.getResource("ViewBooks.fxml"));
        Scene scene = new Scene(fxml.load());
        stage.setScene(scene);
        stage.setTitle("Viewing Books");
        stage.show();
    }
}
