package mtss.Gui;
import javax.swing.JOptionPane;

import mtss.PassiveReader.ReaderApi.ReaderAPI;
import mtss.PassiveReader.main.PassiveRead;
import mtss.PassiveReader.main.PassiveReaderPropertyConstants;
import mtss.Weighbridge.WeighingMachineSimpleRead;

import org.apache.log4j.Logger;

/*import com.weighbridge.PropertiesConstants;
import com.weighbridge.WeighingMachineSimpleRead;*/

/**
 * @author Gurpreet.singh
 * 
 */
public class RunDialog {
	/* Get actual class name to be printed on */
	static Logger log = Logger.getLogger(RunDialog.class.getName());

	public static String epcNum = "-- empty --";
	public static String weight = "-- empty --";
	
	public static boolean rfid_24_STATUS = false;
	public static boolean rfid_22_STATUS = false;
	public static boolean weight_scale_STATUS = false;
	public static String not_Started = "-- Not Started --";
	public static String started_stat = "Ok Started.";
	
	public static String whichEpc = "--";
	public static boolean weight_Service_Called = false;
	
	public static PassiveRead thread_passiveRead_One = null;
	public static PassiveRead thread_passiveRead_Two = null;
	
	public static void main(String[] args) {
		callService();
		
	}
	
	public static void init(){
		epcNum = "-- empty --";
		weight = "-- empty --";
		
		rfid_24_STATUS = false;
		rfid_22_STATUS = false;
		
		weight_scale_STATUS = false;
		not_Started = "-- Not Started --";
		started_stat = "Ok Started.";
		
		whichEpc = "--";
		weight_Service_Called = false;
		
		//thread_passiveRead_One = null;
		PassiveRead.epc_Number_Selected = "";
		PassiveRead.is_epc_Number_Selected = false;
		
		//thread_passiveRead_Two = null;
	
	}
	public static void callService() {
		MyFrame.weight_freezed = false;
		// Just start the JFrame with dummy values.
		MyFrame.putValuesRepaint(not_Started, not_Started, not_Started, whichEpc, epcNum, weight);
		if(loadOnceEpcService() > 0) {
			System.out.println("Both epc's thread dispatched. Not sure that any reader(s) is/are connected.");
			
			
			if(getEpcService()) {
				System.out.println("Got one epc number: Epc:"+epcNum);
				System.out.println("Start validating Epc Number:"+epcNum);
				while (!isEpcNumberValid(epcNum) ){
					System.out.println("Epc number "+epcNum+" is Not Valid. Back to getEpcService()...");
					getEpcService();
				}
				
			/* ------------------------------------------------------------------------------- */	
				System.out.println("Trigger PLC for v1 as true.");
				// -- Start Code Step 1-- PLC Trigger 1.
					
					try {
						Thread.sleep(5000); // statically taking 20 seconds.
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				
				// -- End Code Step 1-- PLC Trigger 1.
				
				/**
				  * At this point, PLC is triggered with the intimation that Open the boom-barriers. Green light. 
				  * And give us a signal to read the weight (after button press by Driver).
				  *  
				  */
				
				// -- Start Code Step 2 -- Call the url & wait until PLC puts a signal for us to start taking weight..
					
				// -- End Code Step 2 -- Call the url & wait until PLC puts a signal for us to start taking weight.
				
				/** 
				  * At this point, got the intimation from PLC to take weight.
				  * 
				  */
					
			    // -- Start Code Step 3 -- Take Weight.
					
/*				System.out.println("Load weight service.");		
				if ( loadOnceWeightService() ) {
						System.out.println("Get the output of weight service. Only if the weight service is connected.");
						getWeightService();
				}
*/					
				// -- End Code Step 3 -- Take Weight.
						
				/**
				 * At this point, Weight is taken. The task is now to apply validations .
				 *  
				 */

				// -- Start Code Step 4 -- Apply validations.
					
					
				// -- End Code Step 4 -- Apply validations.
				
				/**
				 * At this point, validations are applied, trigger the PLC to intimate that transaction completes. Go ahead.
				 * PLC should open the front boom barriers, green light to front, After truck goes, make the lights red , close boom barrier. 
				 *  
				 */

				// -- Start Code Step 5 -- Trigger PLC to indicate, Weight is taken.. Transaction complete, Go ahead.
						
						
				// -- End Code Step 5 -- Trigger PLC to indicate, Weight is taken.. Transaction complete, Go ahead.
				
				/**
				 * At this point, validations are applied, trigger the PLC to intimate that transaction completes. Go ahead.
				 * PLC should open the front boom barriers, green light to front, After truck goes, make the lights red , close boom barrier. 
				 *  
				 */

				// -- Start Code Step 6 -- Call the url & wait until PLC puts a signal for us to start recycling everything.
				
				
				
				// -- End Code Step 6 -- Call the url & wait until PLC puts a signal for us to start recycling everything.
				
				/**
				 * At this point, PLC is indicating that recycle each and everything. Means Truck is out now.
				 * 
				 */
				
				// -- Start Code Step 7 -- GET INPUT FROM PLC THAT everything is done- So restart the cycle, refreshes all values and start reading rfid again.
				
					System.out.println("Reseting everything for a fresh call.....");
					init();
					
					// close
					while(thread_passiveRead_One.disConnectReader()) {
						System.out.println("closing...");
					}
					thread_passiveRead_Two.disConnectReader();
					
					// nullify the objects.
					thread_passiveRead_One = null;
					thread_passiveRead_Two = null;
					
					System.out.println("Done with refreshing. Continue....");
					callService();
					
					
				// -- End Code Step 7 -- GET INPUT FROM PLC THAT everything is done- So restart the cycle, refreshes all values and start reading rfid again.
				
				
					//				if(!weight_Service_Called) {
					//					loadOnceWeightService();
					//					weight_Service_Called = true;
					//				}
				
			}
			
		} else{
			System.out.println("No rfid reader is getting connected.");
		}
		
		
		
	}
	
	/*public static void recallService() {
		epcNum = "-- empty --";
		weight = "-- empty --";
		MyFrame.weight_freezed = false;
		PassiveRead.is_epc_Number_Selected = false;
		PassiveRead.epc_Number_Selected = epcNum;
		MyFrame.putValuesRepaint(epcNum, weight);
		
		// Try after every 1 second to get the epc number again.
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		JOptionPane.showMessageDialog(new JOptionPane(), "Page Refreshed !!");
		
		loadOnceEpcService();
		getEpcService();
		if(!weight_Service_Called) 
		{	
			loadOnceWeightService();
			weight_Service_Called = true;
		}
		getWeightService();
		
	}*/
	
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
		return WeighingMachineSimpleRead.loadOnceWeightService();
	}
	
	public static boolean getEpcService() {
		System.out.println("In getEpcService(): Start getting epc number.");
		// set epc number.
		while(true) {
			if(thread_passiveRead_One.connected || thread_passiveRead_Two.connected) {
				System.out.println("is sel:"+PassiveRead.is_epc_Number_Selected+" & num:"+PassiveRead.epc_Number_Selected);
				if(PassiveRead.is_epc_Number_Selected) {
					if(PassiveRead.epc_Number_Selected != null && PassiveRead.epc_Number_Selected.length() > 0) {
						epcNum = PassiveRead.epc_Number_Selected;
						whichEpc = PassiveRead.readerUsed_epc_Number_Selected;
						
						if(weight_scale_STATUS)
							MyFrame.putValuesRepaint(started_stat, started_stat, started_stat, whichEpc, epcNum, weight);
						else 
							MyFrame.putValuesRepaint(started_stat, started_stat, not_Started, whichEpc, epcNum, weight);
						
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
	
	public static void getWeightService() {
		while(true) {
			if(MyFrame.weight_freezed) {
				JOptionPane.showMessageDialog(new JOptionPane(), "Weight Freezed !!!!");
				break;
			}
			// set weight
			if(WeighingMachineSimpleRead.weight_Calculated != null &&
					WeighingMachineSimpleRead.weight_Calculated.length() > 0 )
			 		//&& weight != WeighingMachineSimpleRead.weight_Calculated)
			{
				weight = WeighingMachineSimpleRead.weight_Calculated;
				MyFrame.putValuesRepaint(started_stat, started_stat, started_stat, whichEpc, epcNum, weight);
				//JOptionPane.showMessageDialog(new JOptionPane(), "Weight :"+weight+"  epc:"+epcNum);
			}
			//weight += 1;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Weight :"+weight+"  epc:"+epcNum);
	    }
	}
	
	/**
	 * This method basically checks for the enteries in table 'DispatchActivity', incase it contains the entry with the 
	 * same epc number only then it is allowed.
	 * 
	 * @true, if DispatchActivity has one record of the same Epc number.
	 * otherwise false.
	 * 
	 * Soon: It will return the object of DispatchActivity.
	 * 
	 */
	public static boolean isEpcNumberValid(String epcNum) {
		// Call database to get dispatch activity entry for this epcNum.
		
		// If got one, return the same object. 
		
		return true;
	}
	
}
