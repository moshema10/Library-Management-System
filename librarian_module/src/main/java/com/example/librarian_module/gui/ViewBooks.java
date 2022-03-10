package com.example.librarian_module.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import javafx.beans.property.SimpleStringProperty;

import java.util.Scanner;

import com.example.librarian_module.sys.Account;
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
 * The class ViewBooks is the GUI for adding a book to the library system. It displays the data
 * of all the books in the library system.
 *
 * @author Jackson Yeung
 * @version 1.0
 * @since 2022-03-04
 */

public class ViewBooks extends Application {
    /**
     * The overloaded main entry point of the application.
     *
     * @param stage The default stage of the JavaFX application.
     */
    @Override
    public void start(Stage stage) {
        // get userid, if none passed in by caller, use MainMenu's activeUser
        String userid = null;
        Parameters params = getParameters();
        if (params != null && !params.getRaw().isEmpty()) {
            userid = params.getRaw().get(0);
        }

        if (userid == null) userid = MainMenu.activeUser;
        if (userid == null) {
            showReply(stage, "You are not logged in.");
            return;
        }

        Librarian lbr = Librarian.getActiveLibrarian(userid);
        if (lbr == null) {
            showReply(stage, "You are not authorized to view book data.");
            return;
        }
        // set last access time to avoid auto-logout
        Account.setLastAccessed(userid, System.currentTimeMillis());

        Scene scene = new Scene(new Group());
        stage.setTitle("All Books");
        stage.setWidth(1000);
        stage.setHeight(500);

        final Label label = new Label("All Books");
        label.setFont(new Font("Arial", 20));

        TableView<BookInfo> table = new TableView<>();
        // table expands to fill stage width
        table.prefWidthProperty().bind(stage.widthProperty());

        TableColumn bookidCol = new TableColumn("Book ID");
        bookidCol.setCellValueFactory(
                new PropertyValueFactory<BookInfo, String>("bookID"));
        // set column width to 6.5% of table width
        bookidCol.prefWidthProperty().bind(table.widthProperty().multiply(0.065));

        TableColumn titleCol = new TableColumn("Title");
        titleCol.setCellValueFactory(
                new PropertyValueFactory<BookInfo, String>("title"));
        titleCol.prefWidthProperty().bind(table.widthProperty().multiply(0.3));

        TableColumn authorCol = new TableColumn("Author");
        authorCol.setCellValueFactory(
                new PropertyValueFactory<BookInfo, String>("author"));
        authorCol.prefWidthProperty().bind(table.widthProperty().multiply(0.15));

        TableColumn genreCol = new TableColumn("Genre");
        genreCol.setCellValueFactory(
                new PropertyValueFactory<BookInfo, String>("genre"));
        genreCol.prefWidthProperty().bind(table.widthProperty().multiply(0.1));

        TableColumn editionCol = new TableColumn("Edition");
        editionCol.setCellValueFactory(
                new PropertyValueFactory<BookInfo, String>("edition"));
        editionCol.prefWidthProperty().bind(table.widthProperty().multiply(0.065));

        TableColumn isbnCol = new TableColumn("ISBN");
        isbnCol.setCellValueFactory(
                new PropertyValueFactory<BookInfo, String>("isbn"));
        isbnCol.prefWidthProperty().bind(table.widthProperty().multiply(0.1));

        TableColumn yearCol = new TableColumn("Year");
        yearCol.setCellValueFactory(
                new PropertyValueFactory<BookInfo, String>("year"));
        yearCol.prefWidthProperty().bind(table.widthProperty().multiply(0.065));

        TableColumn pagesCol = new TableColumn("Pages");
        pagesCol.setCellValueFactory(
                new PropertyValueFactory<BookInfo, String>("pages"));
        pagesCol.prefWidthProperty().bind(table.widthProperty().multiply(0.065));

        TableColumn copiesCol = new TableColumn("Copies");
        copiesCol.setCellValueFactory(
                new PropertyValueFactory<BookInfo, String>("copies"));
        copiesCol.prefWidthProperty().bind(table.widthProperty().multiply(0.065));

        table.getColumns().addAll(bookidCol, titleCol, authorCol, genreCol, editionCol,
                isbnCol, yearCol, pagesCol, copiesCol);

        lbr.initReport();
        String record;
        while ((record = lbr.getNextRec()) != null) {
            Scanner in = new Scanner(record).useDelimiter("\\|");
            BookInfo info = new BookInfo(in.next(), in.next(), in.next(), in.next(),
                    in.next(), in.next(), in.next(), in.next(), in.next());
            table.getItems().add(info);
        }
        lbr.closeReport();

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method of the application. It calls the launch() method to start the application.
     *
     * @param args Unused.
     */
    public static void main(String[] args) {
        launch();
    }

    private static void showReply(Stage stage, String msg) {
        Text text = new Text();
        text.setText(msg);
        text.setX(50);
        text.setY(70);
        Scene scene = new Scene(new Group(text), 320, 150);
        stage.setTitle("ALl Books");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Class BookInfo defines the data model and provides methods and fields to work
     * with the book data table.
     */
    public static class BookInfo {
        private final SimpleStringProperty bookID;
        private final SimpleStringProperty title;
        private final SimpleStringProperty author;
        private final SimpleStringProperty genre;
        private final SimpleStringProperty edition;
        private final SimpleStringProperty isbn;
        private final SimpleStringProperty year;
        private final SimpleStringProperty pages;
        private final SimpleStringProperty copies;

        private BookInfo(String bookID, String title, String author, String genre,
                         String edition, String isbn, String year, String pages,
                         String copies) {
            this.bookID = new SimpleStringProperty(bookID);
            this.title = new SimpleStringProperty(title);
            this.author = new SimpleStringProperty(author);
            this.genre = new SimpleStringProperty(genre);
            this.edition = new SimpleStringProperty(edition);
            this.isbn = new SimpleStringProperty(isbn);
            this.year = new SimpleStringProperty(year);
            this.pages = new SimpleStringProperty(pages);
            this.copies = new SimpleStringProperty(copies);
        }

        public String getBookID() {
            return bookID.get();
        }

        public void setBookID(String bid) {
            bookID.set(bid);
        }

        public String getTitle() {
            return title.get();
        }

        public void setTitle(String tle) {
            title.set(tle);
        }

        public String getAuthor() {
            return author.get();
        }

        public void setAuthor(String name) {
            author.set(name);
        }

        public String getGenre() {
            return genre.get();
        }

        public void setGenre(String cat) {
            genre.set(cat);
        }

        public String getEdition() {
            return edition.get();
        }

        public void setEdition(String ed) {
            edition.set(ed);
        }

        public String getIsbn() {
            return isbn.get();
        }

        public void setIsbn(String isbNum) {
            isbn.set(isbNum);
        }

        public String getYear() {
            return year.get();
        }

        public void setYear(String yr) {
            year.set(yr);
        }

        public String getPages() {
            return pages.get();
        }

        public void setPages(String pgs) {
            pages.set(pgs);
        }

        public String getCopies() {
            return copies.get();
        }

        public void setCopies(String cps) {
            copies.set(cps);
        }
    }
}
