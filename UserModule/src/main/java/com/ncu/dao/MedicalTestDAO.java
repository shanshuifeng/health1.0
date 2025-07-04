package com.ncu.dao;

import com.healthsys.common.model.MedicalTest;
import com.healthsys.common.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicalTestDAO {
    public List<MedicalTest> getAllTests() {
        List<MedicalTest> tests = new ArrayList<>();
        String sql = "SELECT * FROM medical_tests ORDER BY name";

        try (Connection conn = DbUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                MedicalTest test = new MedicalTest(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("code"),
                        rs.getString("description"),
                        rs.getString("normal_range"),
                        rs.getDouble("price"),
                        rs.getTimestamp("created_at")
                );
                tests.add(test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tests;
    }

    public MedicalTest getTestById(Long id) {
        String sql = "SELECT * FROM medical_tests WHERE id = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new MedicalTest(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("code"),
                        rs.getString("description"),
                        rs.getString("normal_range"),
                        rs.getDouble("price"),
                        rs.getTimestamp("created_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addTest(MedicalTest test) {
        String sql = "INSERT INTO medical_tests (name, code, description, normal_range, price) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, test.getName());
            stmt.setString(2, test.getCode());
            stmt.setString(3, test.getDescription());
            stmt.setString(4, test.getNormalRange());
            stmt.setDouble(5, test.getPrice());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateTest(MedicalTest test) {
        String sql = "UPDATE medical_tests SET name = ?, code = ?, description = ?, normal_range = ?, price = ? WHERE id = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, test.getName());
            stmt.setString(2, test.getCode());
            stmt.setString(3, test.getDescription());
            stmt.setString(4, test.getNormalRange());
            stmt.setDouble(5, test.getPrice());
            stmt.setLong(6, test.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTest(Long id) {
        String sql = "DELETE FROM medical_tests WHERE id = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
