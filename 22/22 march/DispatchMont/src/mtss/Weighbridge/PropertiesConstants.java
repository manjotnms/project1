package mtss.Weighbridge;

/**
 * This is the file contains the constants whose values are in properties files.
 * 
 * Note: If anyone changes the properties file's key names, then this file also must be updated.
 * @author gurpreet
 *
 */
public class PropertiesConstants {
	
	public static final String machine1_constants = "m1_";
	public static final String machine2_constants = "m2_";
	
	public static final String medium_ethernet = "ethernet";
	public static final String medium_comm = "comm";
	
	public static final String prop_selected_machine = "selected_Machine";
	public static final String prop_selected_medium = "selected_Medium";
	
	public static final String prop_Starting_char = "inputData_config_Starting_char";
	public static final String prop_Ending_char = "inputData_config_Ending_char";
	
	// Ethernet port configurations.
	public static final String prop_ethernet_ip = "weightMachine_ip";
	public static final String prop_ethernet_port = "weightMachine_port";
	
	// host property constants.
	public static final String HOST_IP = "host_ip";
	public static final String HOST_PORT = "host_port";
	
	// Comm port configurations
	public static final String prop_port_config_Port = "port_config_Port";
	public static final String prop_port_config_Baud_rate = "port_config_Baud_rate";
	public static final String prop_port_config_Databits = "port_config_Databits";
	public static final String prop_port_config_Stopbits = "port_config_Stopbits";
	public static final String prop_port_config_SerialPort = "port_config_SerialPort";

}
