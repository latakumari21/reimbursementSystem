package com.dsu.anu.dao;
import java.sql.*;

import com.dsu.anu.conns.Myconnection;
import com.dsu.anu.model.User;

public class UserDao {
	Connection con;
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	Myconnection myconn;
	public UserDao() {
		myconn= new Myconnection();
	}
	
	
	public User login(String userId, String password) {
		User u=null;
		con=myconn.getConnection();
		try {
			pstmt= con.prepareStatement("select * from user where userId = ? and password = ?");
			pstmt.setString(1,userId);
			pstmt.setString(2, password);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				u= new User();
				u.setUserId(rs.getString(1));
				u.setUserName(rs.getString(2));
				u.setDepartment(rs.getString(3));
				u.setRole(rs.getString(4));
				u.setManagerId(rs.getString(5));
				u.setPassword(rs.getString(6));
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return u;
		
	}	
	public boolean insertUser(User u) {
		boolean flag=false;
		con=myconn.getConnection();
		try {
			pstmt= con.prepareStatement("insert into user(userId,userName,password,department,role,managerId) values(?,?,?,?,?,?)");
			pstmt.setString(1,u.getUserId());
			pstmt.setString(2,u.getUserName());
			pstmt.setString(3,u.getPassword());
			pstmt.setString(4,u.getDepartment());
			pstmt.setString(5, u.getRole());
			pstmt.setString(6,u.getManagerId());
			
			pstmt.execute();
			flag=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag=false;
		}
		return flag;
		
	}

}
