package com.mtss.controller.varification;

public class CheckUserType {

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
		return userPage;
	}
}
