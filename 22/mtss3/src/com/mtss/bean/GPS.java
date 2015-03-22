package com.mtss.bean;

public class GPS {
	int IMEI,avail,inUse;
	String dateOfIssue;
	
	public int getIMEI() {
		return IMEI;
	}
	public void setIMEI(int iMEI) {
		IMEI = iMEI;
	}
	public int getAvail() {
		return avail;
	}
	public void setAvail(int avail) {
		this.avail = avail;
	}
	public int getInUse() {
		return inUse;
	}
	public void setInUse(int inUse) {
		this.inUse = inUse;
	}
	public String getDateOfIssue() {
		return dateOfIssue;
	}
	public void setDateOfIssue(String dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}
}
