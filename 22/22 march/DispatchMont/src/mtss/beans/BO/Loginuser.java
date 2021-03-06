package mtss.beans.BO;
// default package
// Generated 14 Feb, 2015 11:57:52 AM by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Loginuser generated by hbm2java
 */
public class Loginuser implements java.io.Serializable {

	private Integer id;
	private String username;
	private String password;
	private String userType;
	private Set useractivities = new HashSet(0);

	public Loginuser() {
	}

	public Loginuser(String username, String password, String userType) {
		this.username = username;
		this.password = password;
		this.userType = userType;
	}

	public Loginuser(String username, String password, String userType,
			Set useractivities) {
		this.username = username;
		this.password = password;
		this.userType = userType;
		this.useractivities = useractivities;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public Set getUseractivities() {
		return this.useractivities;
	}

	public void setUseractivities(Set useractivities) {
		this.useractivities = useractivities;
	}

}
