package com.ncu.Healthcare.dao;

import com.ncu.Common.CheckItem;
import com.ncu.Common.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CheckItemDAO {
    public List<CheckItem> getAll() {
        List<CheckItem> checkItems = new ArrayList<>();
        String sql = "SELECT * FROM check_items";

        try (Connection conn = DbUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                CheckItem item = new CheckItem();
                item.setId(rs.getLong("id"));
                item.setName(rs.getString("name"));
                item.setCode(rs.getString("code"));
                item.setDescription(rs.getString("description"));
                item.setNormalRange(rs.getString("normal_range"));
                item.setPrice(rs.getDouble("price"));
                item.setCreatedAt(rs.getObject("created_at", java.time.LocalDateTime.class));

                checkItems.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return checkItems;
    }

    public CheckItem getById(Long id) {
        String sql = "SELECT * FROM check_items WHERE id = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    CheckItem item = new CheckItem();
                    item.setId(rs.getLong("id"));
                    item.setName(rs.getString("name"));
                    item.setCode(rs.getString("code"));
                    item.setDescription(rs.getString("description"));
                    item.setNormalRange(rs.getString("normal_range"));
                    item.setPrice(rs.getDouble("price"));
                    item.setCreatedAt(rs.getObject("created_at", java.time.LocalDateTime.class));

                    return item;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean add(CheckItem item) {
        String sql = "INSERT INTO check_items (name, code, description, normal_range, price, created_at) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, item.getName());
            pstmt.setString(2, item.getCode());
            pstmt.setString(3, item.getDescription());
            pstmt.setString(4, item.getNormalRange());
            pstmt.setDouble(5, item.getPrice());
            pstmt.setObject(6, item.getCreatedAt());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(CheckItem item) {
        String sql = "UPDATE check_items SET name = ?, code = ?, description = ?, normal_range = ?, price = ? WHERE id = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, item.getName());
            pstmt.setString(2, item.getCode());
            pstmt.setString(3, item.getDescription());
            pstmt.setString(4, item.getNormalRange());
            pstmt.setDouble(5, item.getPrice());
            pstmt.setLong(6, item.getId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(Long id) {
        String sql = "DELETE FROM check_items WHERE id = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}


