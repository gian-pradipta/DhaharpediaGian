module com.example.dhaharpedia {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.dhaharpedia to javafx.fxml;
    exports com.example.dhaharpedia;
}