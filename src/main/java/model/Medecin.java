package model;

import java.time.*;
import java.util.*;

public class Medecin {
    private int id;
    private String nom;
    private String prenom;
    private Specialite specialite;
    private Map<LocalDate, List<LocalTime>> horairesDisponibles = new HashMap<>();

    public Medecin(int id, String nom, String prenom, Specialite specialite) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.specialite = specialite;
    }

}
