module com.example.medsyncapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.medsyncapp to javafx.fxml;
    exports com.example.medsyncapp;
    
    exports controller;
    opens controller to javafx.fxml;
    
    exports model;
    opens model to javafx.base;
}