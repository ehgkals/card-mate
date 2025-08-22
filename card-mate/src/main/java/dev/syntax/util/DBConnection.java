package dev.syntax.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static Connection initDatabase() throws SQLException, ClassNotFoundException {
		final String USER_NAME = "root";
		final String PASSWORD = "1234";
		final String DB_URL = "jdbc:mysql://localhost:3306/";
		final String DATABASE_SCHEMA = "cardmate";
		

		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = DriverManager.getConnection(DB_URL + DATABASE_SCHEMA, USER_NAME, PASSWORD);
		
		return connection;
	}
}
