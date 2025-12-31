package controller;

import model.RendezVous;

import java.util.ArrayList;
import java.util.List;

public class RendezVousController {
    private List<RendezVous> rendezVousList = new ArrayList<>();

    // CREATE
    public void ajouterRdv(RendezVous rdv) {
        rendezVousList.add(rdv);
    }

    // READ
    public List<RendezVous> getTousLesRdvs() {
        return rendezVousList;
    }

    public void afficherRdvs() {
        for (RendezVous rdv : rendezVousList) {
            System.out.println(rdv.getId() + " - " + rdv.getDate());
        }
    }

    // UPDATE
    public void modifierRdv(RendezVous rdv) {
        for (int i = 0; i < rendezVousList.size(); i++) {
            if (rendezVousList.get(i).getId() == rdv.getId()) {
                rendezVousList.set(i, rdv);
                break;
            }
        }
    }

    // DELETE
    public void supprimerRdv(int id) {
        rendezVousList.removeIf(r -> r.getId() == id);
    }
}
