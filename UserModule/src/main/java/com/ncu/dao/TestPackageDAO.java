package com.ncu.dao;

import com.healthsys.common.model.TestPackage;
import com.healthsys.common.util.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestPackageDAO {
    public List<TestPackage> getAllPackages() {
        List<TestPackage> packages = new ArrayList<>();
        String sql = "SELECT * FROM test_packages ORDER BY name";

        try (Connection conn = DbUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                TestPackage testPackage = new TestPackage(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getTimestamp("created_at")
                );
                packages.add(testPackage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return packages;
    }

    public TestPackage getPackageById(Long id) {
        String sql = "SELECT * FROM test_packages WHERE id = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new TestPackage(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getTimestamp("created_at")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addPackage(TestPackage testPackage) {
        String sql = "INSERT INTO test_packages (name, description, price) VALUES (?, ?, ?)";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, testPackage.getName());
            stmt.setString(2, testPackage.getDescription());
            stmt.setDouble(3, testPackage.getPrice());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePackage(TestPackage testPackage) {
        String sql = "UPDATE test_packages SET name = ?, description = ?, price = ? WHERE id = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, testPackage.getName());
            stmt.setString(2, testPackage.getDescription());
            stmt.setDouble(3, testPackage.getPrice());
            stmt.setLong(4, testPackage.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePackage(Long id) {
        String sql = "DELETE FROM test_packages WHERE id = ?";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addPackageWithTests(TestPackage testPackage, List<Long> testIds) {
        String sql = "INSERT INTO test_packages (name, description, price) VALUES (?, ?, ?)";
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, testPackage.getName());
            stmt.setString(2, testPackage.getDescription());
            stmt.setDouble(3, testPackage.getPrice());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) return false;

            ResultSet rs = stmt.getGeneratedKeys();
            if (!rs.next()) return false;

            Long packageId = rs.getLong(1);

            // 插入套餐与检查项目的关系
            for (Long testId : testIds) {
                if (!new PackageTestDAO().addTestToPackage(packageId, testId)) {
                    return false;
                }
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean createPackage(TestPackage testPackage, List<Long> testIds) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DbUtil.getConnection();

            // 开启事务
            conn.setAutoCommit(false);

            // 插入套餐
            String sql = "INSERT INTO test_packages (name, description, price) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, testPackage.getName());
            stmt.setString(2, testPackage.getDescription());
            stmt.setDouble(3, testPackage.getPrice());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                conn.rollback();
                return false;
            }

            // 获取新插入套餐的ID
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    long packageId = rs.getLong(1);

                    // 插入关联表 package_tests
                    String insertSql = "INSERT INTO package_tests (package_id, test_id) VALUES (?, ?)";
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                        for (Long testId : testIds) {
                            insertStmt.setLong(1, packageId);
                            insertStmt.setLong(2, testId);
                            insertStmt.addBatch();
                        }
                        insertStmt.executeBatch();
                    }

                    // 提交事务
                    conn.commit();
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            }
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
