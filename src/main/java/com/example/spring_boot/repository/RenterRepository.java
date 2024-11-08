package com.example.spring_boot.repository;

import com.example.spring_boot.config.DatabaseConnector;
import com.example.spring_boot.entity.Renter;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RenterRepository {

    // Method to create a new Renter record
    public static int save(Renter renter) {
        String sql = "INSERT INTO renter (renter_id, driver_history, license_proof, form_path, " +
                     "last_rental_date, signup_date, referral_source, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setLong(1, renter.getRenter_id());
            pstmt.setString(2, renter.getDriver_history());
            pstmt.setBytes(3, renter.getLicense_proof());
            pstmt.setString(4, renter.getForm_path());
            pstmt.setDate(5, (java.sql.Date)renter.getLast_rental_date());
            pstmt.setDate(6, (java.sql.Date)renter.getSignup_date());
            pstmt.setString(7, renter.getReferral_source());
            pstmt.setString(8, renter.getStatus());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Method to find a Renter by ID
    public static Renter findById(Long renterId) {
        String sql = "SELECT * FROM renter WHERE renter_id = ?";
        Renter renter = null;

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, renterId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                renter = mapRow(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return renter;
    }

    // Method to update an existing Renter record
    public int update(Renter renter) {
        String sql = "UPDATE renter SET driver_history = ?, license_proof = ?, form_path = ?, " +
                     "last_rental_date = ?, signup_date = ?, referral_source = ?, status = ? " +
                     "WHERE renter_id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, renter.getDriver_history());
            pstmt.setBytes(2, renter.getLicense_proof());
            pstmt.setString(3, renter.getForm_path());
            pstmt.setDate(4, (java.sql.Date)renter.getLast_rental_date());
            pstmt.setDate(5, (java.sql.Date)renter.getSignup_date());
            pstmt.setString(6, renter.getReferral_source());
            pstmt.setString(7, renter.getStatus());
            pstmt.setLong(8, renter.getRenter_id());

            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Method to delete a Renter by ID
    public int deleteById(Long renterId) {
        String sql = "DELETE FROM renter WHERE renter_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, renterId);
            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Method to list all Renters
    public List<Renter> findAll() {
        String sql = "SELECT * FROM renter";
        List<Renter> renterList = new ArrayList<>();

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                renterList.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return renterList;
    }

    // Method to update a specific field of a Renter
    public int updateField(Long renterId, String fieldName, Object value) {
        String sql = "UPDATE renter SET " + fieldName + " = ? WHERE renter_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, value);
            pstmt.setLong(2, renterId);
            return pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // Helper method to map a ResultSet row to a Renter object
    private static Renter mapRow(ResultSet rs) throws SQLException {
        Renter renter = new Renter();
        renter.setRenter_id(rs.getLong("renter_id"));
        renter.setDriver_history(rs.getString("driver_history"));
        renter.setLicense_proof(rs.getBytes("license_proof"));
        renter.setForm_path(rs.getString("form_path"));
        renter.setLast_rental_date(rs.getDate("last_rental_date"));
        renter.setSignup_date(rs.getDate("signup_date"));
        renter.setReferral_source(rs.getString("referral_source"));
        renter.setStatus(rs.getString("status"));
        return renter;
    }
    
    public static boolean isPresentById(Long id){
        boolean ret = (findById(id)!=null);
        return ret;
    }
}
