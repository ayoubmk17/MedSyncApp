package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Medecin;
import model.Patient;
import model.RendezVous;
import model.StatutRdv;
import dao.MedecinDAO;
import dao.PatientDAO;

import java.time.LocalTime;
import java.util.List;

public class RendezVousFormController {

    @FXML
    private ComboBox<Patient> patientComboBox;
    @FXML
    private ComboBox<Medecin> medecinComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<LocalTime> heureComboBox;

    private Stage dialogStage;
    private RendezVous rendezVous;
    private boolean okClicked = false;
    
    // DAOs pour charger les listes
    private PatientDAO patientDAO = new PatientDAO();
    private MedecinDAO medecinDAO = new MedecinDAO();

    @FXML
    public void initialize() {
        // 1. Remplir les ComboBox
        remplirListes();
        
        // 2. Configurer l'affichage (StringConverter)
        configurerAffichageComboBox();
    }

    private void remplirListes() {
        // Patients
        List<Patient> patients = patientDAO.findAll();
        patientComboBox.setItems(FXCollections.observableArrayList(patients));
        
        // Médecins
        List<Medecin> medecins = medecinDAO.findAll();
        medecinComboBox.setItems(FXCollections.observableArrayList(medecins));
        
        // Heures (de 08:00 à 18:00 par pas de 30 min)
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(18, 0);
        while(start.isBefore(end.plusSeconds(1))) { // Inclure 18:00
            heureComboBox.getItems().add(start);
            start = start.plusMinutes(30);
        }
    }

    private void configurerAffichageComboBox() {
        // Afficher "Nom Prénom" pour Patient
        patientComboBox.setConverter(new StringConverter<Patient>() {
            @Override
            public String toString(Patient p) {
                return (p == null) ? "" : p.getNom() + " " + p.getPrenom();
            }

            @Override
            public Patient fromString(String string) {
                return null; // Pas besoin pour une sélection
            }
        });

        // Afficher "Dr Nom (Specialité)" pour Médecin
        medecinComboBox.setConverter(new StringConverter<Medecin>() {
            @Override
            public String toString(Medecin m) {
                return (m == null) ? "" : "Dr " + m.getNom() + " (" + m.getSpecialite() + ")";
            }

            @Override
            public Medecin fromString(String string) {
                return null;
            }
        });
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setRendezVous(RendezVous rdv) {
        this.rendezVous = rdv;
        if (rdv != null) {
            // Pré-sélectionner les valeurs existantes (si modification)
            if (rdv.getDate() != null) {
                datePicker.setValue(rdv.getDate().toLocalDate());
                heureComboBox.setValue(rdv.getDate().toLocalTime());
            }
            
            // Sélectionner les objets correspondants dans les listes
            // (Nécessite que equals() soit bien implémenté dans Patient/Medecin, sinon il faut chercher par ID)
            if (rdv.getPatient() != null) {
                // Recherche par ID dans la liste de la ComboBox pour être sûr de sélectionner le bon objet
                for(Patient p : patientComboBox.getItems()) {
                    if(p.getId() == rdv.getPatient().getId()) {
                        patientComboBox.setValue(p);
                        break;
                    }
                }
            }
             if (rdv.getMedecin() != null) {
                for(Medecin m : medecinComboBox.getItems()) {
                    if(m.getId() == rdv.getMedecin().getId()) {
                        medecinComboBox.setValue(m);
                        break;
                    }
                }
            }
        }
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleValider() {
        if (isInputValid()) {
            rendezVous.setPatient(patientComboBox.getValue());
            rendezVous.setMedecin(medecinComboBox.getValue());
            rendezVous.setDate(datePicker.getValue().atTime(heureComboBox.getValue()));
            if(rendezVous.getStatut() == null) {
                rendezVous.setStatut(StatutRdv.PLANIFIE); // Par défaut
            }

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

        if (patientComboBox.getValue() == null) {
            errorMessage += "Veuillez sélectionner un patient.\n";
        }
        if (medecinComboBox.getValue() == null) {
            errorMessage += "Veuillez sélectionner un médecin.\n";
        }
        if (datePicker.getValue() == null) {
            errorMessage += "Veuillez choisir une date.\n";
        }
        if (heureComboBox.getValue() == null) {
            errorMessage += "Veuillez choisir une heure.\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
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
