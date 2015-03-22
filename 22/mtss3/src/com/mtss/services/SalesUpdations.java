package com.mtss.services;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mtss.bean.InternalCoalTranfer;
import com.mtss.bean.Sales;
import com.mtss.beanFactory.BeanFactory;
import com.mtss.dao.AdminDetail;
import com.mtss.dao.SalesDetail;

public class SalesUpdations {
	private static Log log = LogFactory.getLog(SalesUpdations.class.getName());
	
	public boolean doNoAvailability(String doNo){
		log.info("*****SalesUpdations(doNoAvailability) :Check doNo Availability***** "+doNo);
		SalesDetail salesDetail = (SalesDetail)BeanFactory.getRequestBean("salesDetail");
		return salesDetail.checkDoNoAvail(doNo);
	}
	
	public boolean saveSales(Sales sales){
		boolean status = false;
		
		SalesDetail salesDetail = (SalesDetail)BeanFactory.getRequestBean("salesDetail");
		log.info("*****SalesUpdations:(saveSales) Get SalesDtail Bean From IOC.............");
		try{
			salesDetail.saveSales(sales);
			status = true;
		}
		catch(Exception e){
			log.error("*****SalesUpdations:(saveSales) Exception In SalesUpdationsImplement*****"+e);
			status = false;
		}
		return status;
	}
	
	public boolean updateSales(Sales sales){
		boolean status = false;
		
		return status;
	}
	
	public void addNewTax(String taxName){
		log.info("*****SalesUpdations:(addNewTax) Get SalesDetail Bean From IOC.............");
		SalesDetail salesDetail = (SalesDetail)BeanFactory.getRequestBean("salesDetail");
		salesDetail.addNewTax(taxName);
	}
	public List<String> getTaxesList(){
		log.info("*****SalesUpdations:(getTaxesList) Get SalesDetail Bean From IOC.............");
		SalesDetail salesDetail = (SalesDetail)BeanFactory.getRequestBean("salesDetail");
		return salesDetail.getTaxesList();
	}
	public void deleteTax(String taxName){
		log.info("*****SalesUpdations:(deleteTax) Get SalesDetail Bean From IOC.............");
		SalesDetail salesDetail = (SalesDetail)BeanFactory.getRequestBean("salesDetail");
		salesDetail.deleteTax(taxName);
	}
	
	public boolean addInternalCoalTransfer(InternalCoalTranfer ict)throws Exception{
		log.info("*****SalesUpdations:(deleteTax) Get SalesDetail Bean From IOC.............");
		SalesDetail salesDetail = (SalesDetail)BeanFactory.getRequestBean("salesDetail");
		return salesDetail.addInternalCoalTransfer(ict);
	}
	
	public int getAutoGenratedDoNo()throws Exception{
		log.info("*****SalesUpdations:(getAutoGenratedDoNo) Get SalesDetail Bean From IOC.............");
		SalesDetail salesDetail = (SalesDetail)BeanFactory.getRequestBean("salesDetail");
		return salesDetail.getDoNo();
	}
}
