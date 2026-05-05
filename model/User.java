package com.dsu.anu.model;

public class User {
	private String userId ;
	private String password;
    private String userName ;
    private String department ;
    private String role ;
    private String managerId ;
	public User() {
		super();
	}
	public User(String userId,String password, String userName, String department, String role, String managerId) {
		super();
		this.userId = userId;
		this.password=password;
		this.userName = userName;
		this.department = department;
		this.role = role;
		this.managerId = managerId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getManagerId() {
		return managerId;
	}
	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", department=" + department + ", role=" + role
				+ ", managerId=" + managerId + "]";
	}
	
    
}
