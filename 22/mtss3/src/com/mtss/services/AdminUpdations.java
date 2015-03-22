package com.mtss.services;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mtss.bean.EPC;
import com.mtss.bean.GPS;
import com.mtss.bean.ProductionVehicle;
import com.mtss.bean.User;
import com.mtss.beanFactory.BeanFactory;
import com.mtss.dao.AdminDetail;

public class AdminUpdations {
	
	private Log log = LogFactory.getLog(AdminUpdations.class.getName());
	
	public boolean userAvailability(String userName){
		log.info("*****AdminUpdations(userAvailability) :Check User Availability***** "+userName);
		AdminDetail adminDetail = (AdminDetail)BeanFactory.getRequestBean("adminDetail");
		return adminDetail.checkUserAvail(userName);
	}
	
	public List<User> getUserDetails(){
		log.info("*****AdminUpdations(getUserDetails) :Get User List*****");
		AdminDetail adminDetail = (AdminDetail)BeanFactory.getRequestBean("adminDetail");
		return adminDetail.viewUserList();
	}
	
	public boolean addNewUser(User user){
		log.info("*****AdminUpdations(addNewUser) :Add New User*****");
		AdminDetail adminDetail = (AdminDetail)BeanFactory.getRequestBean("adminDetail");
		return adminDetail.addNewUser(user);
	}
	
	public boolean removeUser(String userName){
		log.info("*****AdminUpdations(removeUser) :Remove User ***** "+userName);
		AdminDetail adminDetail = (AdminDetail)BeanFactory.getRequestBean("adminDetail");
		return adminDetail.removeUser(userName);
	}
	
	public boolean changeUserPassword(User user){
		log.info("*****AdminUpdations(changeUserPassword) :Change User's Password ***** "+user.getUserName());
		AdminDetail adminDetail = (AdminDetail)BeanFactory.getRequestBean("adminDetail");
		return adminDetail.changePasword(user);
	}
	
	public User getUserPassword(User user){
		log.info("*****AdminUpdations(getUserPassword) :Get Existing User's Password ***** "+user.getUserName());
		AdminDetail adminDetail = (AdminDetail)BeanFactory.getRequestBean("adminDetail");
		return adminDetail.getPassword(user);
	}
	
	public boolean epcAvailability(String epcNo){
		log.info("*****AdminUpdations(epcAvailability) :Check EPC_No Availability***** "+epcNo);
		AdminDetail adminDetail = (AdminDetail)BeanFactory.getRequestBean("adminDetail");
		return adminDetail.checkEpcNoAvail(epcNo);
	}
	
	public boolean enterEPCNumber(EPC epc){
		log.info("*****AdminUpdations(enterEPCNumber) :Enter EPC Number ***** "+epc.getEpcNo());
		AdminDetail adminDetail = (AdminDetail)BeanFactory.getRequestBean("adminDetail");
		return adminDetail.addNewTag(epc);
	}
	
	public boolean blockEPCNumber(EPC epc){
		log.info("*****AdminUpdations(blockEPCNumber) :Block EPC Number ***** "+epc.getEpcNo());
		AdminDetail adminDetail = (AdminDetail)BeanFactory.getRequestBean("adminDetail");
		return adminDetail.blockTag(epc);
	}
	
	public boolean enterGPSNumber(GPS gps){
		log.info("*****AdminUpdations(enterGPSNumber) :Enter GPS Number ***** "+gps.getIMEI());
		AdminDetail adminDetail = (AdminDetail)BeanFactory.getRequestBean("adminDetail");
		return adminDetail.addGPS(gps);
	}
	
	public boolean gpsAvailability(String gpsNo){
		log.info("*****AdminUpdations(gpsAvailability) :Check IMEI_No Availability***** "+gpsNo);
		AdminDetail adminDetail = (AdminDetail)BeanFactory.getRequestBean("adminDetail");
		return adminDetail.checkGPSAvail(gpsNo);
	}
	
	public boolean enterPerEPCGPCNo(ProductionVehicle proVeh){
		log.info("*****AdminUpdations(enterProVehDetail) :Enter Product Vehicle Detail ***** "+proVeh.getVehicleNo());
		AdminDetail adminDetail = (AdminDetail)BeanFactory.getRequestBean("adminDetail");
		return adminDetail.addPernaEPCGPS(proVeh);
	}
	
	public boolean lostEPCNo(String vehNo){
		log.info("*****AdminUpdations(lostEPCNo) :Lost EPC No where Vehicle Number is***** "+vehNo);
		AdminDetail adminDetail = (AdminDetail)BeanFactory.getRequestBean("adminDetail");
		return adminDetail.lostEPC(vehNo);
	}
	
	public boolean deleteEPCNo(int epcNo) {
		log.info("*****AdminUpdations(deleteEPCNo) :Delete EPC_NO***** "+epcNo);
		AdminDetail adminDetail = (AdminDetail)BeanFactory.getRequestBean("adminDetail");
		return adminDetail.deleteEPC(epcNo);
	}
	
	public List<EPC> getEPCList() {
		log.info("*****AdminUpdations(getEPCList) :Get EPC Number List***** ");
		AdminDetail adminDetail = (AdminDetail)BeanFactory.getRequestBean("adminDetail");
		return adminDetail.epcDetail();
	}
	
	public boolean deleteGPSNo(int gpsNo) {
		log.info("*****AdminUpdations(deleteGPSNo) :Delete GPS_NO***** "+gpsNo);
		AdminDetail adminDetail = (AdminDetail)BeanFactory.getRequestBean("adminDetail");
		return adminDetail.deleteGPS(gpsNo);
	}
	
	public boolean lostGPSNo(String vehNo){
		log.info("*****AdminUpdations(lostGPSNo) :Lost GPS No where Vehicle Number is***** "+vehNo);
		AdminDetail adminDetail = (AdminDetail)BeanFactory.getRequestBean("adminDetail");
		return adminDetail.lostGPS(vehNo);
	}
	
	public List<GPS> getGPSList() {
		log.info("*****AdminUpdations(getGPSList) :Get GPS Number List***** ");
		AdminDetail adminDetail = (AdminDetail)BeanFactory.getRequestBean("adminDetail");
		return adminDetail.gpsDetail();
	}
	
	public boolean vehAvailability(String vehNo){
		log.info("*****AdminUpdations(vehAvailability) :Check Vehicle Availability***** "+vehNo);
		AdminDetail adminDetail = (AdminDetail)BeanFactory.getRequestBean("adminDetail");
		return adminDetail.checkVehAvail(vehNo);
	}
	
	public boolean proVehAvailability(String vehNo){
		log.info("*****AdminUpdations(proVehAvailability) :Check Production Vehicle Availability***** "+vehNo);
		AdminDetail adminDetail = (AdminDetail)BeanFactory.getRequestBean("adminDetail");
		return adminDetail.proCheckVehAvail(vehNo);
	}
}
