package com.mtss.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mtss.bean.User;
import com.mtss.services.AdminUpdations;

@Controller
public class AdminController {
	static Log log = LogFactory.getLog(AdminController.class.getName());
	@RequestMapping(value="/createUser",method=RequestMethod.GET)
	public String createNewUser(){
		
		log.info("*****AdminController(createNewUser) :Request for Create New User***** ");
		return "create_new_user";
	}
	
	@RequestMapping(value="/addNewUser")
	public String addNewUser(@ModelAttribute("user") User user,HttpServletRequest request){
		log.info("*****AdminController(addNewUser) : Add New User***** "+user.getUserName());
		if(new AdminUpdations().addNewUser(user)){
			log.info("*****AdminController(createNewUser) :Successfully User Is Create***** ");
			addUserListInSession(request);
			return "user_details";
		}
		else{
			log.info("*****AdminController(createNewUser) :User Is Not Created***** ");
			ModelAndView mad =  new ModelAndView();
			mad.addObject("userMessage", "Unable To Create New User:");
			return "create_new_user";
		}
	}
	
	@RequestMapping(value="/viewUserList",method=RequestMethod.GET)
	public String viewUserList(HttpServletRequest request){
		log.info("*****AdminController(viewUserList) : View User List***** ");
		addUserListInSession(request);
     	return "user_details";
	}
	
	@RequestMapping(value="/removeUser",method=RequestMethod.GET)
	public String removeUserAccount(@RequestParam String userName,HttpServletRequest request){
		log.info("*****AdminController(removeUserAccount) : Remove User Account***** "+userName);
		new AdminUpdations().removeUser(userName);
	    addUserListInSession(request);
	    return "user_details";
	}
	private void addUserListInSession(HttpServletRequest request){
		log.info("*****AdminController(addUserListInSession) : Set User In Session***** ");
		HttpSession session = request.getSession();
		List<User> userList = new AdminUpdations().getUserDetails();
		session.setAttribute("userList", userList);
	}
}
