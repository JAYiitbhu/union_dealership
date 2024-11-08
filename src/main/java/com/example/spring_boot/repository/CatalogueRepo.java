package com.example.spring_boot.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.spring_boot.config.DatabaseConnector;
import com.example.spring_boot.entity.catalogue;

public class CatalogueRepo {

    // Method to add a new catalogue entry
    // public void addCatalogueEntry(catalogue catalogue) {
    //     String sql = "INSERT INTO catalogue (model_id, mileage, engine_type, price, model_name, manufacture_year, manufacturer_id) "
    //             + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    //     try (Connection conn = DatabaseConnector.getConnection();
    //          PreparedStatement pstmt = conn.prepareStatement(sql)) {

    //         pstmt.setLong(1, catalogue.getModelId());
    //         pstmt.setInt(2, catalogue.getMileage());
    //         pstmt.setString(3, catalogue.getEngineType());
    //         pstmt.setInt(4, catalogue.getPrice());
    //         pstmt.setString(5, catalogue.getModelName());
    //         pstmt.setInt(6, catalogue.getManufactureYear());
    //         pstmt.setLong(7, catalogue.getManufacturerId());

    //         int rowsAffected = pstmt.executeUpdate();
    //         if (rowsAffected > 0) {
    //             System.out.println("Catalogue entry added successfully!");
    //         } else {
    //             System.out.println("Failed to add catalogue entry.");
    //         }

    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //     }
    // }




    public static List<catalogue> getAllCatalogueEntries(int minMileage, int maxMileage, int minPrice, int maxPrice, String sortBy, String engineType) {
        StringBuilder query = new StringBuilder("SELECT * FROM catalogue WHERE mileage BETWEEN ? AND ? AND price BETWEEN ? AND ?");
        
        if (engineType != null && !engineType.isEmpty()) {
            query.append(" AND engine_type = ?");
        }
        
        // Handle sorting by price or mileage
        if (sortBy != null) {
            if (sortBy.equalsIgnoreCase("price_asc")) {
                query.append(" ORDER BY price ASC");
            } else if (sortBy.equalsIgnoreCase("price_desc")) {
                query.append(" ORDER BY price DESC");
            } else if (sortBy.equalsIgnoreCase("mileage_asc")) {
                query.append(" ORDER BY mileage ASC");
            } else if (sortBy.equalsIgnoreCase("mileage_desc")) {
                query.append(" ORDER BY mileage DESC");
            }
        }

        List<catalogue> catalogueList = new ArrayList<>();
        
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            stmt.setInt(1, minMileage);
            stmt.setInt(2, maxMileage);
            stmt.setInt(3, minPrice);
            stmt.setInt(4, maxPrice);

            int paramIndex = 5;
            if (engineType != null && !engineType.isEmpty()) {
                stmt.setString(paramIndex, engineType);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                catalogue catalogue = new catalogue();
                catalogue.setModelId(rs.getLong("model_id"));
                catalogue.setMileage(rs.getInt("mileage"));
                catalogue.setEngineType(rs.getString("engine_type"));
                catalogue.setPrice(rs.getInt("price"));
                catalogue.setModelName(rs.getString("model_name"));
                catalogue.setManufactureYear(rs.getInt("manufacture_year"));
                catalogue.setManufacturerId(rs.getLong("manufacturer_id"));

                catalogueList.add(catalogue);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return catalogueList;
    }

    // Method to retrieve all catalogue entries
    public static List<catalogue> getAllCatalogueEntries() {
        String sql = "SELECT * FROM catalogue";
        List<catalogue> catalogueList = new ArrayList<>();

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                catalogue catalogue = new catalogue();
                catalogue.setModelId(rs.getLong("model_id"));
                catalogue.setMileage(rs.getInt("mileage"));
                catalogue.setEngineType(rs.getString("engine_type"));
                catalogue.setPrice(rs.getInt("price"));
                catalogue.setModelName(rs.getString("model_name"));
                catalogue.setManufactureYear(rs.getInt("manufacture_year"));
                catalogue.setManufacturerId(rs.getLong("manufacturer_id"));

                catalogueList.add(catalogue);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return catalogueList;
    }

    // Method to retrieve a catalogue entry by model_id
    public static catalogue getCatalogueById(Long modelId) {
        String sql = "SELECT * FROM catalogue WHERE model_id = ?";
        catalogue catalogue = null;
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, modelId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                catalogue = new catalogue();
                catalogue.setModelId(rs.getLong("model_id"));
                catalogue.setMileage(rs.getInt("mileage"));
                catalogue.setEngineType(rs.getString("engine_type"));
                catalogue.setPrice(rs.getInt("price"));
                catalogue.setModelName(rs.getString("model_name"));
                catalogue.setManufactureYear(rs.getInt("manufacture_year"));
                catalogue.setManufacturerId(rs.getLong("manufacturer_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return catalogue;
    }

    // Method to delete a catalogue entry by model_id
    public void deleteCatalogueById(int modelId) {
        String sql = "DELETE FROM catalogue WHERE model_id = ?";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, modelId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Catalogue entry deleted successfully!");
            } else {
                System.out.println("No entry found for the given model_id.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

