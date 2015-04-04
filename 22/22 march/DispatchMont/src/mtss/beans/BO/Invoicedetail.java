package mtss.beans.BO;
// default package
// Generated 14 Feb, 2015 11:57:52 AM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;

/**
 * Invoicedetail generated by hbm2java
 */
public class Invoicedetail implements java.io.Serializable {

	private int activityNo;
	private Dispatchexternal dispatchexternal;
	private String invoiceNo;
	private BigDecimal basicAmount;
	private BigDecimal royalty;
	private BigDecimal exicse;
	private BigDecimal educationCess;
	private BigDecimal secEducationCess;
	private BigDecimal vat1;
	private BigDecimal vat2;
	private BigDecimal stowExicse;
	private BigDecimal sizingCharge;
	private BigDecimal surfaceTransCharge;
	private BigDecimal totalAmount;
	private String challanNo;

	public Invoicedetail() {
	}

	public Invoicedetail(Dispatchexternal dispatchexternal, String invoiceNo,
			String challanNo) {
		this.dispatchexternal = dispatchexternal;
		this.invoiceNo = invoiceNo;
		this.challanNo = challanNo;
	}

	public Invoicedetail(Dispatchexternal dispatchexternal, String invoiceNo,
			BigDecimal basicAmount, BigDecimal royalty, BigDecimal exicse,
			BigDecimal educationCess, BigDecimal secEducationCess,
			BigDecimal vat1, BigDecimal vat2, BigDecimal stowExicse,
			BigDecimal sizingCharge, BigDecimal surfaceTransCharge,
			BigDecimal totalAmount, String challanNo) {
		this.dispatchexternal = dispatchexternal;
		this.invoiceNo = invoiceNo;
		this.basicAmount = basicAmount;
		this.royalty = royalty;
		this.exicse = exicse;
		this.educationCess = educationCess;
		this.secEducationCess = secEducationCess;
		this.vat1 = vat1;
		this.vat2 = vat2;
		this.stowExicse = stowExicse;
		this.sizingCharge = sizingCharge;
		this.surfaceTransCharge = surfaceTransCharge;
		this.totalAmount = totalAmount;
		this.challanNo = challanNo;
	}

	public int getActivityNo() {
		return this.activityNo;
	}

	public void setActivityNo(int activityNo) {
		this.activityNo = activityNo;
	}

	public Dispatchexternal getDispatchexternal() {
		return this.dispatchexternal;
	}

	public void setDispatchexternal(Dispatchexternal dispatchexternal) {
		this.dispatchexternal = dispatchexternal;
	}

	public String getInvoiceNo() {
		return this.invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public BigDecimal getBasicAmount() {
		return this.basicAmount;
	}

	public void setBasicAmount(BigDecimal basicAmount) {
		this.basicAmount = basicAmount;
	}

	public BigDecimal getRoyalty() {
		return this.royalty;
	}

	public void setRoyalty(BigDecimal royalty) {
		this.royalty = royalty;
	}

	public BigDecimal getExicse() {
		return this.exicse;
	}

	public void setExicse(BigDecimal exicse) {
		this.exicse = exicse;
	}

	public BigDecimal getEducationCess() {
		return this.educationCess;
	}

	public void setEducationCess(BigDecimal educationCess) {
		this.educationCess = educationCess;
	}

	public BigDecimal getSecEducationCess() {
		return this.secEducationCess;
	}

	public void setSecEducationCess(BigDecimal secEducationCess) {
		this.secEducationCess = secEducationCess;
	}

	public BigDecimal getVat1() {
		return this.vat1;
	}

	public void setVat1(BigDecimal vat1) {
		this.vat1 = vat1;
	}

	public BigDecimal getVat2() {
		return this.vat2;
	}

	public void setVat2(BigDecimal vat2) {
		this.vat2 = vat2;
	}

	public BigDecimal getStowExicse() {
		return this.stowExicse;
	}

	public void setStowExicse(BigDecimal stowExicse) {
		this.stowExicse = stowExicse;
	}

	public BigDecimal getSizingCharge() {
		return this.sizingCharge;
	}

	public void setSizingCharge(BigDecimal sizingCharge) {
		this.sizingCharge = sizingCharge;
	}

	public BigDecimal getSurfaceTransCharge() {
		return this.surfaceTransCharge;
	}

	public void setSurfaceTransCharge(BigDecimal surfaceTransCharge) {
		this.surfaceTransCharge = surfaceTransCharge;
	}

	public BigDecimal getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getChallanNo() {
		return this.challanNo;
	}

	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}

}