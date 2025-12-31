package controller;

import model.RendezVous;

import java.util.ArrayList;
import java.util.List;

public class RendezVousController {
    private List<RendezVous> rendezVousList = new ArrayList<>();
    private int nextId = 1;

    // CREATE
    public void ajouterRdv(RendezVous rdv) {
        rdv.setId(nextId++);
        rendezVousList.add(rdv);

        // RELATIONSHIP CONSISTENCY: Mettre à jour l'historique du patient
        if (rdv.getPatient() != null) {
            rdv.getPatient().getHistoriqueRdv().add(rdv);
        }
    }

    // READ
    public List<RendezVous> getTousLesRdvs() {
        return rendezVousList;
    }

    public void afficherRdvs() {
        rendezVousList.forEach(rdv ->
            System.out.println(rdv.getId() + " - " + rdv.getDate())
        );
    }

    // UPDATE
    public void modifierRdv(RendezVous rdvModifie) {
        rendezVousList.stream()
            .filter(r -> r.getId() == rdvModifie.getId())
            .findFirst()
            .ifPresent(r -> {
                r.setDate(rdvModifie.getDate());
                r.setMedecin(rdvModifie.getMedecin());
                r.setPatient(rdvModifie.getPatient());
                r.setStatut(rdvModifie.getStatut());
            });
    }

    // DELETE
    public void supprimerRdv(int id) {
        rendezVousList.removeIf(r -> r.getId() == id);
    }
}
