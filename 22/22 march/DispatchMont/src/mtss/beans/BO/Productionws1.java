package mtss.beans.BO;
// default package
// Generated 14 Feb, 2015 11:57:52 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * Productionws1 generated by hbm2java
 */
public class Productionws1 implements java.io.Serializable {

	private Long transactionId;
	private String vechNo;
	private Long netWeight;
	private Date date;

	public Productionws1() {
	}

	public Productionws1(String vechNo, Long netWeight, Date date) {
		this.vechNo = vechNo;
		this.netWeight = netWeight;
		this.date = date;
	}

	public Long getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getVechNo() {
		return this.vechNo;
	}

	public void setVechNo(String vechNo) {
		this.vechNo = vechNo;
	}

	public Long getNetWeight() {
		return this.netWeight;
	}

	public void setNetWeight(Long netWeight) {
		this.netWeight = netWeight;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
