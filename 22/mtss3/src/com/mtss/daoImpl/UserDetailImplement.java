package com.mtss.daoImpl;

import com.mtss.bean.User;
import com.mtss.dao.UserDetail;
//import com.mtss.security.Encryption;

import java.sql.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class UserDetailImplement implements UserDetail {
	
	static Log log = LogFactory.getLog(UserDetailImplement.class.getName());
	private JdbcTemplate jdbcTemplate;
	
	private String FIND_USER = "select * from loginuser where username=? AND userType = ? AND password=?";
	private String USER_ACTIVITY_LOGIN = "insert into useractivity(username,LoginDate) values(?,?)";
	private String USER_ACTIVITY_LOGOUT = "insert into useractivity(username,LogoutDate) values(?,?)";
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public boolean findUser(User user) {
		// TODO Auto-generated method stub
		//String encrypPass = Encryption.getEncryptedPass(user.getUserType());
		log.info("*****UserDetailImplement(findUser) :Find User From Table***** "+user.getUserName());
		try{
			RowMapper rowMapper = new RowMapper(){
				@Override
				public User mapRow(ResultSet rs, int index) throws SQLException {
					// TODO Auto-generated method stub
					User user = new User();
					//user.setUserID(rs.getString("userID"));
					user.setUserName(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setUserType(rs.getString("userType"));
					return user;
				}
			};
			User foundUser = (User)jdbcTemplate.queryForObject(
					FIND_USER, new Object[] {user.getUserName(),user.getUserType(),user.getPassword()}, rowMapper);
	
			if(foundUser!=null){
				storeUserActivity(user.getUserName(),USER_ACTIVITY_LOGIN);
				return true;
			}
			else{
				return false;
			}
		}
		catch(EmptyResultDataAccessException e){
			log.error("*****UserDetailImplement(findUser) :***** Error "+e);
			return false;
		}
		catch(Exception e){
			log.error("*****UserDetailImplement(findUser) :***** Error "+e);
			return false;
		}
	}

	@Override
	public void logoutUser(User user) {
		log.info("*****UserDetailImplement(logoutUser) :Store User Activity ***** "+user.getUserName());
		storeUserActivity(user.getUserName(),USER_ACTIVITY_LOGOUT);
	}
	
	private void storeUserActivity(String uname,String storeActivity){
		log.info("*****UserDetailImplement(storeUserActivity) :Store User Activity ***** "+uname);
		Object userAct[] = {uname,getDate()};
		jdbcTemplate.update(storeActivity,userAct);
	}
	
	private Timestamp getDate(){
		java.util.Calendar cal = java.util.Calendar.getInstance();
		java.util.Date utilDate = cal.getTime();
		java.sql.Timestamp sqlDate = null;
		try {
			log.info("*****AdminDetailImplement(getDate) :Current System Date***** "+utilDate);
			sqlDate = new java.sql.Timestamp(utilDate.getTime());
			
		} catch (Exception e) {
			log.error("*****AdminDetailImplement(getDate) :Current System Date Error***** "+e);
		}
		log.info("*****AdminDetailImplement(getDate) :SQL Date***** "+sqlDate);
		return sqlDate;
	}
}
