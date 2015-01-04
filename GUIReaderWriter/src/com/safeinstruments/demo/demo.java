package com.safeinstruments.demo;

import com.safeinstruments.ui.MyFrame;
import com.safeinstruments.ui.PassiveRead;
import com.safeinstruments.ui.PassiveReaderPropertyConstants;

public class demo {
	public static String epcNum = "-- empty --";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Connect reader 1.
		PassiveRead thread_passiveRead_One = new PassiveRead(  PassiveReaderPropertyConstants.RFID_READER_DS_WB_22 );
		thread_passiveRead_One.start();
		
		// Connect reader 2.
		PassiveRead thread_passiveRead_Two = new PassiveRead(  PassiveReaderPropertyConstants.RFID_READER_DS_WB_24  );
		thread_passiveRead_Two.start();
		
		// Read Passively. 
		getEpcService();
		
	}
	
	public static void loadOnceEpcService( String rfidReaderToBeConnected ) {
		System.out.println("loadOnceEpcService");
		// Call epc service only once.
		PassiveRead pr = new PassiveRead( rfidReaderToBeConnected );
		if( pr.connectReader() ) {
			System.out.println("Reading starting");
			pr.readContinously();
		} else {
			System.out.println("Oops, not getting the connection.");
		}
	}
	
	/**
	 * This method simply makes a watch that if any EPC is selected by any of the Rfid attached.
	 * Then it gets the output and stop.
	 * 
	 * Note: The static variables 'is_epc_Number_Selected' & 'epc_Number_Selected' got value from any of
	 * the threads of PassiveRead.java. Whenever any thread puts a value of epc in these variables, the getEpcService
	 * method stops execution. 
	 * 
	 * The threads of PassiveRead.java will be stopped by PassiveRead.java itself.
	 * 
	 */
	public static void getEpcService() {
		System.out.println("getEpcService");
		// set epc
		while(true) {
			System.out.println("is sel:"+PassiveRead.is_epc_Number_Selected+" & num:"+PassiveRead.epc_Number_Selected);
			if(PassiveRead.is_epc_Number_Selected) {
				if(PassiveRead.epc_Number_Selected != null && PassiveRead.epc_Number_Selected.length() > 0){
					epcNum = PassiveRead.epc_Number_Selected;
					//MyFrame.putValuesRepaint(epcNum, weight);
					System.out.println("getEpcService: Epc Number:" + epcNum+ " : With reader used:"+PassiveRead.readerUsed_epc_Number_Selected);
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
	
	

}
