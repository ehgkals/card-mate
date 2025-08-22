package dev.syntax.model.dao;

import java.sql.*;

import dev.syntax.util.DBUtil;

public class UserDAO {
    public Long findUserIdByName(String userName) {
        String sql = "SELECT user_id FROM `user` WHERE user_name = ? LIMIT 1";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, userName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getLong(1);
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("findUserIdByName failed", e);
        }
    }
}
