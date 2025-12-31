package controller;

import model.Patient;
import java.util.ArrayList;
import java.util.List;

public class PatientController {
    private List<Patient> patients = new ArrayList<>();
    private int nextId = 1;

    /* ===================== CREATE ===================== */
    public void ajouterPatient(Patient patient) {
        patient.setId(nextId++);
        patients.add(patient);
    }

    /* ===================== READ ===================== */

    // Retourner tous les patients
    public List<Patient> getAllPatients() {
        return patients;
    }

    // Chercher un patient par ID
    public Patient getPatientById(int id) {
        return patients.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
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
        return patients.removeIf(p -> p.getId() == id);
    }
}
