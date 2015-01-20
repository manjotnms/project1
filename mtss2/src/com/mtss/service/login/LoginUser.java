package com.mtss.service.login;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mtss.bean.User;
import com.mtss.dao.UserDetail;

public class LoginUser {

	public boolean varifyUser(User user){
		//UserDetail userDetail = new UserDetailImplement();
		ApplicationContext appCtx;
		UserDetail userDetail=null;
		try {
			appCtx = new ClassPathXmlApplicationContext("Spring-Module.xml");
			System.out.println("ApplicationContxt Instace is Created................"+appCtx);
			userDetail = (UserDetail)appCtx.getBean("userDetail");
			System.out.println("Get User Detail Bean From IOC.............");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return userDetail.findUser(user); 
	}
}
