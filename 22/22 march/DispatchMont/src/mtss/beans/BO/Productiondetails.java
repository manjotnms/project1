package mtss.beans.BO;
// default package
// Generated 14 Feb, 2015 11:57:52 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * Productiondetails generated by hbm2java
 */
public class Productiondetails implements java.io.Serializable {

	private Integer productionId;
	private String vechNo;
	private Long totalProd;
	private Date date;

	public Productiondetails() {
	}

	public Productiondetails(String vechNo, Long totalProd, Date date) {
		this.vechNo = vechNo;
		this.totalProd = totalProd;
		this.date = date;
	}

	public Integer getProductionId() {
		return this.productionId;
	}

	public void setProductionId(Integer productionId) {
		this.productionId = productionId;
	}

	public String getVechNo() {
		return this.vechNo;
	}

	public void setVechNo(String vechNo) {
		this.vechNo = vechNo;
	}

	public Long getTotalProd() {
		return this.totalProd;
	}

	public void setTotalProd(Long totalProd) {
		this.totalProd = totalProd;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}