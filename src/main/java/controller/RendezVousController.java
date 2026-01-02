package controller;

import model.RendezVous;

import java.util.ArrayList;
import java.util.List;

public class RendezVousController {
    private dao.RendezVousDAO rendezVousDAO = new dao.RendezVousDAO();

    // CREATE
    public void ajouterRdv(RendezVous rdv) {
        rendezVousDAO.add(rdv);
    }

    // READ
    public List<RendezVous> getTousLesRdvs() {
        return rendezVousDAO.findAll();
    }

    public void afficherRdvs() {
        List<RendezVous> rdvs = getTousLesRdvs();
        for (RendezVous rdv : rdvs) {
             System.out.println(rdv.getId() + " - " + rdv.getDate() 
                     + " (Patient: " + (rdv.getPatient() != null ? rdv.getPatient().getNom() : "Inconnu") + ")");
        }
    }

    // UPDATE
    public void modifierRdv(RendezVous rdvModifie) {
        rendezVousDAO.update(rdvModifie);
    }

    // DELETE
    public void supprimerRdv(int id) {
        rendezVousDAO.delete(id);
    }
}
