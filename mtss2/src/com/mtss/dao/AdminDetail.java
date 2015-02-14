package com.mtss.dao;

import java.util.List;

import com.mtss.bean.User;

public interface AdminDetail {
	public boolean addNewUser(User user);
	public String getPassword(User user);
	public boolean changePasword(User user);
	public boolean addNewTag(String tagNo);
	public boolean blockTag(String tagNo);
	public boolean unblockTag(String tagNo);
	public boolean addGPS();
	public boolean removeUser(String userName);
	public List<User> viewUserList();
}
