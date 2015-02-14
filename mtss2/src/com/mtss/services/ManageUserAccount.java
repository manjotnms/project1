package com.mtss.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mtss.bean.User;
import com.mtss.beanFactory.BeanFactory;
import com.mtss.dao.UserDetail;

public class ManageUserAccount {
	private Log log = LogFactory.getLog(ManageUserAccount.class.getName());
	public boolean verifyUser(User user){
		UserDetail userDetail=(UserDetail)BeanFactory.getRequestBean("userDetail");
		log.info("*****ManageUserAccount(verifyUser) :Verify User***** "+user.getUserName());
		return userDetail.findUser(user); 
	}
}
