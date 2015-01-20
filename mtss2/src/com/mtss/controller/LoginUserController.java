package com.mtss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mtss.bean.User;
import com.mtss.controller.varification.CheckUserType;
import com.mtss.service.login.LoginUser;

@Controller
public class LoginUserController {
	/*@RequestMapping(value="/varifyUser",method=RequestMethod.POST)
	public String varifyUser(@RequestParam("userID")String userID,
							 @RequestParam("password")String password,
							 @RequestParam("userType")String userType){
		System.out.println("Login User Status: \nUserID: "+userID+"\tPassword: "+password+"\tUserType: "+userType);
		
		String status = "sales_manager";
		return status;
	}*/
	
	@RequestMapping(value="/varifyUser",method=RequestMethod.POST)
	public String varifyUser(@ModelAttribute("user")User user){
		
			String status = "invalid_user";
		
			System.out.println("Login User Status: \n\tUserID: "+user.getUserID()+"\tPassword: "+user.getPassword()+"\tUserType: "+user.getUserType());
			if(new LoginUser().varifyUser(user)){
				status = new CheckUserType().getUserType(user.getUserType());
			}
			return status;
	}
}
