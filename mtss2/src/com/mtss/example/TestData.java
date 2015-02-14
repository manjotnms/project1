package com.mtss.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mtss.bean.Sales;
import com.mtss.beanFactory.BeanFactory;
import com.mtss.dao.SalesDetail;
import com.mtss.daoImpl.SalesDetailImplement;

public class TestData {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// First 10 Records
		Sales sales = new Sales();
		sales.setDoNo("1234");
		sales.setWayBridgeId(1111);
		sales.setPurchaserName("Pur_AAAAA");
		sales.setDestination("Dest_BBBBB");
		sales.setStateCode("Sta1234");
		sales.setGrade("grade");
		sales.setDoDate("18/01/2015");
		sales.setApplNo("12345");
		sales.setApplDate("18/01/2015");
		sales.setDoQty(1000.00f);
		
		sales.setDraftNo1(11111111);
		sales.setDraftDt1(11111);
		sales.setDraftAmt1(1111.11);
		sales.setBank1("Yes Bank1");
		sales.setDraftNo2(22222222);
		sales.setDraftDt2(2222);
		sales.setDraftAmt2(2222.22);
		sales.setBank2("Yes Bank2");
		sales.setDraftNo3(33333333);
		sales.setDraftDt3(33333);
		sales.setDraftAmt3(3333.33);
		sales.setBank3("Yes Bank3");
		
		sales.setQtyBalance(5555.55);
		sales.setTaxtType("J");
		sales.setCustCd(212953);
		sales.setExcRegNo("AADCS6585LEM001");
		sales.setRange("XXX");
		sales.setDivision("DHANBAD");
		sales.setCommission("RANCHI");
		sales.setVattinNo(20111500787L);
		sales.setCstNo("KT-933 ( C)");
		
		sales.setBasicRate(1610.00);
		sales.setPan("XXX");
		sales.setDoStartDate("18/01/2015");
		sales.setDoEndDate("20/01/2015");
		
		//Tax and Prices
		sales.setTaxPercent(5.0);
		sales.setRoyalty(225.40);
		sales.setSed(10.00);
		sales.setCleanEngyCess(50.00);
		sales.setWeighMeBt(1.00);
		sales.setSlc(180.00);
		sales.setWrc(753.00);
        sales.setBazarFee(16.10);
        sales.setCentExcRate(6.00);
        sales.setEduCessRate(2.00);
        sales.setHighEduRate(1.00);
        sales.setRoadCess(0);
        sales.setAmbhCess(0);
        sales.setDoNo("1234");
	
		SalesDetail salesDetail = (SalesDetail)BeanFactory.getRequestBean("salesDetail");
		
		try {
			salesDetail.saveSales(sales);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Exception In TestData: "+e);
		}
	}

}
