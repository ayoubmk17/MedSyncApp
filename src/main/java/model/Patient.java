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

    // Getters
    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public List<RendezVous> getHistoriqueRdv() {
        return historiqueRdv;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHistoriqueRdv(List<RendezVous> historiqueRdv) {
        this.historiqueRdv = historiqueRdv;
    }

}
