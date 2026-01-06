package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import model.Medecin;
import model.Specialite;
import dao.MedecinDAO;

import java.util.Optional;

public class MedecinViewController {
        @FXML
    private TableView<Medecin> medecinTable;
    @FXML
    private TableColumn<Medecin, Integer> colId;
    @FXML
    private TableColumn<Medecin, String> colNom;
    @FXML
    private TableColumn<Medecin, String> colPrenom;
    @FXML
    private TableColumn<Medecin, Specialite> colSpecialite;

    @FXML
    private TextField searchField;

    private MedecinDAO medecinDAO = new MedecinDAO();
    private ObservableList<Medecin> medecinList = FXCollections.observableArrayList();
    
    // Pour la recherche
    private javafx.collections.transformation.FilteredList<Medecin> filteredData;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colSpecialite.setCellValueFactory(new PropertyValueFactory<>("specialite"));

        loadMedecins();
        setupSearch();
    }

    private void loadMedecins() {
        medecinList.clear();
        medecinList.addAll(medecinDAO.findAll());
        medecinTable.setItems(medecinList);
        
        // Wrapper de filtrage
        filteredData = new javafx.collections.transformation.FilteredList<>(medecinList, p -> true);
        medecinTable.setItems(filteredData);
    }
    
    private void setupSearch() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(medecin -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (medecin.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (medecin.getPrenom().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (medecin.getSpecialite().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }

                return false;
            });
        });
    }

    @FXML
    public void handleAjouter() {
        Medecin newMedecin = new Medecin();
        boolean okClicked = showMedecinDialog(newMedecin);
        if (okClicked) {
            medecinDAO.add(newMedecin);
            loadMedecins();
        }
    }

    @FXML
    public void handleModifier() {
        Medecin selected = medecinTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            boolean okClicked = showMedecinDialog(selected);
            if (okClicked) {
                medecinDAO.update(selected);
                loadMedecins();
                medecinTable.refresh();
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un médecin dans la liste.");
        }
    }

    @FXML
    public void handleSupprimer() {
        Medecin selected = medecinTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression");
            alert.setHeaderText("Supprimer le médecin ?");
            alert.setContentText(
                    "Êtes-vous sûr de vouloir supprimer " + selected.getNom() + " " + selected.getPrenom() + " ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                medecinDAO.delete(selected.getId());
                loadMedecins();
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un médecin dans la liste.");
        }
    }

    private boolean showMedecinDialog(Medecin medecin) {
        Dialog<Boolean> dialog = new Dialog<>();
        dialog.setTitle("Détails du Médecin");
        dialog.setHeaderText("Modifier les informations du médecin");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nomField = new TextField();
        nomField.setPromptText("Nom");
        nomField.setText(medecin.getNom());

        TextField prenomField = new TextField();
        prenomField.setPromptText("Prénom");
        prenomField.setText(medecin.getPrenom());

        ComboBox<Specialite> specialiteBox = new ComboBox<>();
        specialiteBox.getItems().setAll(Specialite.values());
        specialiteBox.setValue(medecin.getSpecialite());

        grid.add(new Label("Nom:"), 0, 0);
        grid.add(nomField, 1, 0);
        grid.add(new Label("Prénom:"), 0, 1);
        grid.add(prenomField, 1, 1);
        grid.add(new Label("Spécialité:"), 0, 2);
        grid.add(specialiteBox, 1, 2);

        dialog.getDialogPane().setContent(grid);

        // Enable/Disable login button depending on whether a username was entered.
        javafx.scene.Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Validation listener
        javafx.beans.value.ChangeListener<Object> validationListener = (observable, oldValue, newValue) -> {
            boolean nomEmpty = nomField.getText() == null || nomField.getText().trim().isEmpty();
            boolean prenomEmpty = prenomField.getText() == null || prenomField.getText().trim().isEmpty();
            boolean specialiteEmpty = specialiteBox.getValue() == null;
            loginButton.setDisable(nomEmpty || prenomEmpty || specialiteEmpty);
        };

        nomField.textProperty().addListener(validationListener);
        prenomField.textProperty().addListener(validationListener);
        specialiteBox.valueProperty().addListener(validationListener);

        // Initial check
        validationListener.changed(null, null, null);

        // Result converter
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                // No need to check for nulls here as button is disabled if invalid
                medecin.setNom(nomField.getText());
                medecin.setPrenom(prenomField.getText());
                medecin.setSpecialite(specialiteBox.getValue());
                return true;
            }
            return false;
        });

        Optional<Boolean> result = dialog.showAndWait();
        return result.orElse(false);
    }

    private dao.RendezVousDAO rdvDAO = new dao.RendezVousDAO();

    @FXML
    public void handlePlanning() {
        Medecin selected = medecinTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader();
                loader.setLocation(com.example.medsyncapp.HelloApplication.class.getResource("HistoryView.fxml"));
                javafx.scene.layout.VBox page = (javafx.scene.layout.VBox) loader.load();

                javafx.stage.Stage dialogStage = new javafx.stage.Stage();
                dialogStage.setTitle("Planning Médecin");
                dialogStage.initModality(javafx.stage.Modality.WINDOW_MODAL);
                dialogStage.initOwner(medecinTable.getScene().getWindow());
                javafx.scene.Scene scene = new javafx.scene.Scene(page);
                dialogStage.setScene(scene);

                HistoryController controller = loader.getController();
                controller.setDialogStage(dialogStage);
                controller.setData(rdvDAO.findByMedecinId(selected.getId()), 
                                   "Planning du Dr " + selected.getNom(), 
                                   false);

                dialogStage.showAndWait();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un médecin.");
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
