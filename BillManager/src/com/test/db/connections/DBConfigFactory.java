package com.test.db.connections;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBConfigFactory {
	private static Connection db2conn = null;

	public static Connection getConnection() {
		if (db2conn != null) {
			return db2conn;
		}
		else {
			String url = "jdbc:db2://localhost:50000/TRUNKLOA";
			String userName = "db2admin";
			String password = "Db2adm1n";
			//Connection db2conn = null;
			try {
				Class.forName("com.ibm.db2.jcc.DB2Driver");
				db2conn = DriverManager.getConnection(url, userName, password);
			}
			catch (ClassNotFoundException ex) {
				System.out.println("Could not find the class-DB2Driver");
				ex.printStackTrace();
			}
			catch (SQLException ex) {
				System.out.println("DB2 Connection initialization failed");
				ex.printStackTrace();
			}
			finally {
				return db2conn;
			}
		}
	}

	public static void destroyConnection() {
		if (db2conn != null) {
			try {
				db2conn.close();
			}
			catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static PreparedStatement getPreparedStatement(String queryString){
		PreparedStatement ps = null;
		try{
			ps = db2conn.prepareStatement(queryString);
		}
		catch(SQLException ex){
			ex.printStackTrace();
		}
		finally{
			return ps;
		}
		
	}
	public static void closePreparedStatement(PreparedStatement ps){
		try{
		ps.close();
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
	} 
}
