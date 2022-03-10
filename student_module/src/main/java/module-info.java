module com.example.student_module {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.junit.jupiter.api;

    opens com.example.student_module to javafx.fxml;
    exports com.example.student_module;
}