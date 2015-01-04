package com.safeinstruments.ui;

import javax.swing.JOptionPane;

import com.weighbridge.WeighingMachineSimpleRead;

/**
 * @author Gurpreet.singh
 * 
 */

public class RunDialog {
	public static String epcNum = "-- empty --";
	public static String weight = "-- empty --";
	
	public static boolean rfid_24_STATUS = false;
	public static boolean rfid_22_STATUS = false;
	public static boolean weight_scale_STATUS = false;
	
	public static String not_Started = "-- Not Started --";
	public static String started_stat = "Ok Started.";
	
	public static String whichEpc = "--";
	public static boolean weight_Service_Called = false;
	public static void main(String[] args) {
		callService();
	}
	
	public static void callService() {
		epcNum = "-- empty --";
		weight = "-- empty --";
		
		MyFrame.weight_freezed = false;
		// Just start the JFrame with dummy values.
		MyFrame.putValuesRepaint(not_Started, not_Started, not_Started, whichEpc, epcNum, weight);
		if(loadOnceEpcService() > 0) {
			System.out.println("Start getting epc number as either 1 or all readers are connected.");
			getEpcService();
					
					//				if(!weight_Service_Called) {
					//					loadOnceWeightService();
					//					weight_Service_Called = true;
					//				}
			
			System.out.println("Load weight service.");
			if ( loadOnceWeightService() ) {
				System.out.println("Get the output of weight service. Only if the weight service is connected.");
				getWeightService();
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
			PassiveRead thread_passiveRead_One = new PassiveRead(  PassiveReaderPropertyConstants.RFID_READER_DS_WB_22 );
			thread_passiveRead_One.start();
			i++;
			
			// Connect reader 2.
			PassiveRead thread_passiveRead_Two = new PassiveRead(  PassiveReaderPropertyConstants.RFID_READER_DS_WB_24  );
			thread_passiveRead_Two.start();
			i++;
			
			return i;
			
		} catch(Exception e){
			e.printStackTrace();
			return i;
		}
			
	}
	
	public static boolean loadOnceWeightService() {
		// call weight service only once.
		if(WeighingMachineSimpleRead.init()){
			System.out.println("Initialization is done. A thread is dispatched to do the task.");
			return true;
		}
		else {
			System.out.println("Not done.");
			return false;
		}
	}
	
	public static void getEpcService() {
		System.out.println("getEpcService");
		// set epc
		while(true) {
			System.out.println("is sel:"+PassiveRead.is_epc_Number_Selected+" & num:"+PassiveRead.epc_Number_Selected);
			if(PassiveRead.is_epc_Number_Selected) {
				if(PassiveRead.epc_Number_Selected != null && PassiveRead.epc_Number_Selected.length() > 0){
					epcNum = PassiveRead.epc_Number_Selected;
					whichEpc = PassiveRead.readerUsed_epc_Number_Selected;
					
					if(weight_scale_STATUS)
						MyFrame.putValuesRepaint(started_stat, started_stat, started_stat, whichEpc, epcNum, weight);
					else 
						MyFrame.putValuesRepaint(started_stat, started_stat, not_Started, whichEpc, epcNum, weight);
					
					break;
				}
			}
			// Try after every 1 second to get the epc number again.
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
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
				MyFrame.putValuesRepaint(not_Started, not_Started, not_Started, whichEpc, epcNum, weight);
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
}
