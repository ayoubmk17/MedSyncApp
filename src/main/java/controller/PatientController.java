package controller;

import model.Patient;
import java.util.ArrayList;
import java.util.List;

public class PatientController {
    private dao.PatientDAO patientDAO = new dao.PatientDAO();

    /* ===================== CREATE ===================== */
    public void ajouterPatient(Patient patient) {
        patientDAO.add(patient);
    }

    /* ===================== READ ===================== */

    // Retourner tous les patients
    public List<Patient> getAllPatients() {
        return patientDAO.findAll();
    }

    // Chercher un patient par ID
    public Patient getPatientById(int id) {
        return patientDAO.findById(id);
    }

    /* ===================== UPDATE ===================== */
    public boolean modifierPatient(int id, String nom, String prenom, String telephone, String email) {
        Patient patient = new Patient(id, nom, prenom, telephone, email);
        return patientDAO.update(patient);
    }

    /* ===================== DELETE ===================== */
    public boolean supprimerPatient(int id) {
        return patientDAO.delete(id);
    }
}
