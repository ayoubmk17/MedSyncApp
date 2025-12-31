package model;

import java.time.LocalDateTime;

public class RendezVous {
    private int id;
    private Patient patient;
    private Medecin medecin;
    private LocalDateTime date;
    private StatutRdv statut;

    public RendezVous(int id, Patient patient, Medecin medecin, LocalDateTime date) {
        this.id = id;
        this.patient = patient;
        this.medecin = medecin;
        this.date = date;
        this.statut = StatutRdv.PLANIFIE;
    }
    // GETTERS & SETTERS
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Medecin getMedecin() { return medecin; }
    public void setMedecin(Medecin medecin) { this.medecin = medecin; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public StatutRdv getStatut() { return statut; }
    public void setStatut(StatutRdv statut) { this.statut = statut; }
}
