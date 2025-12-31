package dao;

import model.RendezVous;
import model.StatutRdv;
import model.Medecin;
import model.Patient;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RendezVousDAO {

    private MedecinDAO medecinDAO = new MedecinDAO();
    private PatientDAO patientDAO = new PatientDAO();

    public void add(RendezVous rdv) {
        String sql = "INSERT INTO rendez_vous (date_heure, medecin_id, patient_id, statut) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = config.DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setTimestamp(1, java.sql.Timestamp.valueOf(rdv.getDate()));
            pstmt.setInt(2, rdv.getMedecin().getId());
            pstmt.setInt(3, rdv.getPatient().getId());
            pstmt.setString(4, rdv.getStatut().name());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<RendezVous> findAll() {
        List<RendezVous> rdvs = new ArrayList<>();
        String sql = "SELECT * FROM rendez_vous";
        
        try (Connection conn = config.DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int id = rs.getInt("id");
                LocalDateTime date = rs.getTimestamp("date_heure").toLocalDateTime();
                int medecinId = rs.getInt("medecin_id");
                int patientId = rs.getInt("patient_id");
                String statutStr = rs.getString("statut");

                Medecin medecin = medecinDAO.findById(medecinId);
                Patient patient = patientDAO.findById(patientId);

                RendezVous rdv = new RendezVous(id, patient, medecin, date);
                if (statutStr != null) {
                    rdv.setStatut(StatutRdv.valueOf(statutStr));
                }
                rdvs.add(rdv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rdvs;
    }

    public void update(RendezVous rdv) {
        String sql = "UPDATE rendez_vous SET date_heure = ?, medecin_id = ?, patient_id = ?, statut = ? WHERE id = ?";
        try (Connection conn = config.DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setTimestamp(1, java.sql.Timestamp.valueOf(rdv.getDate()));
            pstmt.setInt(2, rdv.getMedecin().getId());
            pstmt.setInt(3, rdv.getPatient().getId());
            pstmt.setString(4, rdv.getStatut().name());
            pstmt.setInt(5, rdv.getId());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM rendez_vous WHERE id = ?";
        try (Connection conn = config.DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
