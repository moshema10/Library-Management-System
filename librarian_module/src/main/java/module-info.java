module com.example.librarian_module {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.junit.jupiter.api;
    requires java.logging;
    requires com.example.student_module;
    requires com.example.admin_module;

    opens com.example.librarian_module.gui to javafx.fxml;
    exports com.example.librarian_module.gui;
    exports com.example.librarian_module.sys;
}