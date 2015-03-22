package com.mtss.daoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.mtss.bean.Activities;
import com.mtss.bean.EPC;
import com.mtss.bean.GPS;
import com.mtss.bean.Sales;
import com.mtss.dao.FrontOfficeDetail;
import com.mtss.services.AdminUpdations;

public class FrontOfficeDetailImplement implements FrontOfficeDetail {
	
	private JdbcTemplate jdbcTemplate;
	
	private Log log = LogFactory.getLog(FrontOfficeDetailImplement.class.getName());
	private final static String IS_EPC_NO_EXIST = "select EpcNo from epcinventory where EpcNo=?";
	private final static String IMEI_LIST = "select IMEI from gpsinventory where Avail=1 AND InUse=0";
	private final static String DO_NO_AVAIL = "select DNo from intercoaltransfer where DNo=?"; 
	private final static String EPC_NO_STATUS= "select EpcNo from epcinventory where Avail=1 AND InUse=1 AND EpcNo=?";
	private final static String DISPATCH_EXTERNAL = "insert into dispatchexternal (DoNo,EpcNo,IMEI,VechNo,RLW,IssueTime) values(?,?,?,?,?,?)";
	private final static String DISPATCH_INTERNAL = "insert into internalactivity (DoNo,EpcNo,IMEIno,VechNo,RLW,DateTimeIssue) values(?,?,?,?,?,?);";
	private final static String EPC_INVENTORY = "update epcinventory set DateOfIssue = ?, InUse=1, ActivityNo=?,EpcType =? where EpcNo=?";
	private final static String GPS_INVENTORy = "update gpsinventory set InUse=1 where IMEI = ?";
	private final static String TRANS_ACT_NO = "select ActivityNo from dispatchexternal where DoNo=? AND EpcNo = ?";
	private final static String INTERN_ACT_NO = "select ActivityNo from internalactivity where DoNo=? AND EpcNo = ?";
	
	private final static String OTHER_GOODS = "insert into othergood (DateTimeIssue,QuantityType,VechNo,EpcNo) values(?,?,?,?)";
	private final static String OTHER_GOODS_ACT_NO = "select OtherActivityNo from othergood where VechNo=? AND EpcNo = ?";
	
	private final static String EPC_DETAIL = "select * from epcinventory where EpcNo = ? and InUse=1";
	private final static String INTERNAL_ACTIVITY_DETAIL = "select * from internalactivity where ActivityNo=?";
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<GPS> getIMEINoList() {
		
		log.info("*****AdminDetailImplement(epcDetail) :Get EPC List From Table***** ");
		RowMapper rm = new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				// TODO Auto-generated method stub
				GPS gps = new GPS();
				gps.setIMEI(rs.getInt("IMEI"));
				return gps;
			}
		};
		List<GPS> list = jdbcTemplate.query(IMEI_LIST, rm);
		log.info("*****AdminDetailImplement(epcDetail) :Get EPC List Size ***** "+list.size());
		return list;
	}
	
	@Override
	public boolean checkDoNoAvail(String doNo) {
		log.info("*****FrontOfficeDetailImplement(checkDoNoAvail) :Is Do_No Already Exist In Table***** "+doNo);
		RowMapper rm = new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				// TODO Auto-generated method stub
				int doNo = rs.getInt("DNo");
				return doNo;
			}
		};
		List<Integer> doNoList = jdbcTemplate.query(DO_NO_AVAIL,new Object[]{doNo}, rm);
		log.info("*****FrontOfficeDetailImplement(ChekDoNoAvail) :Do_No Info***** "+doNoList );
		if ( doNoList.isEmpty())
			return false;
		else
			return true;
	}

	@Override
	public boolean checkEpcNoStatus(String epcNo) {
		// TODO Auto-generated method stub
		log.info("*****FrontOfficeDetailImplement(checkEpcNoStatus) :Get EPC Number Available In Table***** "+epcNo);
		
		try{
			int EPCNo = (Integer)jdbcTemplate.queryForObject(IS_EPC_NO_EXIST,new Object[]{epcNo},Integer.class);
			log.info("*****FrontOfficeDetailImplement(checkEpcNoStatus) :Is EPC No Is Available in Table ***** "+EPCNo);
		
			RowMapper rm = new RowMapper() {
					@Override
				public Object mapRow(ResultSet rs, int i) throws SQLException {
			// TODO Auto-generated method stub
					int epc = rs.getInt("EpcNo");
					return epc;
				}
			};
			List<EPC> epcList = jdbcTemplate.query(EPC_NO_STATUS,new Object[]{epcNo}, rm);
			log.info("*****AdminDetailImplement(ChekEPCNoStatus) :EPN_No Info***** "+epcList);
			if (epcList.isEmpty())
				return true;
			else
				return false;			
		}
		catch(Exception e){
			log.info("*****AdminDetailImplement(ChekEPCNoStatus) :Erro***** "+e);
			return false;
		}
		
	}

	@Override
	public boolean enterDispatchExternalDetail(String DoNo,String EpcNo,String IMEI,String vechNo,String RLW) {
		log.info("*****FrontOfficeDetailImplement(enterDispatchExternalDetail) :***** ");
		log.info("*****DoNo: "+DoNo+" EPC_No: "+EpcNo+" IMEI: "+IMEI+" VECHICLE_NO: "+vechNo+" RLW: "+RLW+" *****");
		int rowsEffected = jdbcTemplate.update(DISPATCH_EXTERNAL,new Object[]{DoNo,EpcNo,IMEI,vechNo,RLW,getDate()});
		int transActivityNo = (Integer)jdbcTemplate.queryForObject(TRANS_ACT_NO, new Object[]{DoNo,EpcNo},Integer.class);
		int EPCCount = jdbcTemplate.update(EPC_INVENTORY,new Object[]{getDate(),transActivityNo,EpcNo,1});
		int GPSCount = jdbcTemplate.update(GPS_INVENTORy,new Object[]{IMEI});
		if(rowsEffected==1 && EPCCount==1 && GPSCount==1)
			return true;
		else
			return false;
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean enterDispatchInternalDetail(String DoNo,String EpcNo,String IMEI,String vechNo,String RLW) {
		log.info("*****FrontOfficeDetailImplement(enterDispatchInternalDetail) :***** ");
		log.info("*****DoNo: "+DoNo+" EPC_No: "+EpcNo+" IMEI: "+IMEI+" VECHICLE_NO: "+vechNo+" RLW: "+RLW+" *****");
		int rowsEffected = jdbcTemplate.update(DISPATCH_INTERNAL,new Object[]{DoNo,EpcNo,IMEI,vechNo,RLW,getDate()});
		int transActivityNo = (Integer)jdbcTemplate.queryForObject(INTERN_ACT_NO, new Object[]{DoNo,EpcNo},Integer.class);
		int EPCCount = jdbcTemplate.update(EPC_INVENTORY,new Object[]{getDate(),transActivityNo,EpcNo,2});
		int GPSCount = jdbcTemplate.update(GPS_INVENTORy,new Object[]{IMEI});
		if(rowsEffected==1 && EPCCount==1 && GPSCount==1)
			return true;
		else
			return false;
	}


	@Override
	public boolean enterOtherGoodsDetail(String otheGoods, String EpcNo,String VechNo) {
		log.info("*****FrontOfficeDetailImplement(enterOtherGoodsDetail) :***** ");
		log.info("***** QuantityType: "+otheGoods+" VECHICLE_NO: "+VechNo+" EPC_No: "+EpcNo+" *****");
		int rowsEffected = jdbcTemplate.update(OTHER_GOODS,new Object[]{getDate(),otheGoods,VechNo,EpcNo});
		int transActivityNo = (Integer)jdbcTemplate.queryForObject(OTHER_GOODS_ACT_NO, new Object[]{VechNo,EpcNo},Integer.class);
		int EPCCount = jdbcTemplate.update(EPC_INVENTORY,new Object[]{getDate(),transActivityNo,EpcNo,3});

		if(rowsEffected==1 && EPCCount==1)
			return true;
		else
			return false;
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

	@Override
	public EPC getEPCDetail(String epcNo) {
		log.info("*****AdminDetailImplement(getEPCDetail) :Get EPC Detail From Table Where EPC_No: *****"+epcNo);
		RowMapper rm = new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int i) throws SQLException {
				EPC epc = new EPC();
				epc.setDateOfIssue(rs.getString("DateOfIssue"));
				epc.setActivityNo(rs.getInt("ActivityNo"));
				epc.setEpcType(rs.getInt("EpcType"));
				return epc;
			}
		};
		List<EPC> epcList = jdbcTemplate.query(EPC_DETAIL,new Object[]{epcNo}, rm);
		if (!epcList.isEmpty())
			return epcList.get(0);
		else
			return null;
	}
	
	@Override
	public Activities getActivityDetail(int epcNo, int activityNo) {
		// TODO Auto-generated method stub
		return null;
	}
}
