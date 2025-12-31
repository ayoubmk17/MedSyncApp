package model;

import java.time.*;
import java.util.*;

public class Medecin {
    private int id;
    private String nom;
    private String prenom;
    private Specialite specialite;
    private Map<LocalDate, List<LocalTime>> horairesDisponibles;


    public Medecin(int id, String nom, String prenom, Specialite specialite) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.specialite = specialite;
        this.horairesDisponibles = new HashMap<>();
    }

    public Medecin() {
        this.horairesDisponibles = new HashMap<>();
    }


    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public Map<LocalDate, List<LocalTime>> getHorairesDisponibles() {
        return horairesDisponibles;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    public void setHorairesDisponibles(Map<LocalDate, List<LocalTime>> horairesDisponibles) {
        this.horairesDisponibles = horairesDisponibles;
    }



    // Ajouter un créneau horaire
    public void ajouterHoraire(LocalDate date, LocalTime heure) {
        horairesDisponibles
                .computeIfAbsent(date, d -> new ArrayList<>())
                .add(heure);
    }

    // Supprimer un créneau horaire
    public void supprimerHoraire(LocalDate date, LocalTime heure) {
        if (horairesDisponibles.containsKey(date)) {
            horairesDisponibles.get(date).remove(heure);
            if (horairesDisponibles.get(date).isEmpty()) {
                horairesDisponibles.remove(date);
            }
        }
    }


}
