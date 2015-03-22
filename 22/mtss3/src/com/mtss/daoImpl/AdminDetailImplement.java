package com.mtss.daoImpl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.mtss.bean.EPC;
import com.mtss.bean.GPS;
import com.mtss.bean.ProductionVehicle;
import com.mtss.bean.User;
import com.mtss.dao.AdminDetail;

public class AdminDetailImplement implements AdminDetail {

	static Log log = LogFactory.getLog(AdminDetailImplement.class.getName());
	
	private JdbcTemplate jdbcTemplate;
	private String USER_LIST = "select * from loginuser";
	private String ADD_NEW_USER= "insert into loginuser(username,password,userType,name) values(?,?,?,?)";
	private String REMOVE_USER = "DELETE FROM loginuser WHERE userName=?";
	private String CHANGE_PASSWORD = "update loginuser set password=? where username=?" ;
	private String GET_PASSWORD = "select * from loginuser where username=?";
	private String USER_AVAIL="select * from loginuser where username=?";
	
	private String EPC_Detail = "insert into epcinventory(EpcNo,DateOfIssue,Avail,InUse,EpcType) values(?,?,?,?,?)";
	private String EPC_NO_AVAIL = "select EpcNo from epcinventory where EpcNo=?";
	private String EPC_DETAIL_LIST = "select * from epcinventory";
	private String DELETE_EPC = "delete from epcinventory where EpcNo=?";
	private String EPC_TYPE = "select EpcType where EpcNo=?";
	
	private String GPS_Detail = "insert into gpsinventory(IMEI,Avail,InUse,DateOfIssue) values(?,?,?,?)";
	private String IMEI_AVAIL= "select * from gpsinventory where IMEI=?";
	private String GPS_DETAIL_LIST = "select * from gpsinventory";
	private String DELETE_GPS = "delete from gpsinventory where IMEI=?";
	
	private String PERMANENT_EPC_GPS_NO = "insert into productionvechdetail(EPCNo,IMEINo,vechNo) values(?,?,?)";
	private String VEHICLE_Detail = "select EpcNo,IMEI from dispatchexternal where vechNo=?";
	private String LOST_EPC = "update epcinventory Set Avail=? where EpcNo=?";
	private String LOST_GPS = "update gpsinventory Set Avail=? where IMEI=?";
	
	private String IS_AVAIL_VEHICLE = "select * from dispatchexternal where vechNo=?";
	private String IS_PRO_AVAIL_VEHICLE = "select * from productionvechdetail where vechNo=?";
	
	private String BLOCK_TAG = "delete from internalactivity where FirstWeightTime='null' AND SecondWeight='null' AND EpcNo=?";
	
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
	public User getPassword(User user) {
		log.info("*****AdminDetailImplement(getPassword) :Get Existing User's Password From Table***** "+user.getUserName());
		User userObj = null;
		RowMapper rm = new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				// TODO Auto-generated method stub
				User user = new User();
				user.setName(rs.getString("name"));
				user.setUserName(rs.getString("userName"));
				user.setPassword(rs.getString("password"));
				user.setUserType(rs.getString("userType"));
				return user;
			}
		};
		userObj = (User)jdbcTemplate.queryForObject(GET_PASSWORD,new Object[]{user.getUserName()}, rm);
		log.info("*****AdminDetailImplement(getPassword) :User Info***** User_Name "+userObj.getUserName()+"\t Name "+userObj.getName()+"\t Password "+userObj.getPassword()+"\t User_Type "+userObj.getUserType());
		return userObj;
	}

	@Override
	public boolean changePasword(User user) {
		log.info("******AdminDetailImplement (changePassword): Change Existing User's Password****** "+user.getUserName());
		Object changePassword[] ={user.getPassword(),user.getUserName()};
		int count = jdbcTemplate.update(CHANGE_PASSWORD, changePassword);
		if(count!=0)
			return true;
		else
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
	
	@Override
	public boolean checkUserAvail(String userName) {
		log.info("*****AdminDetailImplement(getPassword) :Get Existing User's Password From Table***** "+userName);
		RowMapper rm = new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				// TODO Auto-generated method stub
				User user = new User();
				user.setName(rs.getString("name"));
				return user;
			}
		};
		List<User> userList = jdbcTemplate.query(USER_AVAIL,new Object[]{userName}, rm);
		log.info("*****AdminDetailImplement(ChekUserAvail) :User Info***** "+userList );
		if ( userList.isEmpty())
			return false;
		else
			return true;
	}
	
	@Override
	public boolean addNewTag(EPC epc) {
		log.info("*****AdminDetailImplement(addNewTag) :Add New Tag In Table***** "+epc.getEpcNo());
		Object epcNo[] = {epc.getEpcNo(),getDate(),1,0,0};
		int count = jdbcTemplate.update(EPC_Detail, epcNo);
		if(count!=0)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean deleteEPC(int epcNo) {
		// TODO Auto-generated method stub
		log.info("*****AdminDetailImplement(deleteEPC) :Remove EPC Detail From Table***** "+epcNo);
		Object removeEPC[]={epcNo};
		int count = jdbcTemplate.update(DELETE_EPC,removeEPC);
		log.info("*****AdminDetailImplement(deteteEPC) :Remove EPC Detail From Table and Effected Rows***** "+count);
		if(count==1)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean checkEpcNoAvail(String epcNo) {
		// TODO Auto-generated method stub
		log.info("*****AdminDetailImplement(checkEpcNoAvail) :Get EPC Number Available In Table***** "+epcNo);
		RowMapper rm = new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				// TODO Auto-generated method stub
				EPC epc = new EPC();
				epc.setEpcNo(rs.getInt("epcNo"));
				return epc;
			}
		};
		List<EPC> epcList = jdbcTemplate.query(EPC_NO_AVAIL,new Object[]{epcNo}, rm);
		log.info("*****AdminDetailImplement(ChekEPCNoAvail) :EPN_No Info***** "+epcList);
		if ( epcList.isEmpty())
			return false;
		else
			return true;
	}

	@Override
	public boolean lostEPC(String vehicleNo) {
		log.info("*****AdminDetailImplement(lostEPC) :Block Tag for Vehicle Number***** "+vehicleNo);
		RowMapper rm = new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				log.info("*****AdminDetailImplement(lostEPC) :EPC_No is:***** "+rs.getInt("EpcNo"));
				ProductionVehicle proVeh = new ProductionVehicle();
				proVeh.setEPCNo(rs.getInt("EpcNo"));
				return proVeh;
			}
		};
		
		ProductionVehicle prodVeh = (ProductionVehicle)jdbcTemplate.queryForObject(VEHICLE_Detail,new Object[]{vehicleNo},rm);
		log.info("*****AdminDetailImplement(lostEPC) :Lost EPC for where EPCNo is***** "+prodVeh.getEPCNo());
		int count = jdbcTemplate.update(LOST_EPC,new Object[]{0,prodVeh.getEPCNo()});
		log.info("*****AdminDetailImplement(lostEPC) : Effected Rows are***** "+count);
		if(count==1)
			return true;
		else
			return false;
	}

	@Override
	public boolean blockTag(EPC epc) {
		log.info("*****AdminDetailImplement(blockTag) :Block Tag In Table***** "+epc.getEpcNo());
		String updateableTable = "";
		RowMapper rm = new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				// TODO Auto-generated method stub
				EPC epc= new EPC();
				epc.setEpcType(rs.getInt("EpcType"));
				return epc;
			}
		};
		
		
		EPC epcDetail = (EPC)jdbcTemplate.queryForObject(EPC_TYPE,new Object[]{epc.getEpcNo()},rm);
		
		switch(epcDetail.getEpcType()){
			case 1:
				updateableTable = "";
				break;
			case 2:
				updateableTable = "";
				break;
			case 3:
				updateableTable = "";
				break;
			case 4:
				updateableTable = "";
				break;
			case 5:
				updateableTable = "";
				break;
			default:
				updateableTable = null;
				break;
		}
		
		/*Object epcNo[] = {epc.getEpcNo(),getDate(),1,0,0};
		int count = jdbcTemplate.update(EPC_Detail, epcNo);
		if(count!=0)
			return true;
		else
			return false;*/
		
		boolean block_result = false;
		
		if(epcDetail.getEpcType()==2){
			Object epcNo[] = {epc.getEpcNo()};
			int count = jdbcTemplate.update(BLOCK_TAG, epcNo);
			
			if(count!=0)
				block_result =  true;
			else
				block_result = false;
		}
		
		return block_result;
	}
	
	@Override
	public List<EPC> epcDetail() {
		log.info("*****AdminDetailImplement(epcDetail) :Get EPC List From Table***** ");
		RowMapper rm = new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				// TODO Auto-generated method stub
				EPC epc = new EPC();
				epc.setEpcNo(rs.getInt("EpcNo"));
				epc.setDateOfIssue(rs.getString("DateOfIssue"));
				epc.setAvail(rs.getInt("Avail"));
				epc.setInUse(rs.getInt("InUse"));
				epc.setActivityNo(rs.getInt("ActivityNo"));
				epc.setEpcType(rs.getInt("EpcType"));
				return epc;
			}
		};
		return jdbcTemplate.query(EPC_DETAIL_LIST, rm);
	}
	
	@Override
	public boolean unblockTag(String tagNo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addGPS(GPS gps) {
		// TODO Auto-generated method stub
		log.info("*****AdminDetailImplement(addGPS) :Add New GPS Detail In Table***** "+gps.getIMEI());
		Object gpsNo[] = {gps.getIMEI(),1,0,getDate()};
		int count = jdbcTemplate.update(GPS_Detail, gpsNo);
		if(count!=0)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean deleteGPS(int imeiNo) {
		// TODO Auto-generated method stub
		log.info("*****AdminDetailImplement(deleteGPS) :Remove GPS Detail From Table***** "+imeiNo);
		Object removeIMEI[]={imeiNo};
		int count = jdbcTemplate.update(DELETE_GPS,removeIMEI);
		log.info("*****AdminDetailImplement(deteteGPS) :Remove GPS Detail From Table and Effected Rows***** "+count);
		if(count==1)
			return true;
		else
			return false;
	}
	
	@Override
	public boolean checkGPSAvail(String IMEINo) {
		// TODO Auto-generated method stub
		log.info("*****AdminDetailImplement(checkGPSAvail) :Get GPS Number Available In Table***** "+IMEINo);
		RowMapper rm = new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				// TODO Auto-generated method stub
				GPS gps = new GPS();
				gps.setIMEI(rs.getInt("IMEI"));
				return gps;
			}
		};
		List<GPS> gpsList = jdbcTemplate.query(IMEI_AVAIL,new Object[]{IMEINo}, rm);
		log.info("*****AdminDetailImplement(ChekEPCNoAvail) :EPN_No Info***** "+gpsList);
		if ( gpsList.isEmpty())
			return false;
		else
			return true;
	}
	
	@Override
	public List<GPS> gpsDetail() {
		// TODO Auto-generated method stub
		log.info("*****AdminDetailImplement(epcDetail) :Get EPC List From Table***** ");
		RowMapper rm = new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				// TODO Auto-generated method stub
				GPS gps = new GPS();
				gps.setIMEI(rs.getInt("IMEI"));
				gps.setAvail(rs.getInt("Avail"));
				gps.setInUse(rs.getInt("InUse"));
				gps.setDateOfIssue(rs.getString("DateOfIssue"));;
				return gps;
			}
		};
		return jdbcTemplate.query(GPS_DETAIL_LIST, rm);
	}
	
	@Override
	public boolean lostGPS(String vehicleNo) {
		// TODO Auto-generated method stub
		log.info("*****AdminDetailImplement(lostGPS) :Block GPS for Vehicle Number***** "+vehicleNo);
		RowMapper rm = new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				log.info("*****AdminDetailImplement(lostGPS) :GPS_No and IMEI_no is:***** "+rs.getInt("IMEI"));
				ProductionVehicle proVeh = new ProductionVehicle();
				proVeh.setGPSNo(rs.getInt("IMEI"));
				return proVeh;
			}
		};
		
		ProductionVehicle prodVeh = (ProductionVehicle)jdbcTemplate.queryForObject(VEHICLE_Detail,new Object[]{vehicleNo},rm);
		log.info("*****AdminDetailImplement(lostEPC) :Lost GPS for where IMEI is***** "+prodVeh.getGPSNo());
		int count = jdbcTemplate.update(LOST_GPS,new Object[]{0,prodVeh.getGPSNo()});
		log.info("*****AdminDetailImplement(lostGPS) : Effected Rows are***** "+count);
		if(count==1)
			return true;
		else
			return false;
	}

	@Override
	public boolean addPernaEPCGPS(ProductionVehicle perVeh) {
		
		log.info("*****AdminDetailImplement(addPernaEPCGPS) :Add Permanent EPC And GPS No to Vehicle***** "+perVeh.getVehicleNo());
		Object perVehDetail[] = {perVeh.getEPCNo(),perVeh.getGPSNo(),perVeh.getVehicleNo()};
		int vehCount  = jdbcTemplate.update(PERMANENT_EPC_GPS_NO,perVehDetail);
				
		log.info("*****AdminDetailImplement(addPernaEPCGPS) :Add New Tag In Table***** "+perVeh.getEPCNo());
		Object epcNo[] = {perVeh.getEPCNo(),getDate(),1,1,4};
		int gpsCount = jdbcTemplate.update(EPC_Detail, epcNo);
		
		log.info("*****AdminDetailImplement(addPernaEPCGPS) :Add New GPS Detail In Table***** "+perVeh.getGPSNo());
		Object gpsNo[] = {perVeh.getGPSNo(),1,1,getDate()};
		int epcCount = jdbcTemplate.update(GPS_Detail, gpsNo);
		
		if(vehCount==1 && gpsCount==1 && epcCount==1)
			return true; 
		else
			return false;
	}
	
	@Override
	public boolean checkVehAvail(String vehNo) {
		// TODO Auto-generated method stub
		log.info("*****AdminDetailImplement(checkVehAvail) :Check Vehicle is Available With This Number***** "+vehNo);
		RowMapper rm = new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				log.info("*****AdminDetailImplement(checkVehAvail) :EPC_No is of Vehicle:***** "+rs.getString("EpcNo"));
				ProductionVehicle proVeh = new ProductionVehicle();
				proVeh.setEPCNo(rs.getInt("EpcNo"));
				return proVeh;
			}
		};
		
		List<ProductionVehicle> proVehList = jdbcTemplate.query(IS_AVAIL_VEHICLE,new Object[]{vehNo}, rm);
		log.info("*****AdminDetailImplement(ChekEPCNoAvail) :Vehicle Info***** "+proVehList);
		if (proVehList.isEmpty())
			return false;
		else
			return true;
	}
	
	@Override
	public boolean proCheckVehAvail(String vehNo) {
		log.info("*****AdminDetailImplement(proCheckVehAvail) :Check Production Vehicle is Available With This Number***** "+vehNo);
		RowMapper rm = new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				log.info("*****AdminDetailImplement(proCheckVehAvail) :EPC_No is of Vehicle:***** "+rs.getString("EPCNo"));
				ProductionVehicle proVeh = new ProductionVehicle();
				proVeh.setEPCNo(rs.getInt("EpcNo"));
				return proVeh;
			}
		};
		
		List<ProductionVehicle> proVehList = jdbcTemplate.query(IS_PRO_AVAIL_VEHICLE,new Object[]{vehNo}, rm);
		log.info("*****AdminDetailImplement(proCheckVehAvail) :Vehicle Info***** "+proVehList);
		if (proVehList.isEmpty())
			return false;
		else
			return true;
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
