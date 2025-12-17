module com.example.medsyncapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.medsyncapp to javafx.fxml;
    exports com.example.medsyncapp;
}