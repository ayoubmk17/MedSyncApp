package model;

import java.util.*;

public class Patient {
    private int id;
    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private List<RendezVous> historiqueRdv = new ArrayList<>();

    public Patient(int id, String nom, String prenom, String telephone, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;
    }
}
