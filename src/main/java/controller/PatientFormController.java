package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Patient;

public class PatientFormController {

    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField telephoneField;
    @FXML
    private TextField emailField;

    private Stage dialogStage;
    private Patient patient;
    private boolean okClicked = false;

    @FXML
    public void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
        if (patient != null) {
            nomField.setText(patient.getNom());
            prenomField.setText(patient.getPrenom());
            telephoneField.setText(patient.getTelephone());
            emailField.setText(patient.getEmail());
        }
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleValider() {
        if (isInputValid()) {
            patient.setNom(nomField.getText());
            patient.setPrenom(prenomField.getText());
            patient.setTelephone(telephoneField.getText());
            patient.setEmail(emailField.getText());

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleAnnuler() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (nomField.getText() == null || nomField.getText().length() == 0) {
            errorMessage += "Nom invalide!\n";
        }
        if (prenomField.getText() == null || prenomField.getText().length() == 0) {
            errorMessage += "Prénom invalide!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Afficher l'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Champs Invalides");
            alert.setHeaderText("Veuillez corriger les champs invalides");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }
}
