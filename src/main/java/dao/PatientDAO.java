package dao;

import model.Patient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    public void add(Patient patient) {
        String sql = "INSERT INTO patient (nom, prenom, telephone, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = config.DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, patient.getNom());
            pstmt.setString(2, patient.getPrenom());
            pstmt.setString(3, patient.getTelephone());
            pstmt.setString(4, patient.getEmail());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Patient> findAll() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patient";

        try (Connection conn = config.DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                patients.add(new Patient(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("telephone"),
                        rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    public Patient findById(int id) {
        String sql = "SELECT * FROM patient WHERE id = ?";
        try (Connection conn = config.DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Patient(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("telephone"),
                            rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(Patient patient) {
        String sql = "UPDATE patient SET nom = ?, prenom = ?, telephone = ?, email = ? WHERE id = ?";
        try (Connection conn = config.DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, patient.getNom());
            pstmt.setString(2, patient.getPrenom());
            pstmt.setString(3, patient.getTelephone());
            pstmt.setString(4, patient.getEmail());
            pstmt.setInt(5, patient.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM patient WHERE id = ?";
        try (Connection conn = config.DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
