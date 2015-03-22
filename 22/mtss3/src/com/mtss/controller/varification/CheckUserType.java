package com.mtss.controller.varification;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CheckUserType {
	
	static Log log = LogFactory.getLog(CheckUserType.class.getName());
	
	public String getUserType(String userType){
		String userPage = null;
		switch(userType){
			case "admin":
					userPage = "admin_user";
					break;
			case "operator":
					userPage = "operator_user";
					break;
			case "sales":
					userPage = "sales_user";
					break;
			case "gm":
					userPage = "gm_user";
					break;
			default:
					userPage = "invalid_user";
					break;
		}
		log.info("*****CheckUserType(getUserType) :User Type Is***** "+userPage);
		return userPage;
	}
}
