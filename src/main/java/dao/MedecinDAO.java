package dao;

import model.Medecin;
import model.Specialite;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedecinDAO {

    public void add(Medecin medecin) {
        String sql = "INSERT INTO medecin (nom, prenom, specialite) VALUES (?, ?, ?)";
        try (Connection conn = config.DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, medecin.getNom());
            pstmt.setString(2, medecin.getPrenom());
            pstmt.setString(3, medecin.getSpecialite().name());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Medecin> findAll() {
        List<Medecin> medecins = new ArrayList<>();
        String sql = "SELECT * FROM medecin";
        
        try (Connection conn = config.DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Medecin m = new Medecin();
                m.setId(rs.getInt("id"));
                m.setNom(rs.getString("nom"));
                m.setPrenom(rs.getString("prenom"));
                m.setSpecialite(Specialite.valueOf(rs.getString("specialite")));
                medecins.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medecins;
    }

    public Medecin findById(int id) {
        String sql = "SELECT * FROM medecin WHERE id = ?";
        try (Connection conn = config.DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Medecin m = new Medecin();
                    m.setId(rs.getInt("id"));
                    m.setNom(rs.getString("nom"));
                    m.setPrenom(rs.getString("prenom"));
                    m.setSpecialite(Specialite.valueOf(rs.getString("specialite")));
                    return m;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(Medecin medecin) {
        String sql = "UPDATE medecin SET nom = ?, prenom = ?, specialite = ? WHERE id = ?";
        try (Connection conn = config.DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, medecin.getNom());
            pstmt.setString(2, medecin.getPrenom());
            pstmt.setString(3, medecin.getSpecialite().name());
            pstmt.setInt(4, medecin.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM medecin WHERE id = ?";
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
