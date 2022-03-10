package com.example.student_module;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Sets up the Student Menu winoow by loading the StudentMenu.fxml file
 */
public class StudentMenuApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StudentMenuApplication.class.getResource("StudentMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Student");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
