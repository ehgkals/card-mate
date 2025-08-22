package com.cardrecommendation.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL =
        "jdbc:mysql://localhost:3306/cardmate?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true&characterEncoding=UTF-8";
    private static final String USER = "root";      // 본인 계정
    private static final String PASSWORD = "1234";  // 본인 비번

    static {
        try {
            Class.forName(DRIVER); // MySQL 8+
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL Driver load failed", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
