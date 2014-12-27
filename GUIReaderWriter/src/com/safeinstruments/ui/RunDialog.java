package com.safeinstruments.ui;

import javax.swing.JOptionPane;

import com.weighbridge.WeighingMachineSimpleRead;

/**
 * @author gurpreet
 */

public class RunDialog {
	public static String epcNum = "-- empty --";
	public static String weight = "-- empty --";
	public static boolean weight_Service_Called = false;
	public static void main(String[] args) {
		callService();
	}
	
	public static void callService(){
		epcNum = "-- empty --";
		weight = "-- empty --";
		MyFrame.weight_freezed = false;
		MyFrame.putValuesRepaint(epcNum, weight);
		loadOnceEpcService();
		getEpcService();
		if(!weight_Service_Called) 
		{	
			loadOnceWeightService();
			weight_Service_Called = true;
		}
		getWeightService();
	}
	
	public static void recallService(){
		epcNum = "-- empty --";
		weight = "-- empty --";
		MyFrame.weight_freezed = false;
		PassiveRead.is_epc_Number_Selected = false;
		PassiveRead.epc_Number_Selected = epcNum;
		MyFrame.putValuesRepaint(epcNum, weight);
		
		/*// Try after every 1 second to get the epc number again.
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
		
		JOptionPane.showMessageDialog(new JOptionPane(), "Page Refreshed !!");
		
		loadOnceEpcService();
		getEpcService();
		if(!weight_Service_Called) 
		{	
			loadOnceWeightService();
			weight_Service_Called = true;
		}
		getWeightService();
		
	}
	
	public static void loadOnceEpcService() {
		System.out.println("loadOnceEpcService");
		// Call epc service only once.
		PassiveRead pr = new PassiveRead();
		if( pr.connectReader() ) {
			System.out.println("Reading starting");
			pr.readContinously();
		} else {
			System.out.println("Oops, not getting the connection.");
		}
	}
	
	public static void loadOnceWeightService() {
		// call weight service only once.
		if(WeighingMachineSimpleRead.init())
			System.out.println("Initialization is done. A thread is dispatched to do the task.");
		else
			System.out.println("Not done.");
	}
	
	public static void getEpcService() {
		System.out.println("getEpcService");
		// set epc
		while(true) {
			System.out.println("is sel:"+PassiveRead.is_epc_Number_Selected+" & num:"+PassiveRead.epc_Number_Selected);
			if(PassiveRead.is_epc_Number_Selected) {
				if(PassiveRead.epc_Number_Selected != null && PassiveRead.epc_Number_Selected.length() > 0){
					epcNum = PassiveRead.epc_Number_Selected;
					MyFrame.putValuesRepaint(epcNum, weight);
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
				MyFrame.putValuesRepaint(epcNum, weight);
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
