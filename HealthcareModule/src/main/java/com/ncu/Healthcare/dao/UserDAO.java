package com.ncu.Healthcare.dao;

import com.ncu.Common.Users;
import com.ncu.Common.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public List<Users> getAll() {
        List<Users> usersList = new ArrayList<>();
        String sql = "SELECT * FROM users";

        try (Connection conn = DbUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Users user = new Users();
                user.setId(rs.getLong("id"));
                user.setPhone(rs.getString("phone"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setBirthDate(rs.getObject("birth_date", java.time.LocalDate.class));
                user.setGender(rs.getString("gender"));
                user.setRole(rs.getString("role"));
                user.setIdNumber(rs.getString("id_number"));
                user.setCreatedAt(rs.getObject("created_at", java.time.LocalDateTime.class));
                user.setUpdatedAt(rs.getObject("updated_at", java.time.LocalDateTime.class));

                usersList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usersList;
    }

    public Users getById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Users user = new Users();
                    user.setId(rs.getLong("id"));
                    user.setPhone(rs.getString("phone"));
                    user.setPassword(rs.getString("password"));
                    user.setName(rs.getString("name"));
                    user.setBirthDate(rs.getObject("birth_date", java.time.LocalDate.class));
                    user.setGender(rs.getString("gender"));
                    user.setRole(rs.getString("role"));
                    user.setIdNumber(rs.getString("id_number"));
                    user.setCreatedAt(rs.getObject("created_at", java.time.LocalDateTime.class));
                    user.setUpdatedAt(rs.getObject("updated_at", java.time.LocalDateTime.class));

                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    public boolean delete(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";

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

