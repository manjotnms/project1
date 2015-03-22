package com.mtss.dao;

import java.util.List;

import com.mtss.bean.Activities;
import com.mtss.bean.EPC;
import com.mtss.bean.GPS;

public interface FrontOfficeDetail {
	
	public boolean checkDoNoAvail(String doNo);
	public boolean checkEpcNoStatus(String epcNo);
	public List<GPS> getIMEINoList();
	public boolean enterDispatchExternalDetail(String DoNo,String EpcNo,String IMEI,String VechNo,String RLW);
	public boolean enterDispatchInternalDetail(String DoNo,String EpcNo,String IMEI,String VechNo,String RLW);
	public boolean enterOtherGoodsDetail(String otheGoods,String EpcNo,String VechNo);
	
	public EPC getEPCDetail(String epcNo);
	public Activities getActivityDetail(int epcNo,int activityNo,int epcType);
}
