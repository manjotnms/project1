package mtss.Gui;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import static mtss.Gui.Constants.*;
import mtss.PassiveReader.main.PassiveRead;
import mtss.PassiveReader.main.PassiveReaderPropertyConstants;
import mtss.Weighbridge.WeighingMachineSimpleRead;
import mtss.beans.BO.Epcinventory;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.mtss.logs.LogsMtss;

import bsh.util.Util;

/*import com.weighbridge.PropertiesConstants;
import com.weighbridge.WeighingMachineSimpleRead;*/

/**
 * @author Gurpreet.singh
 * 
 */
public class MainClass {
	/* Get actual class name to be printed on */
	static Logger log = LogsMtss.getLogger(MainClass.class.getName());

	public static String epcNum = "-- empty --";
	public static String weight = "-- empty --";
	public static Epcinventory epcinv = null;
	public static boolean rfid_24_STATUS = false;
	public static boolean rfid_22_STATUS = false;
	public static boolean weight_scale_STATUS = false;
	public static String not_Started = "- Not Started --";
	public static String started_stat = "Ok Started.";
	
	public static String refreshed = " -- REFRESHING -- ";
	public static String epcLoaded = " -- Loading EPC -- ";
	public static String validatingEpcLoaded = " -- Validating EPC -- ";
	public static String epcTaken = " -- EPC TAKEN -- ";
	
	public static String WEIGH_SCALE_LOADED = " -- WEIGH SCALE LOADED -- ";
	public static String WEIGHT_TAKEN = " -- WEIGHT TAKEN -- ";
	public static String TRANSACTION_COMPLETE = " -- TRANSACTION COMPLETE -- ";
	
	
	public static String whichEpc = "--";
	public static boolean weight_Service_Called = false;
	
	public static PassiveRead thread_passiveRead_One = null;
	public static PassiveRead thread_passiveRead_Two = null;
	
	public static WeighingMachineSimpleRead thread_Weighing = null;
	
	public static void main(String[] args) {
		callService();
	}
	
	public static void initFrame() {
		epcNum = "-- empty --";
		weight = "-- empty --";
		
		rfid_24_STATUS = false;
		rfid_22_STATUS = false;
		
		weight_scale_STATUS = false;
		not_Started = "-- Not Started --";
		started_stat = "Ok Started.";
		
		whichEpc = "--";
		weight_Service_Called = false;
		
		epcinv = null;
		
		//thread_passiveRead_One = null;
		PassiveRead.epc_Number_Selected = "";
		PassiveRead.is_epc_Number_Selected = false;
		
		//thread_passiveRead_Two = null;
	}
	
	public static void callService() {
		String reasonVariableValue = "1"; // basically value of v3.
		
		DispatchMont.weight_freezed = false;
		//  Just start the JFrame with dummy values.
		DispatchMont.putValuesRepaint(not_Started, not_Started, not_Started, whichEpc, epcNum, weight);
		if(loadOnceEpcService() > 0) {
			log.debug("Both epc's thread dispatched. Not sure that any reader(s) is/are connected.");
			DispatchMont.refreshStatusOnly(epcLoaded);
			
			if(getEpcService()) {
				log.debug("Got one epc number: Epc:"+epcNum);
				log.debug("Start validating Epc Number:"+epcNum);
				
				DispatchMont.refreshStatusOnly(validatingEpcLoaded);
				
				/*
				 * As now EPC Number is taken. Close all the RFID reader's reading.
				 */
				
				closeAllRfidReaders();
				
				boolean epcValidFromDb = false;
				// validate epc number.
				epcinv = Utils.isEpcNumberValid(epcNum);
				if(null != epcinv && epcinv.getEpcType() !=null && epcinv.getActivityNo() != null && 
					epcinv.getEpcType().intValue() > 0 && epcinv.getActivityNo().intValue() > 0 ) {
					log.debug("Epc number "+epcNum+" is valid.");
					epcValidFromDb = true;
				} else {
					// in case not valid.
					log.debug("Epc number "+epcNum+" is not valid.");
				}
				
				if(!epcValidFromDb) {

					DispatchMont.refreshStatusOnly(EPC_NOT_VALID);
					// This method will refresh the cycle and not let the code go further.
					try {
						log.debug("Sleep for 2 seconds so that refreshing is done properly !!");
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					CompleteRefreshCycle();
					
				} else {
					log.debug("So as epc is valid. Continuing with further process !!");
					DispatchMont.refreshStatusOnly(EPC_VALID);
					
	/*------------------------------------------------------------------------------- */
					// here write the parameters v1, v3, wt to 0. At start of the transaction.
					Utils.invokeAllSetZeroPLC();
					
					log.debug("Trigger PLC for v1 as true.");
					// -- Start Code Step 1-- PLC Trigger 1.
					DispatchMont.refreshStatusOnly( LET_TRUCK_IN );
					
					// Make a write request here to write PLC.
					boolean successfullyWrite = false;
					
					String valueOfParameter = "";
					
					if(whichEpc.contains(PassiveReaderPropertyConstants.RFID_READER_DS_WB_22)) {
						valueOfParameter = VALUE_WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_IN_RFID_22;
					} else {
						valueOfParameter = VALUE_WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_IN_RFID_24;
					}
					
					//valueOfParameter = VALUE_WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_IN_RFID_22;
					do {
						successfullyWrite = Utils.invokeWriteRequest(WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_IN, valueOfParameter);
						if(!successfullyWrite){
							log.debug("Write request Failed !! successfullyWrite:"+successfullyWrite);
							log.debug("Request will go again !!");
						} else {
							log.debug("Write request Success !! successfullyWrite:"+successfullyWrite);	
						}
					} while(!successfullyWrite);
					
					
				//--- End Code Step 1-- PLC Trigger 1.
					
				/**
				  * At this point, PLC is triggered with the intimation that Open the boom-barriers. Green light. 
				  * And give us a signal to read the weight (after button press by Driver).
				  *  
				  */
					
				// -- Start Code Step 2 -- Call the url & wait until PLC puts a signal for us to start taking weight..
						
					//String parameter = "v1";
					//String valueExpected = "1";
					Map<String, String> m = new HashMap<String, String>(); 
					// This map will be having the separated parameters with values after calling Utils.getParams(result, m);.
					
					String buttonValue = "";
					do {
						log.debug("Going into sleep for 2 second. " +
								"As "+READ_PARAM_BUTTON+" not having expected value i.e. "+VALUE_1 +" or "+VALUE_2);
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
						String result = Utils.invokeReadRequestSafe();
						if( result != null && !result.equals("") ) {
							Utils.getParams(result, m);
							//log.debug(m.get("v1"));
							log.debug("Retreived params from PLC_String :"+m);
						} else {
							log.debug("Not Retreived any params from PLC String. Coming as null.");
						}
						
					//} while (m.get(READ_PARAM_BUTTON) == null || ( !m.get(READ_PARAM_BUTTON).equals(VALUE_1) || !m.get(READ_PARAM_BUTTON).equals(VALUE_2)));
					} while ( m.get(READ_PARAM_BUTTON) == null || m.get(READ_PARAM_BUTTON).equals(VALUE_0) );
			
			// Now read the button value.
					// Get the button value from PLC, if 1 means GREEN, set buttonValue to true, other wise let it be as it ease means false in case of RED button.
					buttonValue = m.get(READ_PARAM_BUTTON);
					if(buttonValue != null && buttonValue.equals(VALUE_1)) {
						
						DispatchMont.refreshStatusOnly( GREEN_BUTTON_PRESSED );
						
 //-------------------------------------- Take weight ----------------------------------------------
						log.debug("Load weight service.");		
						if ( loadOnceWeightService() ) {
								DispatchMont.refreshStatusOnly(WEIGH_SCALE_LOADED);
								log.debug("Get the output of weight service. Only if the weight service is connected.");
								getWeightService();
						}
						
						// refresh the weigh scale.
						closeWeightMachine();
						
						/**
						 * At this point, Weight is taken. The task is now to apply validations .
						 */
						
						// Send the weight to PLC.
						
						// Make a write request here to write Weight into PLC.
						boolean successfullyWrite3 = false;
						do {
							successfullyWrite3 = Utils.invokeWriteRequest(WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_OUT_WEIGTH_VALUE, weight);
							if(!successfullyWrite3){
								log.debug("Write request Failed !! successfullyWrite3:"+successfullyWrite3+ ". Request will go again !!");
							} else {
								log.debug("Write request Success !! successfullyWrite3:"+successfullyWrite3);
							}
						} while(!successfullyWrite3);
						
						// Take the weight from PLC at-least 5 attempts to ensure that it is written.
						
						int attempts = 0;
						boolean weightStable = false;
						ArrayList<Integer> weightsTaken = new ArrayList<>();
						//m = new HashMap<String, String>();  // initializing the map again.
						// This map will be having the separated parameters with values after calling Utils.getParams(result, m);.
						do {
							log.debug("Going into sleep for 1 second. " +
									"As "+WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_OUT_WEIGTH_VALUE+" not having expected value i.e.");
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							
							String result = Utils.invokeReadRequestSafe();
							if( result != null && !result.equals("") ) {
								Utils.getParams(result, m);
								//log.debug(m.get("v1"));
								log.debug("Retreived params from PLC_String :"+m);
							} else {
								log.debug("Not Retreived any params from PLC String. Coming as null.");
							}
							
							if( m.get(WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_OUT_WEIGTH_VALUE) != null ) {
								log.debug("weight added to attempts...");
								weightsTaken.add(new Integer(m.get(WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_OUT_WEIGTH_VALUE)));
								attempts++;
							}
							
							if(attempts > 4) {
								log.debug("Attempts Sufficient.."+attempts);
								weightStable = isWeightStable(weightsTaken);
								/*if( m.get(WRITE_PARAM_FROM_PLC_WEIGHT) != null ) {
									if( m.get(WRITE_PARAM_FROM_PLC_WEIGHT).equals(WRITE_PARAM_FROM_PLC_WEIGHT) ) {
										weightsTaken.add(Integer.parseInt(m.get(WRITE_PARAM_FROM_PLC_WEIGHT)));
										weightStable = true;
									}
								}*/
							} else {
								log.debug("Lesser attempts :"+attempts);
							}
						} while(weightStable);
						
						
//--------------------------- Put validations. 1st stage --------------------------------------------------
						
						// here change reasonVariableValue based on 2 validations i.e. RLW and DO balance. 
						// here value of reasonvar can be changed to 2, 3, 4.
						reasonVariableValue = Utils.validateTransaction(epcinv, weight);
						
						if(reasonVariableValue == null)
							reasonVariableValue = "1";
						// -- End Code -- Apply validations.
						
						
						if(reasonVariableValue != null && reasonVariableValue.equals("1")) {
							
		//v3=1---------------------------- Take snapshot --------------------------------------------------------------
							ArrayList<String> listSnapshots = Utils.getSnapShots(); 
							
		//v3=1--------------------------- Store in DB, 2nd stage ------------------------------------------------------
							
							log.debug("Weight is Valid. So carry On.. And it is told by plc to store the weight.");
							log.debug("Store everything in DB...");
							try {
								int res = Utils.insertActivityRecord(epcinv, weight, listSnapshots);
								if(res > 0) {
									reasonVariableValue = "1";
									log.debug("Inserted Successfully !!!!!!!!!");
								} else {
									reasonVariableValue = "5"; // System Error 
									log.debug("Not inserted !!! Transaction incomplete !!!");
								}
							} catch (Exception e) {
								// TODO: handle exception
								reasonVariableValue = "5"; // System Error 
								log.debug("Exception in code !!! Not inserted !!!!");
								e.printStackTrace();
							}
							
						} else {
							
		//v3!=1-----------------------------------------------------------------------------------
							log.debug("The value of v3 is not one after validations. v3="+reasonVariableValue);
						}
						
					} else {
						
						DispatchMont.refreshStatusOnly( RED_BUTTON_PRESSED );
						// set reasonVar = 6. Transaction Cancelled.
						reasonVariableValue = "6";
						// button = 2 means red.
						log.debug("button is red so just pursue the transaction... SKIP storing db.");
						
					}
					
					
//----------------------------------- Send v3/wt --------------------------------------------------------------------------------
					/**
					 * 1 :- Ok
					 * 2 :- Overload
					 * 3 :- Exceed Weight
					 * 4 :- 
					 * 5 :- System Error
					 * 6 :- Transaction Cancelled.
					 * 
					 */
					
					// Make a write request here to write PLC : write v3.
					boolean successfullyWrite2 = false;
					do {
						successfullyWrite2 = Utils.invokeWriteRequest(WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_OUT_STATUS, reasonVariableValue);
						if(!successfullyWrite2){
							log.debug("Write request Failed !! successfullyWrite:"+successfullyWrite2+" for var:"+WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_OUT_STATUS);
							log.debug("Request will go again !! v3 reason:"+reasonVariableValue);
						} else {
							log.debug("Write request Success !! successfullyWrite:"+successfullyWrite2);	
						}
						
					} while(!successfullyWrite2);
					
//--------------------------- Reading v4 & restart transaction. Take it as v4 = 1. It means restart the whole transaction---------
					
					// -- Start Code Step 6 -- Call the url & wait until PLC puts a signal for us to start recycling everything.		
					//m = new HashMap<String, String>();  // initializing the map again.
					// This map will be having the separated parameters with values after calling Utils.getParams(result, m);.
					do {
						log.debug("Going into sleep for 1 second. " +
								"As "+READ_PARAM_FROM_PLC_RESTART_TRANSACTION+" not having expected value i.e. "+VALUE_1);
						try {
							  Thread.sleep(1000);
						} catch (InterruptedException e) {
							  e.printStackTrace();
						}
						
						String result = Utils.invokeReadRequestSafe();
						if( result != null && !result.equals("") ) {
							Utils.getParams(result, m);
							//log.debug(m.get("v1"));
							log.debug("Retreived params from PLC_String :"+m);
						} else {
							log.debug("Not Retreived any params from PLC String. Coming as null.");
						}
					} while (m.get(READ_PARAM_FROM_PLC_RESTART_TRANSACTION) == null || !m.get(READ_PARAM_FROM_PLC_RESTART_TRANSACTION).equals(VALUE_1));
					
					DispatchMont.refreshStatusOnly("Truck is out. Got ok from plc to refresh for a new call.");
							
					// As now got v4 as 1. So clear v3 first.
					// Make a write request here to write PLC : write v3.
					boolean successfullyWrite23 = false;
					do {
						successfullyWrite23 = Utils.invokeWriteRequest(WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_OUT_STATUS, VALUE_0);
						if(!successfullyWrite23){
							log.debug("Write request Failed !! successfullyWrite:"+successfullyWrite23);
							log.debug("Request will go again !!");
						} else {
							log.debug("Write request Success !! successfullyWrite:"+successfullyWrite23);	
						}
						
					} while(!successfullyWrite23);
					
					// As now got v4 as 1. So clear wt first. Make a write request here to write PLC : write wt.
					boolean successfullyWrite24 = false;
					do {
						successfullyWrite24 = Utils.invokeWriteRequest(WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_OUT_WEIGTH_VALUE, VALUE_0);
						if(!successfullyWrite24){
							log.debug("Write request Failed !! successfullyWrite:"+successfullyWrite23);
							log.debug("Request will go again !!");
						} else {
							log.debug("Write request Success !! successfullyWrite:"+successfullyWrite23);	
						}
						
					} while(!successfullyWrite24);
					
					
					/**
					 * At this point, PLC is indicating that recycle each and everything. Means Truck is out now.
					 * 
					 */
			
					DispatchMont.refreshStatusOnly(TRANSACTION_COMPLETE);
					CompleteRefreshCycle();
					
// --------------------------------------------------- END ------------------------------------------------------------------------
					
				}
			} else{
					log.debug("No rfid reader is getting connected.");
			}
		}
	}
	
	public static void CompleteRefreshCycle() {
		
		log.debug("Refreshing the cycle !!");
		initFrame();
		
		log.debug("Done with refreshing. Continue....");
		log.debug("Transaction is completed !!");
		// Show refreshing on gui for 2 seconds.
		DispatchMont.refreshStatusOnly(refreshed);
		
		try {
			Thread.sleep(2000); // 2 seconds to wait..
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //
		
		// call again the service for next cycle.
		callService();
	}
	
	@SuppressWarnings("deprecation")
	public static void closeAllRfidReaders() {
		log.debug("closeAllRfidReaders(): Reseting everything for a fresh call.....Rfid. As rfid's work is over.");
		
		// close
		thread_passiveRead_One.disConnectReader();
		thread_passiveRead_Two.disConnectReader();
		
		// close the thread first.
		thread_passiveRead_One.stop();
		thread_passiveRead_Two.stop();
		
		// nullify the objects.
		thread_passiveRead_One = null;
		thread_passiveRead_Two = null;
	}
	
	public static void closeWeightMachine() {
		log.debug("closeWeightMachine(): Reseting everything for a fresh call.....weight. As weigh scale's work is over.");
		// set the static flag of class as true, else will be taken care of the thread.
		WeighingMachineSimpleRead.refreshFlag = true;
	}
		
	public static int loadOnceEpcService( ) {
		int i = 0;
		try {			
			// Connect reader 1.
			thread_passiveRead_One = new PassiveRead(  PassiveReaderPropertyConstants.RFID_READER_DS_WB_22 );
			thread_passiveRead_One.start();
			i++;
			
			// Connect reader 2.
			thread_passiveRead_Two = new PassiveRead(  PassiveReaderPropertyConstants.RFID_READER_DS_WB_24  );
			thread_passiveRead_Two.start();
			i++;
			
			return i;
		} catch(Exception e){
			e.printStackTrace();
			return i;
		}
	}
	
	public static boolean loadOnceWeightService() {
		boolean st = false;
		do {
			st = WeighingMachineSimpleRead.loadOnceWeightService();
			if(st) {
				log.debug("Success Weight Machine Connected !!");
				break;
			} else {
				log.debug("Failed to connect Weight Machine !! Another Attempt !");
			}
		} while (!st);
		return st;
	}
	
	public static boolean getEpcService() {
		log.debug("In getEpcService(): Start getting epc number.");
		// set epc number.
		while(true) {
			if(thread_passiveRead_One.connected || thread_passiveRead_Two.connected) {
				log.debug("is sel:"+PassiveRead.is_epc_Number_Selected+" & num:"+PassiveRead.epc_Number_Selected);
				if( PassiveRead.is_epc_Number_Selected  && null != PassiveRead.epc_Number_Selected
						&& PassiveRead.epc_Number_Selected.length() > 1) {
					if(PassiveRead.epc_Number_Selected != null && PassiveRead.epc_Number_Selected.length() > 0) {
						epcNum = PassiveRead.epc_Number_Selected;
						whichEpc = PassiveRead.readerUsed_epc_Number_Selected;
						
						if( weight_scale_STATUS )
							DispatchMont.putValuesRepaint(started_stat, started_stat, started_stat, whichEpc, epcNum, weight);
						else 
							DispatchMont.putValuesRepaint(started_stat, started_stat, not_Started, whichEpc, epcNum, weight);
						
						return true;
						//break;
					}
				}
			}
			
			// Try after every 1 second to get the epc number again.
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} // end of while loop.
	}
	
	public static boolean isWeightStable (ArrayList<Integer> a) {
		boolean isWeightStable = true;
		log.debug("In isWeightStable():");
		
		if(a == null) {
			log.debug("List is coming as null.");
			return false;
		}
		
		if(a.size() > 4) {
			log.debug("isWeightStable(): Size more than 5. Size:"+a.size());
			/*Integer one = a.get(0);
			if(one < MIN_WEIGHT) {
				log.debug("Minimum weight condition is not satisfying. Weight:"+one+", min_weight:"+MIN_WEIGHT);
				return false;
			}
			for(int i=0; i<a.size(); i++) {
				log.debug(a);
				Integer newOne = a.get(i);
				if( (one - newOne) > 50) {
					log.debug("Unstable: one:"+one+" , newOne:"+newOne);
					isWeightStable = false;
				}
			}
			*/
			if(isWeightStable)
				log.debug("isWeightStable(): Ok. After comparison");
			else
				log.debug("isWeightStable(): Not Ok.  After comparison");
			
			return isWeightStable;
		} else {
			log.debug("isWeightStable(): Not Ok. Size less than 5. Size:"+a.size());
			return false;
		}
		
	}
	
	public static void getWeightService() {
		ArrayList<Integer> weightattempts = new ArrayList<Integer>();
		while(true) {
			if(DispatchMont.weight_freezed) {
				JOptionPane.showMessageDialog(new JOptionPane(), "Weight Freezed !!!!");
				break;
			}
			// set weight
			if(WeighingMachineSimpleRead.weight_Calculated != null &&
					WeighingMachineSimpleRead.weight_Calculated.length() > 0 )
			 		//&& weight != WeighingMachineSimpleRead.weight_Calculated)  
			{
				weight = WeighingMachineSimpleRead.weight_Calculated;
				weight.trim();
				DispatchMont.putValuesRepaint(started_stat, started_stat, started_stat, whichEpc, epcNum, weight);
				//JOptionPane.showMessageDialog(new JOptionPane(), "Weight :"+weight+"  epc:"+epcNum);
				if(isWeightStable(weightattempts)) {
				//if(WeighingMachineSimpleRead.refreshFlag) {
				//if ( null != weight && !weight.equals("") ) {
					// at this point in case 5 constant weights are coming as stable then 
					// 21 feb: Currently changing logics. Just get one weight.
					log.debug("In this cycle weight has been taken. So stop taking weight !!");
					DispatchMont.refreshStatusOnly(WEIGHT_TAKEN);
					break;
				}
				
				if(Integer.parseInt(weight.trim()) > MIN_WEIGHT) {
					if(weight != null && !weight.equals("")) {
						weightattempts.add(Integer.parseInt(weight.trim()));
					}
				}
			}
			//weight += 1;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			log.debug("Weight :"+weight+"  epc:"+epcNum);
	    }
	}
	
	
	
}
