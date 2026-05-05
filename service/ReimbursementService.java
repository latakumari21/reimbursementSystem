package com.dsu.anu.service;

import java.sql.ResultSet;

import com.dsu.anu.dao.ReimbursementDao;
import com.dsu.anu.model.Reimbursement;

public class ReimbursementService {
	
	ReimbursementDao rdao;
	public ReimbursementService() {
	rdao= new ReimbursementDao();
	}
	public boolean createRequestsvc(Reimbursement r) {
		return rdao.createRequest(r);
		
	}
	
	public ResultSet viewRequestSvc(String userId) {
		return rdao.getRequestsByUser(userId);
	}
	
	public ResultSet getManagerRequests(String managerId) {
		return rdao.getRequestsForManager(managerId);
	}
	
	public boolean updateRequestSvc(int reqId, String userId, String type, double amount) {
	    return rdao.updateRequest(reqId, userId, type, amount);
	}
	
	public boolean deleteRequestSvc(int reqId, String userId) {
	    return rdao.deleteRequest(reqId, userId);
	}
	
	public boolean updateManagerStatusSvc(int reqId,String status,String comment) {
		return rdao.updateManagerStatus(reqId, status, comment);
	}
	
	public ResultSet getApprovedRequestsSvc() {
		return rdao.getApprovedRequests();
	}
	
	public boolean creditRequest(int reqId, double amount, String comment) {
	    return rdao.creditRequest(reqId, amount, comment);
	}


}
