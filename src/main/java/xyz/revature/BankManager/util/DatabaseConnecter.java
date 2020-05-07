package xyz.revature.BankManager.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnecter {
	private static Connection connection;
	
	public static Connection hardcodedConnection() throws SQLException {
		String url = "jdbc:postgresql://bankapp.hash.area.amazonaws.com:5432/postgres";
		String username = "postgres";
		String password = "password";

		if (connection == null || connection.isClosed()) {
			connection = DriverManager.getConnection(url, username, password);
		}

		return connection;
	}
	
}
