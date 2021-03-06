package mtss.beans.BO;
// default package
// Generated 14 Feb, 2015 11:57:52 AM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Taxdetails generated by hbm2java
 */
public class Taxdetails implements java.io.Serializable {

	private int id;
	private BigDecimal taxPercent;
	private BigDecimal royalty;
	private BigDecimal sed;
	private BigDecimal cleanEngyCess;
	private BigDecimal weighMeBt;
	private BigDecimal slc;
	private BigDecimal wrc;
	private BigDecimal bazarFee;
	private BigDecimal centExcRate;
	private BigDecimal eduCessRate;
	private BigDecimal highEduRate;
	private BigDecimal roadCess;
	private BigDecimal ambhCess;
	private BigDecimal otherCharges;
	private String doNo;
	private Set deliveryorderdetailses = new HashSet(0);

	public Taxdetails() {
	}

	public Taxdetails(int id, String doNo) {
		this.id = id;
		this.doNo = doNo;
	}

	public Taxdetails(int id, BigDecimal taxPercent, BigDecimal royalty,
			BigDecimal sed, BigDecimal cleanEngyCess, BigDecimal weighMeBt,
			BigDecimal slc, BigDecimal wrc, BigDecimal bazarFee,
			BigDecimal centExcRate, BigDecimal eduCessRate,
			BigDecimal highEduRate, BigDecimal roadCess, BigDecimal ambhCess,
			BigDecimal otherCharges, String doNo, Set deliveryorderdetailses) {
		this.id = id;
		this.taxPercent = taxPercent;
		this.royalty = royalty;
		this.sed = sed;
		this.cleanEngyCess = cleanEngyCess;
		this.weighMeBt = weighMeBt;
		this.slc = slc;
		this.wrc = wrc;
		this.bazarFee = bazarFee;
		this.centExcRate = centExcRate;
		this.eduCessRate = eduCessRate;
		this.highEduRate = highEduRate;
		this.roadCess = roadCess;
		this.ambhCess = ambhCess;
		this.otherCharges = otherCharges;
		this.doNo = doNo;
		this.deliveryorderdetailses = deliveryorderdetailses;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getTaxPercent() {
		return this.taxPercent;
	}

	public void setTaxPercent(BigDecimal taxPercent) {
		this.taxPercent = taxPercent;
	}

	public BigDecimal getRoyalty() {
		return this.royalty;
	}

	public void setRoyalty(BigDecimal royalty) {
		this.royalty = royalty;
	}

	public BigDecimal getSed() {
		return this.sed;
	}

	public void setSed(BigDecimal sed) {
		this.sed = sed;
	}

	public BigDecimal getCleanEngyCess() {
		return this.cleanEngyCess;
	}

	public void setCleanEngyCess(BigDecimal cleanEngyCess) {
		this.cleanEngyCess = cleanEngyCess;
	}

	public BigDecimal getWeighMeBt() {
		return this.weighMeBt;
	}

	public void setWeighMeBt(BigDecimal weighMeBt) {
		this.weighMeBt = weighMeBt;
	}

	public BigDecimal getSlc() {
		return this.slc;
	}

	public void setSlc(BigDecimal slc) {
		this.slc = slc;
	}

	public BigDecimal getWrc() {
		return this.wrc;
	}

	public void setWrc(BigDecimal wrc) {
		this.wrc = wrc;
	}

	public BigDecimal getBazarFee() {
		return this.bazarFee;
	}

	public void setBazarFee(BigDecimal bazarFee) {
		this.bazarFee = bazarFee;
	}

	public BigDecimal getCentExcRate() {
		return this.centExcRate;
	}

	public void setCentExcRate(BigDecimal centExcRate) {
		this.centExcRate = centExcRate;
	}

	public BigDecimal getEduCessRate() {
		return this.eduCessRate;
	}

	public void setEduCessRate(BigDecimal eduCessRate) {
		this.eduCessRate = eduCessRate;
	}

	public BigDecimal getHighEduRate() {
		return this.highEduRate;
	}

	public void setHighEduRate(BigDecimal highEduRate) {
		this.highEduRate = highEduRate;
	}

	public BigDecimal getRoadCess() {
		return this.roadCess;
	}

	public void setRoadCess(BigDecimal roadCess) {
		this.roadCess = roadCess;
	}

	public BigDecimal getAmbhCess() {
		return this.ambhCess;
	}

	public void setAmbhCess(BigDecimal ambhCess) {
		this.ambhCess = ambhCess;
	}

	public BigDecimal getOtherCharges() {
		return this.otherCharges;
	}

	public void setOtherCharges(BigDecimal otherCharges) {
		this.otherCharges = otherCharges;
	}

	public String getDoNo() {
		return this.doNo;
	}

	public void setDoNo(String doNo) {
		this.doNo = doNo;
	}

	public Set getDeliveryorderdetailses() {
		return this.deliveryorderdetailses;
	}

	public void setDeliveryorderdetailses(Set deliveryorderdetailses) {
		this.deliveryorderdetailses = deliveryorderdetailses;
	}

}
