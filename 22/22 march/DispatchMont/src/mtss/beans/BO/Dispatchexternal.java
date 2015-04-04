package mtss.beans.BO;
// default package
// Generated 14 Feb, 2015 11:57:52 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * Dispatchexternal generated by hbm2java
 */
public class Dispatchexternal implements java.io.Serializable {

	private Integer activityNo;
	private Gpsinventory gpsinventory;
	private Epcinventory epcinventory;
	private String doNo;
	private String vechNo;
	private Date issueTime;
	private long rlw;
	private Date firstWeightTime;
	private Long firstWeight;
	private Date secondWeightTime;
	private Long secondWeight;
	private Long netWeight;
	private byte[] firstImageFront;
	private byte[] firstImageMiddle;
	private byte[] firstImageRear;
	private byte[] secondImageFront;
	private byte[] secondImageMiddle;
	private byte[] secondImageRear;
	private Date returnTime;
	private Invoicedetail invoicedetail;

	public Dispatchexternal() {
	}

	public Dispatchexternal(Gpsinventory gpsinventory,
			Epcinventory epcinventory, String doNo, String vechNo, long rlw) {
		this.gpsinventory = gpsinventory;
		this.epcinventory = epcinventory;
		this.doNo = doNo;
		this.vechNo = vechNo;
		this.rlw = rlw;
	}

	public Dispatchexternal(Gpsinventory gpsinventory,
			Epcinventory epcinventory, String doNo, String vechNo,
			Date issueTime, long rlw, Date firstWeightTime, Long firstWeight,
			Date secondWeightTime, Long secondWeight, Long netWeight,
			byte[] firstImageFront, byte[] firstImageMiddle,
			byte[] firstImageRear, byte[] secondImageFront,
			byte[] secondImageMiddle, byte[] secondImageRear, Date returnTime,
			Invoicedetail invoicedetail) {
		this.gpsinventory = gpsinventory;
		this.epcinventory = epcinventory;
		this.doNo = doNo;
		this.vechNo = vechNo;
		this.issueTime = issueTime;
		this.rlw = rlw;
		this.firstWeightTime = firstWeightTime;
		this.firstWeight = firstWeight;
		this.secondWeightTime = secondWeightTime;
		this.secondWeight = secondWeight;
		this.netWeight = netWeight;
		this.firstImageFront = firstImageFront;
		this.firstImageMiddle = firstImageMiddle;
		this.firstImageRear = firstImageRear;
		this.secondImageFront = secondImageFront;
		this.secondImageMiddle = secondImageMiddle;
		this.secondImageRear = secondImageRear;
		this.returnTime = returnTime;
		this.invoicedetail = invoicedetail;
	}

	public Integer getActivityNo() {
		return this.activityNo;
	}

	public void setActivityNo(Integer activityNo) {
		this.activityNo = activityNo;
	}

	public Gpsinventory getGpsinventory() {
		return this.gpsinventory;
	}

	public void setGpsinventory(Gpsinventory gpsinventory) {
		this.gpsinventory = gpsinventory;
	}

	public Epcinventory getEpcinventory() {
		return this.epcinventory;
	}

	public void setEpcinventory(Epcinventory epcinventory) {
		this.epcinventory = epcinventory;
	}

	public String getDoNo() {
		return this.doNo;
	}

	public void setDoNo(String doNo) {
		this.doNo = doNo;
	}

	public String getVechNo() {
		return this.vechNo;
	}

	public void setVechNo(String vechNo) {
		this.vechNo = vechNo;
	}

	public Date getIssueTime() {
		return this.issueTime;
	}

	public void setIssueTime(Date issueTime) {
		this.issueTime = issueTime;
	}

	public long getRlw() {
		return this.rlw;
	}

	public void setRlw(long rlw) {
		this.rlw = rlw;
	}

	public Date getFirstWeightTime() {
		return this.firstWeightTime;
	}

	public void setFirstWeightTime(Date firstWeightTime) {
		this.firstWeightTime = firstWeightTime;
	}

	public Long getFirstWeight() {
		return this.firstWeight;
	}

	public void setFirstWeight(Long firstWeight) {
		this.firstWeight = firstWeight;
	}

	public Date getSecondWeightTime() {
		return this.secondWeightTime;
	}

	public void setSecondWeightTime(Date secondWeightTime) {
		this.secondWeightTime = secondWeightTime;
	}

	public Long getSecondWeight() {
		return this.secondWeight;
	}

	public void setSecondWeight(Long secondWeight) {
		this.secondWeight = secondWeight;
	}

	public Long getNetWeight() {
		return this.netWeight;
	}

	public void setNetWeight(Long netWeight) {
		this.netWeight = netWeight;
	}

	public byte[] getFirstImageFront() {
		return this.firstImageFront;
	}

	public void setFirstImageFront(byte[] firstImageFront) {
		this.firstImageFront = firstImageFront;
	}

	public byte[] getFirstImageMiddle() {
		return this.firstImageMiddle;
	}

	public void setFirstImageMiddle(byte[] firstImageMiddle) {
		this.firstImageMiddle = firstImageMiddle;
	}

	public byte[] getFirstImageRear() {
		return this.firstImageRear;
	}

	public void setFirstImageRear(byte[] firstImageRear) {
		this.firstImageRear = firstImageRear;
	}

	public byte[] getSecondImageFront() {
		return this.secondImageFront;
	}

	public void setSecondImageFront(byte[] secondImageFront) {
		this.secondImageFront = secondImageFront;
	}

	public byte[] getSecondImageMiddle() {
		return this.secondImageMiddle;
	}

	public void setSecondImageMiddle(byte[] secondImageMiddle) {
		this.secondImageMiddle = secondImageMiddle;
	}

	public byte[] getSecondImageRear() {
		return this.secondImageRear;
	}

	public void setSecondImageRear(byte[] secondImageRear) {
		this.secondImageRear = secondImageRear;
	}

	public Date getReturnTime() {
		return this.returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public Invoicedetail getInvoicedetail() {
		return this.invoicedetail;
	}

	public void setInvoicedetail(Invoicedetail invoicedetail) {
		this.invoicedetail = invoicedetail;
	}

}