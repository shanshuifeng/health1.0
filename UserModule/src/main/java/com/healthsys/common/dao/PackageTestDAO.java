package com.healthsys.common.dao;

import com.healthsys.common.model.MedicalTest;
import com.healthsys.common.model.PackageTest;
import com.healthsys.common.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PackageTestDAO {

    // 添加检查项目到套餐
    public boolean addTestToPackage(Long packageId, Long testId) {
        String sql = "INSERT INTO package_tests (package_id, test_id) VALUES (?, ?)";
        try (Connection conn = DbUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, packageId);
            stmt.setLong(2, testId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 删除套餐中的某个检查项目
    public boolean removeTestFromPackage(Long packageId, Long testId) {
        String sql = "DELETE FROM package_tests WHERE package_id = ? AND test_id = ?";
        try (Connection conn = DbUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, packageId);
            stmt.setLong(2, testId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 查询套餐包含的所有检查项目
    public List<PackageTest> getTestsByPackage(Long packageId) {
        List<PackageTest> list = new ArrayList<>();
        String sql = "SELECT * FROM package_tests WHERE package_id = ?";
        try (Connection conn = DbUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, packageId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(new PackageTest(
                        rs.getLong("package_id"),
                        rs.getLong("test_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 获取套餐未包含的检查项目列表（用于添加时筛选）
    public List<MedicalTest> getAvailableTestsNotInPackage(Long packageId) {
        List<MedicalTest> tests = new ArrayList<>();
        String sql = "SELECT * FROM medical_tests WHERE id NOT IN (SELECT test_id FROM package_tests WHERE package_id = ?)";
        try (Connection conn = DbUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, packageId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tests.add(new MedicalTest(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("code"),
                        rs.getString("description"),
                        rs.getString("normal_range"),
                        rs.getDouble("price"),
                        rs.getTimestamp("created_at")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tests;
    }

    // 获取套餐包含的检查项目列表
    public List<MedicalTest> getMedicalTestsByPackage(Long packageId) {
        List<MedicalTest> tests = new ArrayList<>();
        String sql = "SELECT mt.* FROM medical_tests mt " +
                "JOIN package_tests pt ON mt.id = pt.test_id " +
                "WHERE pt.package_id = ?";
        try (Connection conn = DbUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, packageId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tests.add(new MedicalTest(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("code"),
                        rs.getString("description"),
                        rs.getString("normal_range"),
                        rs.getDouble("price"),
                        rs.getTimestamp("created_at")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tests;
    }
}