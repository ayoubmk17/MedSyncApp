package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Patient;
import dao.PatientDAO;
import javafx.scene.control.Alert;

public class PatientViewController {

    @FXML
    private TableView<Patient> patientTable;
    @FXML
    private TableColumn<Patient, Integer> colId;
    @FXML
    private TableColumn<Patient, String> colNom;
    @FXML
    private TableColumn<Patient, String> colPrenom;
    @FXML
    private TableColumn<Patient, String> colTelephone;
    @FXML
    private TableColumn<Patient, String> colEmail;

    @FXML
    private TextField searchField;

    private PatientDAO patientDAO = new PatientDAO();
    private ObservableList<Patient> patientList = FXCollections.observableArrayList();

    private dao.RendezVousDAO rdvDAO = new dao.RendezVousDAO();
    
    // Pour la recherche
    private javafx.collections.transformation.FilteredList<Patient> filteredData;

    @FXML
    public void initialize() {
        // 1. Configurer les colonnes
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        // 2. Charger les données initiales
        loadPatients();
        
        // 3. Configurer la recherche
        setupSearch();
    }

    private void loadPatients() {
        patientList.clear();
        patientList.addAll(patientDAO.findAll());
        patientTable.setItems(patientList);
        
        // Réinitialiser le wrapper de filtrage après rechargement
        filteredData = new javafx.collections.transformation.FilteredList<>(patientList, p -> true);
        patientTable.setItems(filteredData);
    }
    
    private void setupSearch() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(patient -> {
                // Si le champ est vide, on affiche tout
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                // On compare avec Nom, Prénom, Tél, Email
                if (patient.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (patient.getPrenom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (patient.getTelephone().contains(lowerCaseFilter)) {
                    return true;
                } else if (patient.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false; // Pas de correspondance
            });
        });
    }

    @FXML
    public void handleAjouter() {
        Patient tempPatient = new Patient(0, "", "", "", "");
        boolean okClicked = showPatientEditDialog(tempPatient);
        if (okClicked) {
            patientDAO.add(tempPatient);
            loadPatients();
        }
    }

    @FXML
    public void handleModifier() {
        Patient selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            boolean okClicked = showPatientEditDialog(selectedPatient);
            if (okClicked) {
                patientDAO.update(selectedPatient);
                patientTable.refresh(); // Rafraîchir l'affichage
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un patient dans la liste.");
        }
    }

    @FXML
    public void handleSupprimer() {
        Patient selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
             Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
             alert.setTitle("Confirmation");
             alert.setHeaderText("Supprimer le patient ?");
             alert.setContentText("Voulez-vous vraiment supprimer " + selectedPatient.getNom() + " ?");
             
             if (alert.showAndWait().get() == javafx.scene.control.ButtonType.OK) {
                 patientDAO.delete(selectedPatient.getId());
                 loadPatients();
             }
        } else {
             showAlert("Aucune sélection", "Veuillez sélectionner un patient à supprimer.");
        }
    }

    public boolean showPatientEditDialog(Patient patient) {
        try {
            // Charge le fichier FXML
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
            loader.setLocation(com.example.medsyncapp.HelloApplication.class.getResource("PatientForm.fxml"));
            javafx.scene.layout.VBox page = (javafx.scene.layout.VBox) loader.load();

            // Crée le Stage (fenêtre)
            javafx.stage.Stage dialogStage = new javafx.stage.Stage();
            dialogStage.setTitle("Édition Patient");
            dialogStage.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialogStage.initOwner(patientTable.getScene().getWindow());
            javafx.scene.Scene scene = new javafx.scene.Scene(page);
            dialogStage.setScene(scene);

            // Configure le contrôleur
            PatientFormController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPatient(patient);

            // Affiche la fenêtre et attend
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    


    @FXML
    public void handleHistorique() {
        Patient selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            try {
                javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
                loader.setLocation(com.example.medsyncapp.HelloApplication.class.getResource("HistoryView.fxml"));
                javafx.scene.layout.VBox page = (javafx.scene.layout.VBox) loader.load();

                javafx.stage.Stage dialogStage = new javafx.stage.Stage();
                dialogStage.setTitle("Historique Patient");
                dialogStage.initModality(javafx.stage.Modality.WINDOW_MODAL);
                dialogStage.initOwner(patientTable.getScene().getWindow());
                javafx.scene.Scene scene = new javafx.scene.Scene(page);
                dialogStage.setScene(scene);

                HistoryController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.setData(rdvDAO.findByPatientId(selectedPatient.getId()), 
                                   "Historique de " + selectedPatient.getNom(), 
                                   true);

                dialogStage.showAndWait();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un patient.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
