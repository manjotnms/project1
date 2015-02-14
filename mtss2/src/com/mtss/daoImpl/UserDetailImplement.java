package com.mtss.daoImpl;

import com.mtss.bean.User;
import com.mtss.dao.UserDetail;
import com.mtss.security.Encryption;

import java.sql.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class UserDetailImplement implements UserDetail {
	
	static Log log = LogFactory.getLog(UserDetailImplement.class.getName());
	private String FIND_USER = "select * from loginUser where userName=? AND userType = ? AND password=?";
	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void addUser(User user) {
		// TODO Auto-generated method stub

	}

	//@Override
	/*public boolean findUser(User user) {
		// TODO Auto-generated method stub
		boolean validUser = false;
		try{
			Connection conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(FIND_USER);
			pstmt.setString(1, user.getUserID());
		    pstmt.setString(2, user.getUserType());
			pstmt.setString(3, user.getPassword());
			ResultSet rset = pstmt.executeQuery();
			if(rset!=null && rset.next())
				validUser = true;
		}
		catch(Exception e){
			System.out.println("Exception In UserDetailImplement method findUser()"+e);
		}
		System.out.println("Is Valid User: "+validUser);
		return validUser;
	}*/

	@Override
	public void removeUser(String userID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		
	}

	//Method With JDBCTemplate
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
	
			if(foundUser!=null)
				return true;
			else
				return false;
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

}
