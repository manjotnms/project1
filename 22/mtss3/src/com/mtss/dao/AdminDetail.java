package com.mtss.dao;

import java.util.List;

import com.mtss.bean.EPC;
import com.mtss.bean.GPS;
import com.mtss.bean.ProductionVehicle;
import com.mtss.bean.User;

public interface AdminDetail {
	//User Management
	public boolean addNewUser(User user);
	public User getPassword(User user);
	public boolean changePasword(User user);
	public boolean removeUser(String userName);
	public List<User> viewUserList();
	public boolean checkUserAvail(String userName);
	
	//EPC Management
	public boolean checkEpcNoAvail(String epcNo);
	public boolean addNewTag(EPC epc);
	public boolean deleteEPC(int epcNo);
	public boolean lostEPC(String vehicleNo);
	public boolean unblockTag(String tagNo);
	public boolean blockTag(EPC epc);
	public List<EPC> epcDetail();
	
	//GPS Management
	public boolean addGPS(GPS gps);
	public boolean deleteGPS(int imeiNo);
	public boolean checkGPSAvail(String IMEINo);
	public boolean lostGPS(String vehicleNo);
	public List<GPS> gpsDetail(); 
	
	//Permannet GPS and EPC Number
	public boolean addPernaEPCGPS(ProductionVehicle proVeh);
	public boolean checkVehAvail(String vehNo);
	public boolean proCheckVehAvail(String vehNo);
}
