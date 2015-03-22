package com.mtss.bean;

public class ProductionVehicle {
	int id,EPCNo,GPSNo;
	String vehicleNo;
	Long tareWeight;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEPCNo() {
		return EPCNo;
	}
	public void setEPCNo(int ePCNo) {
		EPCNo = ePCNo;
	}
	public int getGPSNo() {
		return GPSNo;
	}
	public void setGPSNo(int gPSNo) {
		GPSNo = gPSNo;
	}
	public String getVehicleNo() {
		return vehicleNo;
	}
	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}
	public Long getTareWeight() {
		return tareWeight;
	}
	public void setTareWeight(Long tareWeight) {
		this.tareWeight = tareWeight;
	}
	
}
