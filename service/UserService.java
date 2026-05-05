package com.dsu.anu.service;

import com.dsu.anu.dao.UserDao;
import com.dsu.anu.model.User;

public class UserService {
	UserDao ud;
	public UserService() {
	
	 ud= new UserDao();
	}
	public boolean addUser(User u) {
	    return ud.insertUser(u);
	}
	
	public User loginserviice(String userId, String password) {
		return ud.login(userId, password);
	}
}
