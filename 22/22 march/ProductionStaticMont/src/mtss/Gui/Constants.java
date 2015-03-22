package mtss.Gui;

/**
 * This class contains all the constants used in mtss.Gui package.
 * @author gurpreet
 *
 */

public class Constants {
	
	// Params in PLC
	public final static String WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_IN = "v1";
	//public final static String READ_PARAM_FROM_PLC_TAKE_WEIGHT = "v2";
	public final static String WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_OUT_WEIGTH_VALUE = "wt";
	public final static String WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_OUT_STATUS = "v3";
	public final static String READ_PARAM_BUTTON = "v2"; // "button"
	//public final static String WRITE_PARAM_BUTTON_RESPOSE = "v5";
	
	/*
	 * value = 1 means Ok.
	 * value = 2 means Overload. if more than RLW.
	 * value = 3 means Underweight.
	 */
	public final static String WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_OUT_STATUS_VALUE_Ok = "1";
	public final static String WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_OUT_STATUS_VALUE_Overload = "2";
	public final static String WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_OUT_STATUS_VALUE_ExceedsOrderQuantity = "3";
	
	//public final static String WRITE_PARAM_FROM_PLC_WEIGHT = "v4";
	public final static String READ_PARAM_FROM_PLC_RESTART_TRANSACTION = "v4";
	
	// Values means for variables.
	public final static String VALUE_WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_IN_RFID_22 = "1";
	public final static String VALUE_WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_IN_RFID_24 = "2";
	
	public final static String VALUE_WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_OUT_RFID_22 = "1";
	public final static String VALUE_WRITE_PARAM_TRIGGER_PLC_LET_TRUCK_OUT_RFID_24 = "2";
	
	//public final static String VALUE_READ_PARAM_FROM_PLC_RESTART_TRANSACTION = "1";
	
	public final static String VALUE_0 = "0";
	public final static String VALUE_1 = "1";
	public final static String VALUE_2 = "2";
	public final static String VALUE_3 = "3";
	
	// statuses
	public final static String LET_TRUCK_IN = "Triggering PLC for let the truck in.";
	public final static String LET_TRUCK_OUT = "PLC triggered for let the truck out.";
	
	public final static String EPC_NOT_VALID = "Epc number is not valid !!";
	public final static String EPC_VALID = "Epc number is valid !!";
	
	public final static String GREEN_BUTTON_PRESSED = "Green button pressed !!";
	public final static String RED_BUTTON_PRESSED = "Red button pressed !!";
	
	public final static Integer MIN_WEIGHT = 100;
	
}