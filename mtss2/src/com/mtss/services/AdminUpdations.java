package com.mtss.services;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mtss.bean.User;
import com.mtss.beanFactory.BeanFactory;
import com.mtss.dao.AdminDetail;

public class AdminUpdations {
	private Log log = LogFactory.getLog(AdminUpdations.class.getName());
	public List<User> getUserDetails(){
		
		log.info("*****AdminUpdations(getUserDetails) :Get User List*****");
		AdminDetail adminDetail = (AdminDetail)BeanFactory.getRequestBean("adminDetail");
		return adminDetail.viewUserList();
	}
	
	public boolean addNewUser(User user){
		log.info("*****AdminUpdations(addNewUser) :Add New User*****");
		AdminDetail adminDetail = (AdminDetail)BeanFactory.getRequestBean("adminDetail");
		return adminDetail.addNewUser(user);
	}
	
	public boolean removeUser(String userName){
		log.info("*****AdminUpdations(removeUser) :Remove User ***** "+userName);
		AdminDetail adminDetail = (AdminDetail)BeanFactory.getRequestBean("adminDetail");
		return adminDetail.removeUser(userName);
	}
}
