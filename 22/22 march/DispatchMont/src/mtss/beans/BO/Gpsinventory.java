package mtss.beans.BO;
// default package
// Generated 14 Feb, 2015 11:57:52 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Gpsinventory generated by hbm2java
 */
public class Gpsinventory implements java.io.Serializable {

	private int imei;
	private Boolean avail;
	private Boolean inUse;
	private Date dateofIssue;
	private Set internalcoaltranses = new HashSet(0);
	private Set dispatchexternals = new HashSet(0);

	public Gpsinventory() {
	}

	public Gpsinventory(int imei) {
		this.imei = imei;
	}

	public Gpsinventory(int imei, Boolean avail, Boolean inUse,
			Date dateofIssue, Set internalcoaltranses, Set dispatchexternals) {
		this.imei = imei;
		this.avail = avail;
		this.inUse = inUse;
		this.dateofIssue = dateofIssue;
		this.internalcoaltranses = internalcoaltranses;
		this.dispatchexternals = dispatchexternals;
	}

	public int getImei() {
		return this.imei;
	}

	public void setImei(int imei) {
		this.imei = imei;
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

	public Date getDateofIssue() {
		return this.dateofIssue;
	}

	public void setDateofIssue(Date dateofIssue) {
		this.dateofIssue = dateofIssue;
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

}
