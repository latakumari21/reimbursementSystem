package com.dsu.anu.conns;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Myconnection {
	Connection con;
	Statement stmt;
	ResultSet rs;
	PreparedStatement pstmt;
	String url="jdbc:mysql://localhost:3306/reimbursement";
	String password="lata";
	String user="root";
	
	public Connection getConnection() {
	try {
		Class.forName("com.mysql.cj.jdbc.Driver");
		con= DriverManager.getConnection(url, user, password);
		
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	catch(SQLException se) {
		se.printStackTrace();
	}
	return con;
	}
}
