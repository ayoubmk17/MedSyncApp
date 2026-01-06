package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.cell.PropertyValueFactory;
import model.RendezVous;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class HistoryController {

    @FXML
    private Label titleLabel;
    @FXML
    private TableView<RendezVous> rdvTable;
    @FXML
    private TableColumn<RendezVous, String> colDate;
    @FXML
    private TableColumn<RendezVous, String> colHeure;
    @FXML
    private TableColumn<RendezVous, String> colPersonne; // Affichera Patient ou Médecin selon le contexte
    @FXML
    private TableColumn<RendezVous, String> colStatut;

    private Stage dialogStage;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    @FXML
    public void initialize() {
        colDate.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getDate().format(dateFormatter)));
            
        colHeure.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getDate().format(timeFormatter)));
            
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setData(List<RendezVous> rdvs, String title, boolean isPatientView) {
        titleLabel.setText(title);
        rdvTable.setItems(FXCollections.observableArrayList(rdvs));

        // Adapter la colonne "Personne"
        if (isPatientView) {
            // Si on regarde l'historique d'un Patient, on veut voir le Médecin
            colPersonne.setText("Médecin");
            colPersonne.setCellValueFactory(cellData -> 
                new SimpleStringProperty("Dr " + cellData.getValue().getMedecin().getNom()));
        } else {
            // Si on regarde le planning d'un Médecin, on veut voir le Patient
            colPersonne.setText("Patient");
            colPersonne.setCellValueFactory(cellData -> 
                new SimpleStringProperty(cellData.getValue().getPatient().getNom() + " " + cellData.getValue().getPatient().getPrenom()));
        }
    }

    @FXML
    private void handleFermer() {
        dialogStage.close();
    }
}
