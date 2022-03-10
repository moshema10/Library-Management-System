package com.example.librarian_module.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
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
import com.example.librarian_module.sys.Account.LoginStatus;

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
 * The class Login is the GUI for logging in a user before he is allowed to perform any
 * operations.
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

public class Login extends Application {
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
        Text title = new Text("Login");
        title.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        grid.add(title, 1, 0, 2, 1);

        Label useridText = new Label("Userid:");
        grid.add(useridText, 0, 1);

        TextField userid = new TextField();
        grid.add(userid, 1, 1);

        Label passwordText = new Label("Password:");
        grid.add(passwordText, 0, 2);

        PasswordField password = new PasswordField();
        grid.add(password, 1, 2);

        Button submit = new Button("Sign in");
        Button reset = new Button("Clear form");
        HBox hbBtn = new HBox(10);
        // hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(submit, reset);
        grid.add(hbBtn, 1, 4);

        final Text replyText = new Text();
        grid.add(replyText, 1, 6);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                // replyText.setFill(Color.FIREBRICK);
                String uid = userid.getText().trim();
                String pwd = password.getText().trim();
                if ("".equals(uid) || "".equals(pwd)) {
                    replyText.setText("You must enter a value for all the fields.");
                } else {
                    LoginStatus ret = Account.login(uid, pwd);
                    if (ret == LoginStatus.SUCCESS) {
                        MainMenu.activeUser = uid;
                        replyText.setText("You are successfully logged in.");
                    } else if (ret == LoginStatus.NO_ACCOUNT) {
                        replyText.setText("Account with userid \"" + uid + "\" does not exist.");
                    } else if (ret == LoginStatus.BAD_PASSWORD) {
                        replyText.setText("Wrong password.");
                    }
                }
            }
        });

        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                userid.clear();
                password.clear();
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
