package com.mtss.connection;

import java.sql.Connection;

public class ConnectionTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection conn = ConnectionProvider.getConnection();
		System.out.println("Connection Created............"+conn);
	}

}
