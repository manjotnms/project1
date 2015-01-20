package com.mtss.connection;

import java.util.*;
import java.sql.*;
import java.io.*;

public class ConnectionProvider {
	static String configFile = "/src/resources/connectionConfig.properties";
	static Connection conn=null;
	static{
		try{
			/*InputStream  isr = new ConnectionProdiver().readFile(configFile);
			Properties p = new Properties();
			
			if(isr!=null){
				p.load(isr);
				Class.forName(p.getProperty("JDBC_DRIVER"));
				conn = DriverManager.getConnection(p.getProperty("DB_URL"),p.getProperty("USER"),p.getProperty("PASSWORD"));
			}
			else{
				System.out.println("Prperty File Not Found.........");
			}*/
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/mtss","root","yash");
		}
		catch(Exception e){
			System.out.println("Exception In ConnectionProvider Class: "+e);
		}
	}
	
	private InputStream readFile(String configFile){
		return  getClass().getClassLoader().getResourceAsStream(configFile);
	}
	public static Connection getConnection(){
		return conn;
	}
}
