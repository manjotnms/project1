package mtss.beans.BO;
// default package
// Generated 14 Feb, 2015 11:57:52 AM by Hibernate Tools 3.4.0.CR1

/**
 * Taxesdetails generated by hbm2java
 */
public class Taxesdetails implements java.io.Serializable {

	private int id;
	private String doNo;
	private String taxesDetails;

	public Taxesdetails() {
	}

	public Taxesdetails(int id, String doNo, String taxesDetails) {
		this.id = id;
		this.doNo = doNo;
		this.taxesDetails = taxesDetails;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDoNo() {
		return this.doNo;
	}

	public void setDoNo(String doNo) {
		this.doNo = doNo;
	}

	public String getTaxesDetails() {
		return this.taxesDetails;
	}

	public void setTaxesDetails(String taxesDetails) {
		this.taxesDetails = taxesDetails;
	}

}
