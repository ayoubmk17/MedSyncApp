package test;

import controller.MedecinController;
import controller.PatientController;
import controller.RendezVousController;
import model.Medecin;
import model.Patient;
import model.RendezVous;
import model.Specialite;

import java.time.LocalDateTime;

public class ConsoleTest {
    public static void main(String[] args) {
        System.out.println("=== DÉBUT DU TEST ===");

        // 1. Instanciation des Contrôleurs
        MedecinController medecinController = new MedecinController();
        PatientController patientController = new PatientController();
        RendezVousController rdvController = new RendezVousController();

        // 2. Création d'un Médecin
        System.out.println("\n--- 1. Création Médecin ---");
        Medecin medecin = new Medecin();
        medecin.setNom("House");
        medecin.setPrenom("Gregory");
        medecin.setSpecialite(Specialite.GENERALISTE);
        
        medecinController.ajouterMedecin(medecin);
        System.out.println("Médecin ajouté : " + medecin.getNom());

        // 3. Création d'un Patient
        System.out.println("\n--- 2. Création Patient ---");
        Patient patient = new Patient(45, "Dooooe", "John", "0102030405", "john@test.com");
        patientController.ajouterPatient(patient);
        System.out.println("Patient ajouté : " + patient.getNom());

        // 4. Création d'un Rendez-Vous
        System.out.println("\n--- 3. Création RDV ---");
        // On récupère les objets avec leurs IDs générés
        Medecin m = medecinController.getTousLesMedecins().get(0);
        Patient p = patientController.getAllPatients().get(0);
        
        RendezVous rdv = new RendezVous(0, p, m, LocalDateTime.of(2025, 1, 15, 14, 30));
        rdvController.ajouterRdv(rdv);
        System.out.println("RDV créé pour le " + rdv.getDate());

        // 5. Affichage
        System.out.println("\n--- 4. Lecture des données ---");
        System.out.println("Liste des Médecins :");
        for (Medecin doc : medecinController.getTousLesMedecins()) {
            System.out.println("- " + doc.getId() + ": Dr " + doc.getNom() + " (" + doc.getSpecialite() + ")");
        }
        
        System.out.println("Liste des RDVs :");
        rdvController.afficherRdvs();

        // 6. Modification
        System.out.println("\n--- 5. Modification Médecin ---");
        Medecin modif = new Medecin();
        modif.setNom("Cuddy");
        modif.setPrenom("Lisa");
        modif.setSpecialite(Specialite.CARDIOLOGUE);
        medecinController.modifierMedecin(m.getId(), modif);
        
        Medecin updated = medecinController.getMedecinParId(m.getId());
        System.out.println("Médecin modifié : " + updated.getNom() + " (" + updated.getSpecialite() + ")");

        // 7. Suppression
        System.out.println("\n--- 6. Suppression RDV ---");
        int rdvId = rdvController.getTousLesRdvs().get(0).getId();
        rdvController.supprimerRdv(rdvId);
        System.out.println("RDV supprimé. Nombre de RDVs restants : " + rdvController.getTousLesRdvs().size());

        System.out.println("\n=== FIN DU TEST ===");
    }
}
