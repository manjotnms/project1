package com.mtss.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.mtss.bean.User;
import com.mtss.dao.AdminDetail;
import com.mtss.security.Encryption;

public class AdminDetailImplement implements AdminDetail {

	static Log log = LogFactory.getLog(AdminDetailImplement.class.getName());
	
	private JdbcTemplate jdbcTemplate;
	private String USER_LIST = "select * from loginuser";
	private String ADD_NEW_USER= "insert into loginuser(username,password,userType,name) values(?,?,?,?)";
	private String REMOVE_USER = "DELETE FROM loginuser WHERE userName=?";
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean addNewUser(User user) {
		// TODO Auto-generated method stub
		//String encrypPass = Encryption.getEncryptedPass(user.getPassword());
		log.info("******AdminDetailImplement (addNewUser): Add New User****** "+user.getUserName());
		Object addUser[] = {user.getUserName(),user.getPassword(),user.getUserType(),user.getName()};
		int count = jdbcTemplate.update(ADD_NEW_USER, addUser);
		if(count!=0)
			return true;
		else
			return false;
	}

	@Override
	public String getPassword(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean changePasword(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addNewTag(String tagNo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean blockTag(String tagNo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean unblockTag(String tagNo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addGPS() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeUser(String userName) {
		// TODO Auto-generated method stub
		log.info("*****AdminDetailImplement(removeUser) :Remove User From Table***** "+userName);
		Object removeUser[]={userName};
		int count = jdbcTemplate.update(REMOVE_USER,removeUser);
		log.info("*****AdminDetailImplement(removeUser) :Remove User From Table and Effected Rows***** "+count);
		if(count==1)
			return true;
		else
			return false;
	}

	@Override
	public List<User> viewUserList() {
		// TODO Auto-generated method stub
		log.info("*****AdminDetailImplement(viewUserList) :Get Users List From Table***** ");
		RowMapper rm = new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				// TODO Auto-generated method stub
				User user = new User();
				user.setUserName(rs.getString("userName"));
				user.setUserType(rs.getString("userType"));
				return user;
			}
		};
		return jdbcTemplate.query(USER_LIST, rm);
	}
}
