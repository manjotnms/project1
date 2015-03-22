package mtss.Gui;

import static mtss.Gui.Constants.WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_IN;
import static mtss.Gui.Constants.WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_OUT_STATUS;
import static mtss.Gui.Constants.WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_OUT_WEIGTH_VALUE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import mtss.PassiveReader.main.PassiveRead;
import mtss.beans.BO.Epcinventory;

import com.mtss.logs.LogsMtss;
import com.mtss.plc.Requests;

/**
 * This class basically contains all the methods used in RunDialog.java.
 * 
 * @author gurpreet
 *
 */

public class Utils {
	/* Get actual class name to be printed on */
	static Logger log = LogsMtss.getLogger(Utils.class.getName());
	
	/**
	 * This method will return the String of parameters retrieved from PLC web server, 
	 * after firing the read request.
	 * Safe means that it will not return null, so that it makes less chances the code to fail.
	 * @return
	 */
	public static String invokeReadRequestSafe() {
		//String result = "v1@2&v2@4&v3@2&v4@54";
		String result = "";
		try {
			result = Requests.readRequest();
			while(result != null && result.length() < 6) {
				log.debug("Utils: invokeReadRequestSafe(): result is coming as null from PLC.");
				result = Requests.readRequest();
				Thread.sleep(2000);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * This method will write the value of parameter into PLC web server, 
	 * after firing the write request.
	 * @return true if successfully written.
	 */
	public static boolean invokeWriteRequest(String param, String value) {
		boolean result = false;
		try {
			if(value == null)
				return result;
			
			value = value.trim();
			
			if(param == null)
				return result;
			
			param= param.trim();
					
			result = Requests.writeRequest(param, value); 
			//result = true;
			while(!result) {
				log.debug("Utils: invokeReadRequestSafe(): result is coming as null from PLC.");
				result = Requests.writeRequest(param, value);
				Thread.sleep(2000);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * This map will be passed with the string and it will decode it into the hashmap which will be having the string parameters
	 * along with values in key-value pair.
	 * @param result (string of the format : v1@2&v2@4&v3@2&v4@54 )
	 * @return it returns the hashmap of the parameters along with the value of the string passed.
	 */
	public static Map<String, String> getParams(String result, Map<String,String> m) {
		//Map<String,String> m = new HashMap<>();
		m.clear(); // first clear all the mappings to reuse the same again.
		String arr[] = result.split("&amp;");
		if(arr!=null) {
			for(String a : arr) {
				if(a!=null && !a.equals("")) {
					String n[] = a.split("@");
					if(n.length > 1)
						m.put(n[0], n[1]);
					
				}
			}
		} else {
			log.debug("getParams(): Somehow params coming null from PLC.");
		}
		return m;
	}
	
	/**
	 * This method basically checks for the enteries in table 'DispatchActivity', incase it contains the entry with the 
	 * same epc number only then it is allowed.
	 * 
	 * @true, if DispatchActivity has one record of the same Epc number.
	 * otherwise false.
	 * 
	 */
	public static Epcinventory isEpcNumberValid(String epcNum) {
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		// query
		String query = "select * from epcinventory where Avail=1 and InUse=1 and EpcNo=?";
		
		// Call database to get dispatch activity entry for this epcNum.
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtss", "root", "root");
			st = con.prepareStatement(query);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException s) {
			s.printStackTrace();
		}
		
		try {
			st.setString(1, epcNum);
			rs = st.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			if(rs.next()) {
				log.debug("Epc number "+epcNum+" is valid..");
				Epcinventory epcinv = new Epcinventory();
				epcinv.setActivityNo(rs.getInt("ActivityNo"));
				epcinv.setEpcType(rs.getInt("EpcType"));
				
				String firstWeight_colName = "FirstWeight";
				String secondWeight_colName = "SecondWeight";
				boolean anyNull = false;
				
				// now epc inv is ok. So check weight validation. Atleast 1 wt should be null.
				String tableName = "", ActivityNoCol = "";
				if(epcinv.getEpcType() == 1) {
					tableName = "DispatchExternal";
					ActivityNoCol = "ActivityNo";
				} else if(epcinv.getEpcType() == 2) {
					ActivityNoCol = "ActivityNo";
					tableName = "InternalActivity";
				} else {
					ActivityNoCol = "OtherActivityNo";
					tableName = "OtherGood";
					firstWeight_colName = "Weight1";
					secondWeight_colName = "Weight2";
				}
				
		// Check from db is first weight done or not.
				try {
					rs = st.executeQuery("select * from "+tableName+" where "+ActivityNoCol+" = '"+epcinv.getActivityNo()+"' ");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				try {
					if(rs.next()) {
						log.debug("Ok got the entry in table:"+tableName);
						String frstWt = rs.getString(firstWeight_colName);
						String secondWt = rs.getString(secondWeight_colName);
						//String frstWtTime = rs.getString(firstWeightTime_colName);
						
						if(frstWt == null || frstWt.length() < 1 || secondWt == null || secondWt.length() < 1 )
						{
							log.debug("So it is indicating that first or second weight is not coming from DB. So can proceed");
							anyNull = true;
						} else {
							log.debug("So it is indicating that first and second weight coming from DB. So cannot proceed.");
							return null;
						}
						
					} else {
					 		log.debug("Sorry no entry is there in table:"+tableName);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return epcinv;
			} else {
				log.debug("Oops the epc number "+epcNum+" is not valid.....");
				return null;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(con != null) {
				try {
					con.close();
					
					if(rs != null)
						rs.close();
					
					if(st != null)
						st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		// If not got return null.
		return null;
	}
	
	public static int insertActivityRecord(Epcinventory epcinv, String weight, ArrayList listSnapshots) {
		//return 1;
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		int res = -1;
		//boolean validEpcType = true;
		/**
		  *  type = 1 : DispatchExternal.
		  *  type = 2 : InternalActivity.
		  *  type = 3 : OtherGood.
		  */

		try{ 
			// Call database to get dispatch activity entry for this epcNum.
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtss", "root", "root");
				st = con.createStatement();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException s) {
				s.printStackTrace();
			}
			
			// Query .
			String query = "";
			
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			String currTime = sdf.format(date);
			log.debug(currTime);
			
			String tableName= "dispatchexternal";
			String doTableName = "deliveryorderdetails";
			String firstWeight_colName = "FirstWeight";
			String firstWeightTime_colName = "FirstWeightTime";
			String secondWeight_colName = "SecondWeight";
			String secondWeightTime_colName = "SecondWeightTime";
			String activityColName= "ActivityNo";
			
			// for epc type 1 no improvisation of above variables is needed.
			
			if(epcinv.getEpcType() == 1) {
				
			} else if (epcinv.getEpcType() == 2) {
				tableName= "internalactivity";
				doTableName = "";
			} else if (epcinv.getEpcType() == 3) {
				doTableName = "";
				tableName= "othergood";
				firstWeight_colName = "Weight1";
				firstWeightTime_colName = "DateTimeIssue";
				secondWeight_colName = "Weight2";
				secondWeightTime_colName = "DateReturn";
			} else {
				log.debug("This is something went wrong... epc type can not be other than 1, 2, 3. Current epctype is :"+epcinv.getEpcType());
				//validEpcType = false;
				return -1;
			}
			
			boolean storeFirstWeight = false;
			
			//  Check from db is first weight done or not.
			try {
				rs = st.executeQuery("select * from "+tableName+" where "+activityColName+" = '"+epcinv.getActivityNo()+"' ");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(rs.next()) {
					log.debug("Ok got the entry in table:"+tableName);
					String frstWt = rs.getString(firstWeight_colName);
					//String frstWtTime = rs.getString(firstWeightTime_colName);
					if(frstWt == null || frstWt.length() < 1 ) {
						log.debug("So it is indicating that first weight is not coming from DB.");
						storeFirstWeight = true;
					}
				} else {
				 		log.debug("Sorry no entry is there in table:"+tableName);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String imagesPrefix = "";
			if(storeFirstWeight) {
				imagesPrefix = "First";
				// if first weight.
				query = "update "+tableName+" set "+firstWeight_colName+"='"+weight+"', "+firstWeightTime_colName+" = '"+currTime+"' ";
			} else {
				imagesPrefix = "Second";
				// if second weight.
				query = "update "+tableName+" set "+secondWeight_colName+"='"+weight+"', "+secondWeightTime_colName+" = '"+currTime+"' ";
			}				
			
			// If images are ok then add them as well.
			if(listSnapshots.size() > 0) {
				log.debug("Got some images to store !!!");
				query = query.concat(", "+imagesPrefix+"Front = '"+listSnapshots.get(0)+"' ");
				if(listSnapshots.size() > 1) {
					query = query.concat(", "+imagesPrefix+"Middle = '"+listSnapshots.get(1)+"' ");
					if(listSnapshots.size() > 2) {
						query = query.concat(", "+imagesPrefix+"Rear = '"+listSnapshots.get(2)+"' ");
					}
				}
			} else {
				log.debug("Images list is empty !!!!!!!");
			}
			
			// end of query.
			query = query.concat(" where  "+activityColName+" = '"+epcinv.getActivityNo()+"' ");
			
			log.debug("Final update Query is :"+query);
			
			try {
				log.debug(query);
				res = st.executeUpdate(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			int upRes = -1;
			if(res >= 1) {
				log.debug("Means weight inserted into table. Now update the delivery order tables to make them in sync.");
				
				//Integer qtybalInt = new Integer(0);
				Integer weightInt = new Integer(0);
				try {
					weightInt = Integer.parseInt(weight);
				} catch(Exception ee) {
					log.debug("Some problem in parsing qtybal and weight.");
				}
				
				String updateQ = "update "+doTableName+" set qtybalance = qtybalance - "+weightInt+" " +
						"where "+activityColName+" = '"+epcinv.getActivityNo()+"' ";
				try {
					if(epcinv.getEpcType() == 1 || epcinv.getEpcType() == 2) {
						upRes = st.executeUpdate(updateQ);
						log.debug("As epctype is :"+epcinv.getEpcType()+", Update query executed !! :"+upRes);
					} else {
						log.debug("Epc Type is :"+epcinv.getEpcType()+", So not executing update query !!");
					}
				} catch(Exception e) {
					log.debug("Updation failed ..... upRes:"+upRes);
					e.printStackTrace();
				}
			} else {
				log.debug("res is :"+res+". So not updating the qtybalance.");
			}
			
			if(upRes > 0) {
				log.debug("Successfully update !!! ");
			} else {
				log.debug("Due to some problem not able to update the qty balance.");
			}
			
		} catch(Exception e){
			log.debug("Exception in storing the Weight into activity record !!!!!");
			e.printStackTrace();
		} finally {
			if(con != null) {
				try {
					con.close();
					
					if(rs != null)
						rs.close();
					
					if(st != null)
						st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return res;
	}
	
	/*
	 * Validate the transaction.
	 */
	public static String validateTransaction(Epcinventory epcinv, String weight) {
		String status = Constants.WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_OUT_STATUS_VALUE_Ok; // status 1 means ok.
		if(epcinv.getEpcType() == 3 ) {
			return status; // in case of other goods.
		}
		
		log.debug("Inside Utils.validateTransaction(): for epc:"+epcinv.getEpcNo()+"");
		
		Connection con = null;
		Statement st = null;
		ResultSet rs = null, rs2= null;
		String tableName = "", orderDetailsTableName = "";
		boolean epcTypeValid = true;
		/**
		 *  type = 1 : DispatchExternal.    , deliveryorderdetails
		 *  type = 2 : InternalActivity.    , intercoaltransfer
		 *  type = 3 : OtherGood.
		 */
		String query = "";
		String doBalanceQuery = "";
		if(epcinv.getEpcType() == 1) {
			tableName = "DispatchExternal";
			orderDetailsTableName = "deliveryorderdetails";
			query = "select * from "+tableName+" where ActivityNo = '"+epcinv.getActivityNo()+"'";
			doBalanceQuery = "select b.Qtybalance from "+tableName+" a, "+orderDetailsTableName+" b " +
								" where activityno = 1 and a.DoNo = b.DoNo ";
		} else if(epcinv.getEpcType() == 2) {
			tableName = "InternalActivity";
			orderDetailsTableName = "intercoaltransfer";
			query = "select * from "+tableName+" where ActivityNo = '"+epcinv.getActivityNo()+"'";
			doBalanceQuery = "select b.Qtybalance from "+tableName+" a, "+orderDetailsTableName+" b " +
					" where activityno = 1 and a.DoNo = b.DoNo ";
		} else if(epcinv.getEpcType() == 3) {
			tableName = "OtherGood";
			query = "select * from "+tableName+" where OtherActivityNo = '"+epcinv.getActivityNo()+"' ";
		} else {
			epcTypeValid = false;
		}
		try { 
				
				if(epcTypeValid) {
					
					// Call database to get dispatch activity entry for this epcNum.
					try {
						Class.forName("com.mysql.jdbc.Driver");
						con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mtss", "root", "root");
						st = con.createStatement();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException s) {
						s.printStackTrace();
					}
					
					//------------------------------------------------------------RLW -----------------------------------------
					try {
						rs = st.executeQuery(query);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					// rlw.
					try {
						if(rs.next()) {
							log.debug("Found an entry in table:"+tableName+", for activityNo:"+epcinv.getActivityNo()+". Success !!");
							String rlw = rs.getString("RLW");
							if(rlw != null && rlw.length() > 1) {
								// Now got rlw .
								log.debug("RLW got : "+rlw);
								log.debug("Compairing the rlw with weight !!");
								Integer rlwInt = new Integer(0);
								Integer weightInt = new Integer(0);
								try {
									rlw = rlw.trim();
									weight = weight.trim();
									// Trimmed , now convert into integer.
									rlwInt = Integer.parseInt(rlw);
									weightInt = Integer.parseInt(weight);
									if(weightInt > rlwInt) {
										log.debug("Weight is more than RLW. So give the Error.");
										log.debug("Weight:"+weightInt+", Rlw:"+rlwInt);
										return Constants.WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_OUT_STATUS_VALUE_Overload;
									} else {
										log.debug("Weight is not more than RLW. So OK.");
									}
								} catch (Exception ee) {
									ee.printStackTrace();
									log.debug("Some exception in converting the rlw and weight into Integer.");
								}
								
							} else {
								log.debug("RLW is empty !!!!!!!!!!!!!!!!!!");
								return Constants.WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_OUT_STATUS_VALUE_Overload;
							}
						} else {
							log.debug("Oops there is no entry in table:"+tableName+", for activityNo:"+epcinv.getActivityNo()+"....");
							return null;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					} 
					
//------------------------------------------------------------DO balance -----------------------------------------//
					try {
						rs2 = st.executeQuery(doBalanceQuery);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					// do.
					try {
						if(rs2.next()) {
							log.debug("Found an entry in table:"+orderDetailsTableName+", for activityNo:"+epcinv.getActivityNo()+". Success !!");
							String qtyBalance = rs2.getString("QtyBalance");
							if(qtyBalance != null && qtyBalance.length() > 1) {
								// Now got QtyBalance .
								log.debug("QtyBalance got : "+qtyBalance);
								Integer qtylimitInt = new Integer(0);
								Integer weightInt = new Integer(0);
								Integer diff = new Integer(0);
								try {
									qtylimitInt = Integer.parseInt(qtyBalance);
									weightInt = Integer.parseInt(weight);
									diff = qtylimitInt - weightInt;
									log.debug("Minus the weight:"+weight+" from qtyBalance:"+qtyBalance+"  = "+diff+"  !");
									
									// 20 KGs up can be there.
									if(diff < -20) {
										log.debug("Diff is more than 20 KGs. So give the Error.");
										return Constants.WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_OUT_STATUS_VALUE_ExceedsOrderQuantity;
									} else {
										log.debug("Diff is not more than 20 KGs.");
									}
								} catch (Exception ee) {
									ee.printStackTrace();
									log.debug("Some exception in converting the rlw and weight into Integer.");
								}
							} else {
								log.debug("Qty Balance is empty !!!!!!!!!!!!!!!!!!");
								return Constants.WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_OUT_STATUS_VALUE_ExceedsOrderQuantity;
							}
						} else {
							log.debug("Oops there is no entry in table:"+tableName+", for activityNo:"+epcinv.getActivityNo()+"....");
							return null;
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}

					
				} else {
					log.debug("Cannot do the transaction as type of epc is not valid. Type :"+epcinv.getEpcType());
				}
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			if(con != null) {
				try {
					con.close();
					if(rs != null)
						rs.close();
					
					if(rs2 != null)
						rs2.close();
					
					if(st != null)
						st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return status;
	}
	
	public static boolean invokeAllSetZeroPLC() {
		boolean successfullyWrite = false;
		// v1
		do {
			successfullyWrite = Utils.invokeWriteRequest(WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_IN, Constants.VALUE_0);
			if(!successfullyWrite){
				log.debug("Write request Failed !! successfullyWrite:"+successfullyWrite);
				log.debug("Request will go again !!");
			} else {
				log.debug("Write request Success !! successfullyWrite:"+successfullyWrite);	
			}
			
		} while(!successfullyWrite);
		
		// v3
		do {
			successfullyWrite = Utils.invokeWriteRequest(WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_OUT_STATUS, Constants.VALUE_0);
			if(!successfullyWrite){
				log.debug("Write request Failed !! successfullyWrite:"+successfullyWrite);
				log.debug("Request will go again !!");
			} else {
				log.debug("Write request Success !! successfullyWrite:"+successfullyWrite);	
			}
			
		} while(!successfullyWrite);
		
		//wt
		do {
			successfullyWrite = Utils.invokeWriteRequest(WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_OUT_WEIGTH_VALUE, Constants.VALUE_0);
			if(!successfullyWrite){
				log.debug("Write request Failed !! successfullyWrite:"+successfullyWrite);
				log.debug("Request will go again !!");
			} else {
				log.debug("Write request Success !! successfullyWrite:"+successfullyWrite);	
			}
			
		} while(!successfullyWrite);
		
		return successfullyWrite;
	}
	
	/*
	 * This method will call the url which inturn return the image byte code. That needs to be in the database.
	 */
	public static ArrayList<String> getSnapShots() {
		ArrayList<String> list = new ArrayList<>();
		
		// Get 1st camera screenshot.
		String first = "";
		list.add(first);
		
		// Get 2nd camera screenshot.
		String second = "";
		list.add(second);
		
		// Get 3rd camera screenshot.
		String third = "";
		list.add(third);
		
		return list;
	}
	
	
	public static void main(String a[]) {
		// this is for testing purposes.
		//isEpcNumberValid("2,");
		
		// Testing validate
		/*
		Epcinventory epcinv = new Epcinventory();
		epcinv.setEpcType(1);
		epcinv.setActivityNo(1);
		validateTransaction(epcinv, "254");
		*/
		
		// Testing insert into DB.
		/*
		Epcinventory epcinv = new Epcinventory();
		epcinv.setEpcType(1);
		epcinv.setActivityNo(1);
		insertActivityRecord(epcinv, "455");
		*/
	}
	

}
