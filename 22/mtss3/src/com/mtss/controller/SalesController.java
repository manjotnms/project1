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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mtss.bean.InternalCoalTranfer;
import com.mtss.bean.Sales;
import com.mtss.services.AdminUpdations;
import com.mtss.services.SalesUpdations;

@Controller
public class SalesController {
	static Log log = LogFactory.getLog(SalesController.class.getName());
	
	@RequestMapping(value="/doNoAvail",method=RequestMethod.GET)
	@ResponseBody
	public String doNoAvailable(@RequestParam String doNo){
		log.info("*****SalesController(doNoAvailable) : Check User Avalilability of Do_No***** "+doNo);
		if(new SalesUpdations().doNoAvailability(doNo))
			return "true";
		else 
			return "false";
	}
	
	@RequestMapping(value="/enterSalesData",method=RequestMethod.GET)
	public ModelAndView enterSalesData(HttpServletRequest request){
		log.info("*****SalesController(enterSalesData) :Enter Sales Detail");
		setListInSession(request);
		return new ModelAndView("sales_manager","message","Valid Entry");
	}
	
	@RequestMapping(value="/saveSales",method=RequestMethod.POST)
	public ModelAndView saveSales(@ModelAttribute("sales")Sales sales,HttpServletRequest request){
		log.info("*****SalesController(saveSales) :Save Sales's Detail***** "+sales.getDoDate());
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
	
	@RequestMapping(value="/interCoalTransRequest",method=RequestMethod.GET)
	public String interCoalTransRequest(HttpServletRequest request){
		log.info("*****SalesController(interCoalTransRequest) :Get Auto Genrated DoNo***** ");
		try{
			int doNo = new SalesUpdations().getAutoGenratedDoNo();
			request.getSession().setAttribute("doNo",doNo+1);
			log.info("*****SalesController(interCoalTransRequest) :Internal Coal Transfer Request***** ");
		}
		catch(Exception e){
			log.error("*****SalesController(interCoalTransRequest) :Internal Coal Transfer Request***** ");
		}
		return "inter_coal_trans";
	}
	
	@RequestMapping(value="/interCoalTrans",method=RequestMethod.POST)
	public String addInterCoalTrans(@ModelAttribute("interCoalTrans")InternalCoalTranfer ict){
		log.info("*****SalesController(addInterCoalTrans) :Add Internal Coal Transfer Into Database***** ");
		String result = "sales_user";
		try{
		    boolean isUpdated = new SalesUpdations().addInternalCoalTransfer(ict);
		    if(isUpdated)
		    	result = "sales_user";
		    else
		    	result = "error";
		}
		catch(Exception e){
			log.error("*****SalesController(addInterCoalTrans) :Add Internal Coal Transfer Into Database***** "+e);
			result = "error";
		}
		return result;
	}
	
	private void setListInSession(HttpServletRequest request){
		log.info("*****SalesController(addNewTaxName) :Set Taxes List In Session Scope***** ");
		SalesUpdations salesUpdates = new SalesUpdations();
		List<String> taxesList = salesUpdates.getTaxesList();
		request.getSession().setAttribute("taxesList",taxesList);
	}
}
