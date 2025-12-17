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
}
