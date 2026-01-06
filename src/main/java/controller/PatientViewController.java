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

    @FXML
    public void initialize() {
        // 1. Configurer les colonnes pour qu'elles lisent les attributs de Patient
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colTelephone.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        // 2. Charger les données (À FAIRE : décommentez la ligne suivante quand vous serez prêts)
        // loadPatients();
    }

    private void loadPatients() {
        patientList.clear();
        patientList.addAll(patientDAO.findAll());
        patientTable.setItems(patientList);
    }

    @FXML
    public void handleAjouter() {
        System.out.println("Clic sur Ajouter - À implémenter par vous !");
        // Indice : Ouvrir une nouvelle fenêtre (Dialog)
    }

    @FXML
    public void handleModifier() {
        Patient selected = patientTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            System.out.println("Modification de : " + selected.getNom());
            // Indice : Ouvrir la fenêtre avec les données pré-remplies
        } else {
            System.out.println("Veuillez sélectionner un patient.");
        }
    }

    @FXML
    public void handleSupprimer() {
        Patient selected = patientTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Indice : Appeler patientDAO.delete(selected.getId()) puis recharger la table
            System.out.println("Suppression de : " + selected.getNom());
        }
    }
}
