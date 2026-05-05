package com.dsu.anu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.dsu.anu.conns.Myconnection;
import com.dsu.anu.model.Reimbursement;

public class ReimbursementDao {
	Connection con;
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs;
	Myconnection myconn;
	
	public ReimbursementDao() {
		myconn= new Myconnection();
	}
	
	public boolean createRequest(Reimbursement r) {
		boolean flag;
		con=myconn.getConnection();
		try {
			pstmt=con.prepareStatement("insert into reimbursement(userId,billType,amount,status) values(?,?,?,?)");
			pstmt.setString(1,r.getUserId());
			pstmt.setString(2,r.getBillType());
			pstmt.setDouble(3, r.getAmount());
			pstmt.setString(4,r.getStatus());
			pstmt.execute();
			flag=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 flag=false;
		}
		
		
		return flag;
		
	}
	public ResultSet getRequestsByUser(String userId) {
		con = myconn.getConnection();
	    try {
	        pstmt = con.prepareStatement(
	            "SELECT * FROM reimbursement WHERE userId=?"
	        );
	        pstmt.setString(1, userId);
	        rs = pstmt.executeQuery();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return rs;
	}
	public ResultSet getRequestsForManager(String managerId) {
		con = myconn.getConnection();

	    try {
	        pstmt = con.prepareStatement(
	            "SELECT * FROM reimbursement WHERE userId IN " +
	            "(SELECT userId FROM user WHERE managerId=?)"
	        );

	        pstmt.setString(1, managerId);
	        rs = pstmt.executeQuery();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return rs;
	}
	
	
	public boolean updateManagerStatus(int reqId,String status, String comment) {
		con=myconn.getConnection();
		boolean flag=false;
		try {
			pstmt=con.prepareStatement("Update reimbursement set status=? , managerComment=? where reqId=?");
			pstmt.setString(1,status);
			pstmt.setString(2, comment);
			pstmt.setInt(3,reqId);
			pstmt.executeUpdate();
			flag=true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}
	public ResultSet getApprovedRequests() {
	    con = myconn.getConnection();

	    try {
	        pstmt = con.prepareStatement("SELECT * FROM reimbursement WHERE status='APPROVED' AND (paymentStatus IS NULL OR paymentStatus!='PAID')");
	        rs = pstmt.executeQuery();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return rs;
	}
	public boolean updateRequest(int reqId, String userId, String type, double amount) {
	    String sql = "UPDATE reimbursement SET billType=?, amount=? " +
	                 "WHERE reqId=? AND userId=? AND status='OPEN'";

	    try (Connection con = myconn.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, type);
	        ps.setDouble(2, amount);
	        ps.setInt(3, reqId);
	        ps.setString(4, userId);

	        return ps.executeUpdate() > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	public boolean deleteRequest(int reqId, String userId) {
	    String sql = "DELETE FROM reimbursement " +
	                 "WHERE reqId=? AND userId=? AND status='OPEN'";

	    try (Connection con = myconn.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setInt(1, reqId);
	        ps.setString(2, userId);

	        return ps.executeUpdate() > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return false;
	}
	
	
	
	
	public boolean creditRequest(int reqId, double amount, String comment) {

	    boolean flag = false;
	    con = myconn.getConnection();

	    try {
	        String checkQuery = "SELECT status, paymentStatus FROM reimbursement WHERE reqId=?";
	        pstmt = con.prepareStatement(checkQuery);
	        pstmt.setInt(1, reqId);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {

	            String status = rs.getString("status");
	            String paymentStatus = rs.getString("paymentStatus");

	            // Already paid
	            if ("PAID".equalsIgnoreCase(paymentStatus)) {
	                System.out.println("Request already paid");
	                return false;
	            }

	            // Not approved yet
	            if (!"APPROVED".equalsIgnoreCase(status)) {
	                System.out.println("Request is not approved yet");
	                return false;
	            }

	            //Step 2: Credit + Close
	            String updateQuery = "UPDATE reimbursement SET " +
	                    "status='CLOSED', " +
	                    "paymentStatus='PAID', " +
	                    "paidAmount=?, " +
	                    "financeComment=? " +
	                    "WHERE reqId=?";

	            pstmt = con.prepareStatement(updateQuery);
	            pstmt.setDouble(1, amount);
	            pstmt.setString(2, comment);
	            pstmt.setInt(3, reqId);

	            int rows = pstmt.executeUpdate();

	            if (rows > 0) {
	                flag = true;
	            }
	        } else {
	            System.out.println("Request ID not found");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return flag;
	}


	}
	


