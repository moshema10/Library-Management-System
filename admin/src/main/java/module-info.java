module com.example.admin_module {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.junit.jupiter.api;

    opens com.example.admin_module to javafx.fxml;
    exports com.example.admin_module;
}