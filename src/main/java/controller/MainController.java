package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import com.example.medsyncapp.HelloApplication;
import java.io.IOException;

public class MainController {

    @FXML
    private BorderPane mainLayout;

    @FXML
    public void showPatients() {
        loadView("PatientView.fxml");
    }

    @FXML
    public void showMedecins() {
        loadView("MedecinView.fxml"); // Sera créé par votre binôme plus tard
    }

    @FXML
    public void showRendezVous() {
        loadView("RendezVousView.fxml"); // Sera créé par votre binôme plus tard
    }

    private void loadView(String fxmlFile) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile));
            Parent view = fxmlLoader.load();
            mainLayout.setCenter(view);
        } catch (IOException e) {
            // Si la vue n'existe pas encore (cas normal au début)
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger la vue : " + fxmlFile);
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
