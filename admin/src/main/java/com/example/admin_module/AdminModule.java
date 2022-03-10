package com.example.admin_module;
import javafx.event.ActionEvent;

import java.io.*;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class AdminModule extends Application {
    //all layout components:
    Stage page;
    Button adminButton;
    Scene start, openPage, adminMain;
    StackPane layout;
    HBox pageButton;
    StackPane adminPage;
    GridPane adminLogin, registerLibrarian;
    Button login, addLibrarian, removeLibrarian, register, report;
    TextField password, username, librarianID, librarianPwd, firstName, lastName;
    VBox adminVerticalBox;
    TableView<Librarians> librarianTable;
    int pageWidth = 600;
    int pageHeight = 600;
    ObservableList<Librarians> allLibrarians = FXCollections.observableArrayList();

    /**
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {

        try (BufferedReader br = new BufferedReader(new FileReader("librarianDB.txt"))) {
            String record;
            while ((record = br.readLine()) != null) {
                Scanner in = new Scanner(record).useDelimiter("\\|");
                allLibrarians.add(new Librarians(in.next(), in.next(), in.next(), in.next()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        page = primaryStage;
        page.setTitle("Library Management System");

        adminMainComponents();
        adminComponents();
        librarianComponents();
        mainPage();
        adminPage();
        page();
        page.setScene(start);
        page.show();
        adminButton.setOnAction(new EventHandler<ActionEvent>() {

            /**
             * @param arg0
             *
             *
             */
            @Override
            public void handle(ActionEvent arg0) {
                System.out.println("Admin Login");
                page.setScene(openPage);
            }
        });

        login.setOnAction(e -> {
            if (username.getText().equals("rupin") && password.getText().equals("admin")) {
                page.setScene(adminMain);
            }
        });

        addLibrarian.setOnAction(e -> {
            if (!librarianID.getText().isEmpty() && !librarianPwd.getText().isEmpty()
                    && !firstName.getText().isEmpty() && !lastName.getText().isEmpty()) {
                allLibrarians.add(new Librarians(librarianID.getText(), librarianPwd.getText(),
                        lastName.getText(), firstName.getText()));
                librarianID.clear();
                librarianPwd.clear();
                firstName.clear();
                lastName.clear();
            }
        });

        removeLibrarian.setOnAction(e -> {
            ObservableList<Librarians> selected;
            selected = librarianTable.getSelectionModel().getSelectedItems();
            selected.forEach(allLibrarians::remove);
        });

        register.setOnAction(e -> {
            try {
                File object = new File("librarianReport.txt");
                if (object.createNewFile()) {
                    System.out.println("The file was created: " + object.getName());
                } else {
                    System.out.println("The file already exists.");
                }

                FileWriter register = new FileWriter("librarianDB.txt");
                for (int i = 0; i < allLibrarians.size(); ++i) {
                    register.write(allLibrarians.get(i).getId() + "|"
                            + allLibrarians.get(i).getPassword() + "|"
                            + allLibrarians.get(i).getLastName() + "|"
                            + allLibrarians.get(i).getFirstName() + "\n");
                }
                register.close();
                System.out.println("Librarians database file is updated.");

            } catch (IOException ex) {
                System.out.println("An error has occurred!");
                ex.printStackTrace();
            }
        });

        report.setOnAction(e -> {
            try {
                File object = new File("librarianReport.txt");
                if (object.createNewFile()) {
                    System.out.println("The file was created: " + object.getName());
                } else {
                    System.out.println("The file already exists.");
                }

                FileWriter register = new FileWriter("librarianReport.txt");
                String str = String.format("%-10s %-10s %-20s %-20s%n", "Username", "Password",
                        "Last Name", "First Name");
                register.write(str);
                for (int i = 0; i < allLibrarians.size(); ++i) {
                    str = String.format("%-10s %-10s %-20s %-20s%n",
                            allLibrarians.get(i).getId(), allLibrarians.get(i).getPassword(),
                            allLibrarians.get(i).getLastName(), allLibrarians.get(i).getFirstName());
                    register.write(str);
                }
                register.close();
                System.out.println("Librarians were successfully printed to the file.");

            } catch (IOException ex) {
                System.out.println("An error has occurred!");
                ex.printStackTrace();
            }
        });

        page.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
    }

    //the table where the names are stored
    private void table() {
        TableColumn<Librarians, String> idColumn = new TableColumn<Librarians, String>("Name");
        idColumn.setMaxWidth(300);
        idColumn.setCellValueFactory(cellData -> cellData.getValue().id);
        librarianTable = new TableView<>();
        librarianTable.setItems(allLibrarians);
        librarianTable.getColumns().addAll(idColumn);
    }

    /*Labels and input text fields in which you can type username, password, first name and last name and also
    register librarian, remove librarian, save data into librarianDB.txt and print the report to librarianReport.txt */
    private void adminComponents() {
        adminLogin = new GridPane();
        adminLogin.setAlignment(Pos.CENTER);
        adminLogin.setPadding(new Insets(7, 7, 7, 7));
        adminLogin.setVgap(8);
        adminLogin.setHgap(10);

        Label labelName = new Label("Username: ");
        GridPane.setConstraints(labelName, 0, 0);

        username = new TextField();
        username.setPromptText("username");
        GridPane.setConstraints(username, 1, 0);

        Label labelPassword = new Label("Password: ");
        GridPane.setConstraints(labelPassword, 0, 1);

        password = new TextField();
        password.setPromptText("password");
        GridPane.setConstraints(password, 1, 1);

        login = new Button("Login");
        GridPane.setConstraints(login, 1, 2);

        adminLogin.getChildren().addAll(labelName, username, labelPassword, password, login);

        registerLibrarian = new GridPane();
        registerLibrarian.setAlignment(Pos.CENTER);
        registerLibrarian.setPadding(new Insets(7, 7, 7, 7));
        registerLibrarian.setVgap(10);
        registerLibrarian.setHgap(10);

        Label libIDLabel = new Label("Add Librarian: Username: ");
        GridPane.setConstraints(libIDLabel, 0, 1);

        librarianID = new TextField();
        librarianID.setPromptText("Type Username Here");
        GridPane.setConstraints(librarianID, 1, 1);

        Label libPwdLabel = new Label("Password: ");
        GridPane.setConstraints(libPwdLabel, 2, 1);

        librarianPwd = new PasswordField();
        librarianPwd.setPromptText("Password Here");
        GridPane.setConstraints(librarianPwd, 3, 1);

        Label firstNameLabel = new Label("First Name: ");
        GridPane.setConstraints(firstNameLabel, 0, 2);

        firstName = new TextField();
        firstName.setPromptText("First Name Here");
        GridPane.setConstraints(firstName, 1, 2);

        Label lastNameLabel = new Label("Last Name: ");
        GridPane.setConstraints(lastNameLabel, 2, 2);

        lastName = new PasswordField();
        lastName.setPromptText("Last Name Here");
        GridPane.setConstraints(lastName, 3, 2);

        addLibrarian = new Button("Register Librarian");
        GridPane.setConstraints(addLibrarian, 0, 4);

        removeLibrarian = new Button("Remove Librarian");
        GridPane.setConstraints(removeLibrarian, 1, 4);

        register = new Button("Commit Changes");
        GridPane.setConstraints(register, 2, 4);

        report = new Button("Print All Librarians");
        GridPane.setConstraints(report, 3, 4);

        registerLibrarian.getChildren().addAll(libIDLabel, librarianID, libPwdLabel, librarianPwd,
                firstNameLabel, firstName, lastNameLabel, lastName,
                addLibrarian, removeLibrarian, register, report);

        adminVerticalBox = new VBox(10);
        adminVerticalBox.getChildren().addAll(registerLibrarian);

    }

    private void page() {
        start = new Scene(layout, pageWidth, pageHeight);
        openPage = new Scene(adminLogin, pageWidth, pageHeight);
        adminMain = new Scene(adminPage, pageWidth, pageHeight);
    }

    private void mainPage() {
        layout = new StackPane();
        layout.getChildren().add(pageButton);

    }

    private void adminPage() {
        table();
        adminVerticalBox.getChildren().add(librarianTable);
        adminPage = new StackPane();
        adminPage.getChildren().add(adminVerticalBox);
        adminPage.setAlignment(Pos.CENTER);

    }

    private void librarianComponents() {
        Label labelUsername = new Label("Username: ");
        GridPane.setConstraints(labelUsername, 0, 0);
        Label labelPassword = new Label("User ID: ");
        GridPane.setConstraints(labelPassword, 0, 1);
    }

    //main page where you click the button "Admin Login" and it takes you to the login page
    private void adminMainComponents() {
        adminButton = new Button();
        adminButton.setText("Admin Login");
        pageButton = new HBox(20);
        pageButton.getChildren().addAll(adminButton);
        pageButton.setAlignment(Pos.CENTER);
    }

    //command prompt to close the program
    private void closeProgram() {
        Boolean select = Exit.display("Exit", "Are you sure you want to exit?");
        if (select) {
            page.close();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
