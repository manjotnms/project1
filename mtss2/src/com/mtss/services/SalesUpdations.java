package com.mtss.services;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import com.mtss.bean.Sales;
import com.mtss.dao.SalesDetail;
import com.mtss.daoImpl.SalesDetailImplement;

public class SalesUpdations {
	public boolean saveSales(Sales sales){
		boolean status = false;
		//SalesDetail salesDetail = new SalesDetailImplement();
		ApplicationContext appCtx = new ClassPathXmlApplicationContext("Spring-Module.xml");
		System.out.println("ApplicationContxt Instace is Created................"+appCtx);
		SalesDetail salesDetail = (SalesDetail)appCtx.getBean("salesDetail");
		System.out.println("Get SalesDtail Bean From IOC.............");
		try{
			salesDetail.saveSales(sales);
			status = true;
		}
		catch(Exception e){
			System.out.println("Exception In SalesUpdationsImplement: "+e);
			status = false;
		}
		return status;
	}
	
	public boolean updateSales(Sales sales){
		boolean status = false;
		
		return status;
	}
	
	public void addNewTax(String taxName){
		ApplicationContext appCtx = new ClassPathXmlApplicationContext("Spring-Module.xml");
		SalesDetail salesDetail = (SalesDetail)appCtx.getBean("salesDetail");
		salesDetail.addNewTax(taxName);
	}
	public List<String> getTaxesList(){
		ApplicationContext appCtx = new ClassPathXmlApplicationContext("Spring-Module.xml");
		SalesDetail salesDetail = (SalesDetail)appCtx.getBean("salesDetail");
		return salesDetail.getTaxesList();
	}
	public void deleteTax(String taxName){
		ApplicationContext appCtx = new ClassPathXmlApplicationContext("Spring-Module.xml");
		SalesDetail salesDetail = (SalesDetail)appCtx.getBean("salesDetail");
		salesDetail.deleteTax(taxName);
	}
}
