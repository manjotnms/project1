package com.mtss.services;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mtss.bean.Activities;
import com.mtss.bean.EPC;
import com.mtss.bean.GPS;
import com.mtss.beanFactory.BeanFactory;
import com.mtss.dao.AdminDetail;
import com.mtss.dao.FrontOfficeDetail;
import com.mtss.dao.SalesDetail;
import com.mtss.daoImpl.FrontOfficeDetailImplement;

public class FrontOfficeUpdations {
	
	private Log log = LogFactory.getLog(FrontOfficeUpdations.class.getName());
	public List<GPS> getIMEIList(){
		
		log.info("***** FrontOfficeUpdations:getIMEIList() Get IMEI List *****");
		FrontOfficeDetail frontOff = (FrontOfficeDetail)BeanFactory.getRequestBean("frontOfficeDetail");
		return frontOff.getIMEINoList();
	}
	
	public boolean doNoAvailability(String doNo){
		log.info("*****FrontOfficeUpdations: (doNoAvailability) :Check doNo Availability***** "+doNo);
		FrontOfficeDetail frontOff = (FrontOfficeDetail)BeanFactory.getRequestBean("frontOfficeDetail");
		return frontOff.checkDoNoAvail(doNo);
	}
	
	public boolean epcAvailability(String epcNo){
		log.info("*****FrontOfficeUpdations(epcAvailability) :Check EPC_No Availability***** "+epcNo);
		FrontOfficeDetail frontOff = (FrontOfficeDetail)BeanFactory.getRequestBean("frontOfficeDetail");
		return frontOff.checkEpcNoStatus(epcNo);
	}
	
	public boolean updateDispatchExternal(String DoNo,String EpcNo,String IMEI,String VechNo,String RLW){
		log.info("*****FrontOfficeUpdations(updateDispatchExternal) :Dispatch External***** ");
		log.info("*****DoNo: "+DoNo+" EPC_No: "+EpcNo+" IMEI: "+IMEI+" VECHICLE_NO: "+VechNo+" RLW: "+RLW+" *****");
		FrontOfficeDetail frontOff = (FrontOfficeDetail)BeanFactory.getRequestBean("frontOfficeDetail");
		return frontOff.enterDispatchExternalDetail(DoNo, EpcNo, IMEI, VechNo, RLW);
	}
	
	public boolean updateDispatchInternal(String DoNo,String EpcNo,String IMEI,String VechNo,String RLW){
		log.info("*****FrontOfficeUpdations(updateDispatchExternal) :Dispatch Internal***** ");
		log.info("*****DoNo: "+DoNo+" EPC_No: "+EpcNo+" IMEI: "+IMEI+" VECHICLE_NO: "+VechNo+" RLW: "+RLW+" *****");
		FrontOfficeDetail frontOff = (FrontOfficeDetail)BeanFactory.getRequestBean("frontOfficeDetail");
		return frontOff.enterDispatchInternalDetail(DoNo, EpcNo, IMEI, VechNo, RLW);
	}
	
	public boolean updateForOtherGoods(String otheGoods,String EpcNo,String VechNo){
		log.info("*****FrontOfficeUpdations(updateForOtherGoods) :Other Goods***** ");
		log.info("*****: EPC_No: "+EpcNo+" Other Goods: "+otheGoods+" VECHICLE_NO: "+VechNo+" *****");
		FrontOfficeDetail frontOff = (FrontOfficeDetail)BeanFactory.getRequestBean("frontOfficeDetail");
		return frontOff.enterOtherGoodsDetail(otheGoods, EpcNo, VechNo);
	}
	
	public EPC epcDetail(String epcNo) {
		log.info("*****FrontOfficeUpdations(epcDetail) : Get EPC Detail***** ");
		FrontOfficeDetail frontOff = (FrontOfficeDetail)BeanFactory.getRequestBean("frontOfficeDetail");
		return frontOff.getEPCDetail(epcNo);
	}
	
	public Activities activityDetail(int epcNo,int activityNo,int epcType) {
		log.info("*****FrontOfficeUpdations(activityDetail) : Get Activity Detail For EPC_No: "+epcNo+" Activity_No: "+activityNo+" EPC_Type: "+epcType+"***** ");
		FrontOfficeDetail frontOff = (FrontOfficeDetail)BeanFactory.getRequestBean("frontOfficeDetail");
		return frontOff.getActivityDetail(epcNo, activityNo, epcType);
	}
}
