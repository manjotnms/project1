package com.safeinstruments.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import com.safeinstruments.bo.Entry;

public class JdbcGlobal {
	static public String url = "jdbc:mysql://localhost:3306/weighbridgesystem";
	static public String user = "root";
	static public String password = "root";
	static public void insert(Entry e) {
		Connection connection;
		Statement statement;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, password);
			statement = connection.createStatement();
			statement.executeUpdate("insert into entry (epcnum, weight) values ('"
				+ e.getEpcNum() + "', '" + e.getWeight() + "')");

			statement.close();
			connection.close();

		} catch (Exception es) {
			System.out.println(es.getMessage());
		}
	}

	/*public void update(Entry e) {
		Connection connection;
		Statement statement;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, password);
			statement = connection.createStatement();
			statement.executeUpdate();
			statement.close();
			connection.close();
		} catch (Exception es) {
			System.out.println(es.getMessage());
		}
	}*/

	/*public void delete(Entry e) {
		Connection connection;

		Statement statement;
		try {

			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, user, password);
			statement = connection.createStatement();
			statement.executeUpdate();
			statement.close();
			connection.close();

		} catch (Exception es) {
			System.out.println(es.getMessage());
		}
	}
*/
}
