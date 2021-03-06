package mtss.beans.BO;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Epcinventory implements java.io.Serializable {

	private int epcNo;
	private Date dateofIssue;
	private Boolean avail;
	private Boolean inUse;
	private Integer activityNo;
	private Integer epcType;
	private Set internalcoaltranses = new HashSet(0);
	private Set dispatchexternals = new HashSet(0);
	private String vechNo;
	private Integer tareWeight;
	private String IMEINo;
	
	public Epcinventory() {
	}

	public Epcinventory(int epcNo) {
		this.epcNo = epcNo;
	}

	public Epcinventory(int epcNo, Date dateofIssue, Boolean avail,
			Boolean inUse, Integer activityNo, Integer epcType,
			Set internalcoaltranses, Set dispatchexternals) {
		this.epcNo = epcNo;
		this.dateofIssue = dateofIssue;
		this.avail = avail;
		this.inUse = inUse;
		this.activityNo = activityNo;
		this.epcType = epcType;
		this.internalcoaltranses = internalcoaltranses;
		this.dispatchexternals = dispatchexternals;
	}

	public int getEpcNo() {
		return this.epcNo;
	}

	public void setEpcNo(int epcNo) {
		this.epcNo = epcNo;
	}

	public Date getDateofIssue() {
		return this.dateofIssue;
	}

	public void setDateofIssue(Date dateofIssue) {
		this.dateofIssue = dateofIssue;
	}

	public Boolean getAvail() {
		return this.avail;
	}

	public void setAvail(Boolean avail) {
		this.avail = avail;
	}

	public Boolean getInUse() {
		return this.inUse;
	}

	public void setInUse(Boolean inUse) {
		this.inUse = inUse;
	}

	public Integer getActivityNo() {
		return this.activityNo;
	}

	public void setActivityNo(Integer activityNo) {
		this.activityNo = activityNo;
	}

	public Integer getEpcType() {
		return this.epcType;
	}

	public void setEpcType(Integer epcType) {
		this.epcType = epcType;
	}

	public Set getInternalcoaltranses() {
		return this.internalcoaltranses;
	}

	public void setInternalcoaltranses(Set internalcoaltranses) {
		this.internalcoaltranses = internalcoaltranses;
	}

	public Set getDispatchexternals() {
		return this.dispatchexternals;
	}

	public void setDispatchexternals(Set dispatchexternals) {
		this.dispatchexternals = dispatchexternals;
	}

	public String getVechNo() {
		return vechNo;
	}

	public void setVechNo(String vechNo) {
		this.vechNo = vechNo;
	}

	public Integer getTareWeight() {
		return tareWeight;
	}

	public void setTareWeight(Integer tareWeight) {
		this.tareWeight = tareWeight;
	}

	public String getIMEINo() {
		return IMEINo;
	}

	public void setIMEINo(String iMEINo) {
		IMEINo = iMEINo;
	}
	
}
