package com.mtss.service.sales;

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
}
