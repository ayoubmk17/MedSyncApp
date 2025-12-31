package controller;

import model.Medecin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedecinController {
    private List<Medecin> medecins = new ArrayList<>();
    private int nextId = 1;

    // CREATE

    public void ajouterMedecin(Medecin medecin) {
        medecin.setId(nextId++);
        medecins.add(medecin);
    }


    // Récupérer tous les médecins
    public List<Medecin> getTousLesMedecins() {
        return medecins;
    }

    // Rechercher un médecin par ID
    public Medecin getMedecinParId(int id) {
        Optional<Medecin> medecin = medecins.stream()
                .filter(m -> m.getId() == id)
                .findFirst();

        return medecin.orElse(null);
    }

    // UPDATE

    public boolean modifierMedecin(int id, Medecin nouveauMedecin) {
        Medecin medecinExistant = getMedecinParId(id);

        if (medecinExistant != null) {
            medecinExistant.setNom(nouveauMedecin.getNom());
            medecinExistant.setPrenom(nouveauMedecin.getPrenom());
            medecinExistant.setSpecialite(nouveauMedecin.getSpecialite());
            medecinExistant.setHorairesDisponibles(
                    nouveauMedecin.getHorairesDisponibles()
            );
            return true;
        }
        return false;
    }

    // DELETE

    public boolean supprimerMedecin(int id) {
        return medecins.removeIf(m -> m.getId() == id);
    }
}
