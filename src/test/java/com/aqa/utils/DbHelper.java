package com.aqa.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static com.aqa.utils.AppConfig.CONFIG;

public class DbHelper {

    public static boolean isUserExistsInDb(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(CONFIG.dbUrl(), CONFIG.dbUser(), CONFIG.dbPassword());
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при запросе к БД", e);
        }
        return false;
    }

    public static boolean isArticleExistsInDb(String title) {
        String sql = "SELECT COUNT(*) FROM articles WHERE title = ?";
        try (Connection conn = DriverManager.getConnection(CONFIG.dbUrl(), CONFIG.dbUser(), CONFIG.dbPassword());
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при запросе к БД", e);
        }
        return false;
    }
}
