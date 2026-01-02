package controller;

import model.Medecin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedecinController {
    private dao.MedecinDAO medecinDAO = new dao.MedecinDAO();

    // CREATE
    public void ajouterMedecin(Medecin medecin) {
        medecinDAO.add(medecin);
    }

    // Récupérer tous les médecins
    public List<Medecin> getTousLesMedecins() {
        return medecinDAO.findAll();
    }

    // Rechercher un médecin par ID
    public Medecin getMedecinParId(int id) {
        return medecinDAO.findById(id);
    }

    // UPDATE
    public boolean modifierMedecin(int id, Medecin nouveauMedecin) {
        nouveauMedecin.setId(id);
        return medecinDAO.update(nouveauMedecin);
    }

    // DELETE
    public boolean supprimerMedecin(int id) {
        return medecinDAO.delete(id);
    }
}
