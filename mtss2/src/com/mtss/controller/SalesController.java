package com.mtss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.mtss.bean.Sales;
import com.mtss.service.sales.SalesUpdations;

@Controller
public class SalesController {

	@RequestMapping(value="/enterSalesData",method=RequestMethod.GET)
	public ModelAndView enterSalesData(){
		System.out.println("In SalesController's Get Method...........");
		return new ModelAndView("sales_manager","message","Valid Entry");
	}
	
	@RequestMapping(value="/saveSales",method=RequestMethod.POST)
	public ModelAndView saveSales(@ModelAttribute("sales")Sales sales){
		System.out.println("In SalesController's Post Method....................."+sales.getDoNo());
		new SalesUpdations().saveSales(sales);
		return new ModelAndView("sales_manager","message","Valid Entry");
	}
}
