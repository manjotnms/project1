package mtss.beans.BO;
// default package
// Generated 14 Feb, 2015 11:57:52 AM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

/**
 * Deliveryorderdetails generated by hbm2java
 */
public class Deliveryorderdetails implements java.io.Serializable {

	private int id;
	private Taxdetails taxdetails;
	private String doNo;
	private int wayBridgeId;
	private String purchaserName;
	private String destination;
	private String stateCode;
	private String grade;
	private Date doDate;
	private int applNo;
	private Date applDate;
	private long doQty;
	private String draftNo1;
	private Date draftDt1;
	private double draftAmt1;
	private String draftNo2;
	private Date draftDt2;
	private double draftAmt2;
	private String bank2;
	private String draftNo3;
	private Date draftDt3;
	private double draftAmt3;
	private String bank3;
	private long qtyBalance;
	private String range;
	private String division;
	private String commission;
	private String vattinNo;
	private String cstNo;
	private BigDecimal basicRate;
	private String pan;
	private Date doStartDate;
	private Date doEndDate;

	public Deliveryorderdetails() {
	}

	public Deliveryorderdetails(int id, Taxdetails taxdetails, String doNo,
			int wayBridgeId, String purchaserName, String destination,
			String stateCode, String grade, Date doDate, int applNo,
			Date applDate, long doQty, String draftNo1, double draftAmt1,
			Date draftDt2, double draftAmt2, String bank2, String draftNo3,
			Date draftDt3, double draftAmt3, String bank3, long qtyBalance,
			String range, String division, String commission, String vattinNo,
			String cstNo, BigDecimal basicRate, String pan, Date doStartDate,
			Date doEndDate) {
		this.id = id;
		this.taxdetails = taxdetails;
		this.doNo = doNo;
		this.wayBridgeId = wayBridgeId;
		this.purchaserName = purchaserName;
		this.destination = destination;
		this.stateCode = stateCode;
		this.grade = grade;
		this.doDate = doDate;
		this.applNo = applNo;
		this.applDate = applDate;
		this.doQty = doQty;
		this.draftNo1 = draftNo1;
		this.draftAmt1 = draftAmt1;
		this.draftDt2 = draftDt2;
		this.draftAmt2 = draftAmt2;
		this.bank2 = bank2;
		this.draftNo3 = draftNo3;
		this.draftDt3 = draftDt3;
		this.draftAmt3 = draftAmt3;
		this.bank3 = bank3;
		this.qtyBalance = qtyBalance;
		this.range = range;
		this.division = division;
		this.commission = commission;
		this.vattinNo = vattinNo;
		this.cstNo = cstNo;
		this.basicRate = basicRate;
		this.pan = pan;
		this.doStartDate = doStartDate;
		this.doEndDate = doEndDate;
	}

	public Deliveryorderdetails(int id, Taxdetails taxdetails, String doNo,
			int wayBridgeId, String purchaserName, String destination,
			String stateCode, String grade, Date doDate, int applNo,
			Date applDate, long doQty, String draftNo1, Date draftDt1,
			double draftAmt1, String draftNo2, Date draftDt2, double draftAmt2,
			String bank2, String draftNo3, Date draftDt3, double draftAmt3,
			String bank3, long qtyBalance, String range, String division,
			String commission, String vattinNo, String cstNo,
			BigDecimal basicRate, String pan, Date doStartDate, Date doEndDate) {
		this.id = id;
		this.taxdetails = taxdetails;
		this.doNo = doNo;
		this.wayBridgeId = wayBridgeId;
		this.purchaserName = purchaserName;
		this.destination = destination;
		this.stateCode = stateCode;
		this.grade = grade;
		this.doDate = doDate;
		this.applNo = applNo;
		this.applDate = applDate;
		this.doQty = doQty;
		this.draftNo1 = draftNo1;
		this.draftDt1 = draftDt1;
		this.draftAmt1 = draftAmt1;
		this.draftNo2 = draftNo2;
		this.draftDt2 = draftDt2;
		this.draftAmt2 = draftAmt2;
		this.bank2 = bank2;
		this.draftNo3 = draftNo3;
		this.draftDt3 = draftDt3;
		this.draftAmt3 = draftAmt3;
		this.bank3 = bank3;
		this.qtyBalance = qtyBalance;
		this.range = range;
		this.division = division;
		this.commission = commission;
		this.vattinNo = vattinNo;
		this.cstNo = cstNo;
		this.basicRate = basicRate;
		this.pan = pan;
		this.doStartDate = doStartDate;
		this.doEndDate = doEndDate;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Taxdetails getTaxdetails() {
		return this.taxdetails;
	}

	public void setTaxdetails(Taxdetails taxdetails) {
		this.taxdetails = taxdetails;
	}

	public String getDoNo() {
		return this.doNo;
	}

	public void setDoNo(String doNo) {
		this.doNo = doNo;
	}

	public int getWayBridgeId() {
		return this.wayBridgeId;
	}

	public void setWayBridgeId(int wayBridgeId) {
		this.wayBridgeId = wayBridgeId;
	}

	public String getPurchaserName() {
		return this.purchaserName;
	}

	public void setPurchaserName(String purchaserName) {
		this.purchaserName = purchaserName;
	}

	public String getDestination() {
		return this.destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getStateCode() {
		return this.stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Date getDoDate() {
		return this.doDate;
	}

	public void setDoDate(Date doDate) {
		this.doDate = doDate;
	}

	public int getApplNo() {
		return this.applNo;
	}

	public void setApplNo(int applNo) {
		this.applNo = applNo;
	}

	public Date getApplDate() {
		return this.applDate;
	}

	public void setApplDate(Date applDate) {
		this.applDate = applDate;
	}

	public long getDoQty() {
		return this.doQty;
	}

	public void setDoQty(long doQty) {
		this.doQty = doQty;
	}

	public String getDraftNo1() {
		return this.draftNo1;
	}

	public void setDraftNo1(String draftNo1) {
		this.draftNo1 = draftNo1;
	}

	public Date getDraftDt1() {
		return this.draftDt1;
	}

	public void setDraftDt1(Date draftDt1) {
		this.draftDt1 = draftDt1;
	}

	public double getDraftAmt1() {
		return this.draftAmt1;
	}

	public void setDraftAmt1(double draftAmt1) {
		this.draftAmt1 = draftAmt1;
	}

	public String getDraftNo2() {
		return this.draftNo2;
	}

	public void setDraftNo2(String draftNo2) {
		this.draftNo2 = draftNo2;
	}

	public Date getDraftDt2() {
		return this.draftDt2;
	}

	public void setDraftDt2(Date draftDt2) {
		this.draftDt2 = draftDt2;
	}

	public double getDraftAmt2() {
		return this.draftAmt2;
	}

	public void setDraftAmt2(double draftAmt2) {
		this.draftAmt2 = draftAmt2;
	}

	public String getBank2() {
		return this.bank2;
	}

	public void setBank2(String bank2) {
		this.bank2 = bank2;
	}

	public String getDraftNo3() {
		return this.draftNo3;
	}

	public void setDraftNo3(String draftNo3) {
		this.draftNo3 = draftNo3;
	}

	public Date getDraftDt3() {
		return this.draftDt3;
	}

	public void setDraftDt3(Date draftDt3) {
		this.draftDt3 = draftDt3;
	}

	public double getDraftAmt3() {
		return this.draftAmt3;
	}

	public void setDraftAmt3(double draftAmt3) {
		this.draftAmt3 = draftAmt3;
	}

	public String getBank3() {
		return this.bank3;
	}

	public void setBank3(String bank3) {
		this.bank3 = bank3;
	}

	public long getQtyBalance() {
		return this.qtyBalance;
	}

	public void setQtyBalance(long qtyBalance) {
		this.qtyBalance = qtyBalance;
	}

	public String getRange() {
		return this.range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getDivision() {
		return this.division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getCommission() {
		return this.commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}

	public String getVattinNo() {
		return this.vattinNo;
	}

	public void setVattinNo(String vattinNo) {
		this.vattinNo = vattinNo;
	}

	public String getCstNo() {
		return this.cstNo;
	}

	public void setCstNo(String cstNo) {
		this.cstNo = cstNo;
	}

	public BigDecimal getBasicRate() {
		return this.basicRate;
	}

	public void setBasicRate(BigDecimal basicRate) {
		this.basicRate = basicRate;
	}

	public String getPan() {
		return this.pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public Date getDoStartDate() {
		return this.doStartDate;
	}

	public void setDoStartDate(Date doStartDate) {
		this.doStartDate = doStartDate;
	}

	public Date getDoEndDate() {
		return this.doEndDate;
	}

	public void setDoEndDate(Date doEndDate) {
		this.doEndDate = doEndDate;
	}

}
