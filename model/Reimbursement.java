package com.dsu.anu.model;

public class Reimbursement {
    private int reqId ;
    private String userId ;
    private String billType ;
    private double amount ;
    private String status ;
    private String managerComment ;
    private String financeComment ;
    private String paymentStatus;
    private double paidAmount;

	public Reimbursement() {
		super();
	}
	public Reimbursement( String userId, String billType, double amount, String status) {
		super();
		this.userId = userId;
		this.billType = billType;
		this.amount = amount;
		this.status = status;
	}
	public int getReqId() {
		return reqId;
	}
	public void setReqId(int reqId) {
		this.reqId = reqId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getManagerComment() {
		return managerComment;
	}
	public void setManagerComment(String managerComment) {
		this.managerComment = managerComment;
	}
	public String getFinanceComment() {
		return financeComment;
	}
	public void setFinanceComment(String financeComment) {
		this.financeComment = financeComment;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public double getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(double paidAmount) {
		this.paidAmount = paidAmount;
	}
	@Override
	public String toString() {
		return "Reimbursement [reqId=" + reqId + ", userId=" + userId + ", billType=" + billType + ", amount=" + amount
				+ ", status=" + status + ", managerComment=" + managerComment + ", financeComment=" + financeComment
				+ ", paymentStatus=" + paymentStatus + ", paidAmount=" + paidAmount + "]";
	}
	
	
}
