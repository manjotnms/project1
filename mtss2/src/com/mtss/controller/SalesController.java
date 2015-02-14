package com.mtss.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mtss.bean.Sales;
import com.mtss.beanFactory.BeanFactory;
import com.mtss.services.SalesUpdations;

@Controller
public class SalesController {
	static Log log = LogFactory.getLog(SalesController.class.getName());
	@RequestMapping(value="/enterSalesData",method=RequestMethod.GET)
	public ModelAndView enterSalesData(HttpServletRequest request){
		log.info("*****SalesController(enterSalesData) :Enter Sales Detail");
		setListInSession(request);
		return new ModelAndView("sales_manager","message","Valid Entry");
	}
	
	@RequestMapping(value="/saveSales",method=RequestMethod.POST)
	public ModelAndView saveSales(@ModelAttribute("sales")Sales sales,HttpServletRequest request){
		log.info("*****SalesController(saveSales) :Save Sales's Detail");
		SalesUpdations salesUpdates = new SalesUpdations();
		salesUpdates.saveSales(sales);
		setListInSession(request);
		return new ModelAndView("sales_manager","message","Valid Entry");
	}
	
	@RequestMapping(value="/addNewTaxName",method=RequestMethod.GET)
	public ModelAndView addNewTaxName(@RequestParam("taxName")String taxName,HttpServletRequest request){
		log.info("*****SalesController(addNewTaxName) :Add New Tax Name***** "+taxName);
		SalesUpdations salesUpdates = new SalesUpdations();
		salesUpdates.addNewTax(taxName);
		setListInSession(request);
		return new ModelAndView("sales_manager","message","Valid Entry");
	}
	
	@RequestMapping(value="/deleteTaxRequest",method=RequestMethod.GET)
	public ModelAndView deleteTaxRequest(HttpServletRequest request){
		log.info("*****SalesController(deleteTaxRequest) :Delete Tax***** ");
		setListInSession(request);
		return new ModelAndView("delete_tax","message","Valid Entry");
	}
	@RequestMapping(value="/deleteTax",method=RequestMethod.GET)
	public ModelAndView deleteTax(HttpServletRequest request){
		
		log.info("*****SalesController(deleteTax) :Delete Tax***** ");
		
		SalesUpdations salesUpdates = new SalesUpdations();
		
		String taxName = request.getParameter("taxName");
		salesUpdates.deleteTax(taxName);
		setListInSession(request);
		return new ModelAndView("sales_manager","message","Valid Entry");
	}
	
	private void setListInSession(HttpServletRequest request){
		log.info("*****SalesController(addNewTaxName) :Set Taxes List In Session Scope***** ");
		SalesUpdations salesUpdates = new SalesUpdations();
		List<String> taxesList = salesUpdates.getTaxesList();
		request.getSession().setAttribute("taxesList",taxesList);
	}
}
