package controller;

import model.Patient;
import java.util.ArrayList;
import java.util.List;

public class PatientController {
    private List<Patient> patients = new ArrayList<>();

    /* ===================== CREATE ===================== */
    public void ajouterPatient(Patient patient) {
        patients.add(patient);
    }

    /* ===================== READ ===================== */

    // Retourner tous les patients
    public List<Patient> getAllPatients() {
        return patients;
    }

    // Chercher un patient par ID
    public Patient getPatientById(int id) {
        for (Patient p : patients) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null; // non trouvé
    }

    /* ===================== UPDATE ===================== */
    public boolean modifierPatient(int id, String nom, String prenom, String telephone, String email) {
        Patient patient = getPatientById(id);
        if (patient != null) {
            patient.setNom(nom);
            patient.setPrenom(prenom);
            patient.setTelephone(telephone);
            patient.setEmail(email);
            return true;
        }
        return false;
    }

    /* ===================== DELETE ===================== */
    public boolean supprimerPatient(int id) {
        Patient patient = getPatientById(id);
        if (patient != null) {
            patients.remove(patient);
            return true;
        }
        return false;
    }
}
