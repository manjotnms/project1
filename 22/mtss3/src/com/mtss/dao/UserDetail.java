package com.mtss.dao;

import com.mtss.bean.User;

public interface UserDetail {
	public boolean findUser(User user);
	public void logoutUser(User user);
}
