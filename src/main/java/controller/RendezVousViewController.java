package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import model.RendezVous;
import dao.RendezVousDAO;

import java.time.format.DateTimeFormatter;

public class RendezVousViewController {

    @FXML
    private TableView<RendezVous> rdvTable;
    @FXML
    private TableColumn<RendezVous, Integer> colId;
    @FXML
    private TableColumn<RendezVous, String> colPatient;
    @FXML
    private TableColumn<RendezVous, String> colMedecin;
    @FXML
    private TableColumn<RendezVous, String> colDate;
    @FXML
    private TableColumn<RendezVous, String> colHeure;
    @FXML
    private TableColumn<RendezVous, String> colStatut;

    private RendezVousDAO rdvDAO = new RendezVousDAO();
    private ObservableList<RendezVous> rdvList = FXCollections.observableArrayList();
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        // Afficher proprement les objets liés (Nom du patient au lieu de l'objet Patient)
        colPatient.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getPatient().getNom() + " " + cellData.getValue().getPatient().getPrenom()));
            
        colMedecin.setCellValueFactory(cellData -> 
            new SimpleStringProperty("Dr " + cellData.getValue().getMedecin().getNom()));
            
        colDate.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getDate().format(dateFormatter)));
            
        colHeure.setCellValueFactory(cellData -> 
            new SimpleStringProperty(cellData.getValue().getDate().format(timeFormatter)));
            
        colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));

        loadRdvs();
    }

    private void loadRdvs() {
        rdvList.clear();
        rdvList.addAll(rdvDAO.findAll());
        rdvTable.setItems(rdvList);
    }

    @FXML
    public void handleAjouter() {
        RendezVous tempRdv = new RendezVous();
        boolean okClicked = showRendezVousDialog(tempRdv);
        if (okClicked) {
            rdvDAO.add(tempRdv);
            loadRdvs();
        }
    }

    @FXML
    public void handleModifier() {
        RendezVous selectedRdv = rdvTable.getSelectionModel().getSelectedItem();
        if (selectedRdv != null) {
            boolean okClicked = showRendezVousDialog(selectedRdv);
            if (okClicked) {
                rdvDAO.update(selectedRdv); // Assurez-vous que RendezVousDAO a une méthode update
                loadRdvs();
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un rendez-vous à modifier.");
        }
    }

    @FXML
    public void handleSupprimer() {
        RendezVous selectedRdv = rdvTable.getSelectionModel().getSelectedItem();
        if (selectedRdv != null) {
             Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
             alert.setTitle("Confirmation");
             alert.setHeaderText("Annuler le rendez-vous ?");
             alert.setContentText("Voulez-vous vraiment annuler ce RDV ?");
             
             if (alert.showAndWait().get() == javafx.scene.control.ButtonType.OK) {
                 rdvDAO.delete(selectedRdv.getId());
                 loadRdvs();
             }
        } else {
             showAlert("Aucune sélection", "Veuillez sélectionner un rendez-vous à annuler.");
        }
    }

    public boolean showRendezVousDialog(RendezVous rdv) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
            loader.setLocation(com.example.medsyncapp.HelloApplication.class.getResource("RendezVousForm.fxml"));
            javafx.scene.layout.VBox page = (javafx.scene.layout.VBox) loader.load();

            javafx.stage.Stage dialogStage = new javafx.stage.Stage();
            dialogStage.setTitle("Édition Rendez-Vous");
            dialogStage.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialogStage.initOwner(rdvTable.getScene().getWindow());
            javafx.scene.Scene scene = new javafx.scene.Scene(page);
            dialogStage.setScene(scene);

            RendezVousFormController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setRendezVous(rdv);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return false;
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
