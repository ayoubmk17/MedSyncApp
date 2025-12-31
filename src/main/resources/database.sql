-- Création de la table Medecin
CREATE TABLE IF NOT EXISTS medecin (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    specialite VARCHAR(50) NOT NULL
);

-- Création de la table Patient
CREATE TABLE IF NOT EXISTS patient (
    id SERIAL PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    telephone VARCHAR(20),
    email VARCHAR(100)
);

-- Création de la table RendezVous
CREATE TABLE IF NOT EXISTS rendez_vous (
    id SERIAL PRIMARY KEY,
    date_heure TIMESTAMP NOT NULL,
    medecin_id INT NOT NULL,
    patient_id INT NOT NULL,
    statut VARCHAR(20) DEFAULT 'PLANIFIE',
    CONSTRAINT fk_medecin FOREIGN KEY (medecin_id) REFERENCES medecin(id) ON DELETE CASCADE,
    CONSTRAINT fk_patient FOREIGN KEY (patient_id) REFERENCES patient(id) ON DELETE CASCADE
);
