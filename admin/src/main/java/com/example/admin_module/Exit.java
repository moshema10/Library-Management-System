package com.example.admin_module;
import javafx.scene.*;
import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.*;

public class Exit {

    static boolean select;

    /**
     * @param title
     * @param message
     * @return
     *
     * This will show a page when you close the program which has the buttons yes and no, if you click yes the program closes and if you click no the prompt closes
     * and you can see the library management system again
     */
    public static boolean display(String title, String message) {
        Stage page = new Stage();
        page.initModality(Modality.APPLICATION_MODAL);
        page.setTitle(title);
        page.setMinWidth(250);

        Label label = new Label();
        label.setText(message);

        Button clickYes = new Button("Yes");
        Button clickNo = new Button("No");

        clickYes.setOnAction(e -> {
            select = true;
            page.close();
        });

        clickNo.setOnAction(e -> {
            select = false;
            page.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, clickYes, clickNo);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        page.setScene(scene);
        page.showAndWait();

        return select;

    }
}
