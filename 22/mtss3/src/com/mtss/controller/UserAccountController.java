package com.mtss.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mtss.bean.User;
import com.mtss.controller.varification.CheckUserType;
import com.mtss.services.ManageUserAccount;

@Controller
public class UserAccountController {
	/*@RequestMapping(value="/varifyUser",method=RequestMethod.POST)
	public String varifyUser(@RequestParam("userID")String userID,
							 @RequestParam("password")String password,
							 @RequestParam("userType")String userType){
		System.out.println("Login User Status: \nUserID: "+userID+"\tPassword: "+password+"\tUserType: "+userType);
		
		String status = "sales_manager";
		return status;
	}*/
	static Log log = LogFactory.getLog(UserAccountController.class.getName());
	@RequestMapping(value="/verifyUser",method=RequestMethod.POST)
	public String verifyUser(@ModelAttribute("user")User user,HttpServletRequest request){
		
			log.info("*****UserAccountController(verifyUser) :check user is valid***** ");
			String status = "invalid_user";
		
			System.out.println("Login User Status: \n\tUserID: "+user.getUserName()+"\tPassword: "+user.getPassword()+"\tUserType: "+user.getUserType());
			try{
				
				if(new ManageUserAccount().verifyUser(user)){
					status = new CheckUserType().getUserType(user.getUserType());
					request.getSession().setAttribute("user",user);
					log.info("*****UserAccountController(verifyUser) :Entered User Is Valid***** "+user.getUserName());
				}
			}
			catch(Exception e){
				log.error("*****UserAccountController(verifyUser) :Error In Login User *****");
				request.setAttribute("invalid_user", "Invalid User name or Invalid Password Or Invalid User Type");
				status = "login_user";
			}
			return status;
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String logoutUser(HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info("*****UserAccountController(logoutUser) :Logout User and Remove User from session:");
		
		HttpSession session = request.getSession(false);
		if (session != null){
			User user = (User) session.getAttribute("user");
			if(user!=null){
				session.removeAttribute("user");
				session.invalidate();
			
				log.info("*****UserAccountController(logoutUser) :Store User Activity***** ");
				new ManageUserAccount().removeUserInfo(user);
			}
		}
		return "login_user";
	}
}
