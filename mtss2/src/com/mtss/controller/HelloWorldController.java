package com.mtss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public ModelAndView sayHello() {
		String message = "Say Hello TO Spring";
		return new ModelAndView("login_user","message",message);
		//return new ModelAndView("index","message",message);
	}

}
