package com.mtss.dao;

import com.mtss.bean.User;

public interface UserDetail {
	public void addUser(User user);
	public boolean findUser(User user);
	public void removeUser(String userID);
	public void updateUser(User user);
}
