package com.weighbridge;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.awt.Color;
import java.awt.Font;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.TooManyListenersException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class WeighingMachineSimpleRead implements Runnable, SerialPortEventListener {
static CommPortIdentifier portId;
//static Enumeration portList;
private static int port_config_Starting_char = -1;
private static int port_config_Ending_char = -1;

// One weight to be taken.
public static String weight_Calculated = "0";

// Machine config
private static String machine_selected=null;

// Port configuration
static private String port_config_Port = null;
private static int port_config_Baud_rate = -1;
private static int port_config_Databits = -1;
private static int port_config_Stopbits = -1;
private static int port_config_SerialPort = -1;

InputStream inputStream;
SerialPort serialPort;
Thread readThread;

public static boolean init(){
	
	// Call load configuration. If return true then go further.
	if(loadConfigurations())
	{
		// Check if any port configuration is not happening then say bad connection parameters error.
	    if(port_config_Baud_rate == -1 || port_config_Databits == -1 || port_config_Stopbits == -1 || 
	    	port_config_SerialPort == -1 || port_config_Port == null || port_config_Starting_char == -1
	    	|| port_config_Ending_char == -1 || machine_selected == null) {
	    	System.out.println("Some parameters are not coming from properties files.");
	    	return false;
	    }
	    
	    // Load the rxtx dll files.
	    /*try {
	    	//System.setProperty("java.library.path", "D://dll_files_for_rxtx");
	    	System.setProperty("java.library.path", "./lib");
	    	//System.setProperty( "java.library.path", "/path/to/libs" );
			Field fieldSysPath = ClassLoader.class.getDeclaredField( "sys_paths" );
			fieldSysPath.setAccessible( true );
			fieldSysPath.set( null, null );
	    } catch(Exception e){
	    	e.printStackTrace();
	    	System.out.println("init(): Rxtx dll files have not be loaded.");
	    	return false;
	    }
	    */
	    
		// Configure port.
		try {
			// System.out.println("value is "+port_config_Port);
			portId = CommPortIdentifier.getPortIdentifier(port_config_Port);
		} catch (NoSuchPortException e) {
			e.printStackTrace();
			System.out.println("init(): No port is selected. Either port is not specified/configured or not loading.");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("init(): No port is selected. Either port is not specified/configured or not loading.");
			return false;
		}catch (Throwable e) {
			e.printStackTrace();
			System.out.println("init(): No port is selected. Either port is not specified/configured or not loading.");
			return false;
		}
		
		System.out.println("port ids... "+portId);
	    if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
	        	 System.out.println("Got "+port_config_Port+" port...");   
	        	 //                if (portId.getName().equals("/dev/term/a")) {
	             WeighingMachineSimpleRead reader = new WeighingMachineSimpleRead();
	    }
	    
	    System.out.println("All parameters are done. Succesfully configured. ");
	    return true;
	} else {
	    System.out.println("Denied: There is some problem in loadConfigurations");
	    return false;
	}
}

public static boolean loadConfigurations(){
	try {
		// Read values from config file to load the machine.
	    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("com.weighbridge/WeighMachineConfig");
	    //System.out.println("value is :"+bundle.getString(PropertiesConstants.prop_selected_machine)+"_");
	    // First read which machine is selected.
	    if(bundle.getString(PropertiesConstants.prop_selected_machine) != null && 
	    		bundle.getString(PropertiesConstants.prop_selected_machine).length() > 1)
	    	machine_selected = bundle.getString(PropertiesConstants.prop_selected_machine)+"_";
	    
	    if(machine_selected != null && machine_selected.length() > 0) {
	    	System.out.println("Machine selected is :"+machine_selected);
		    /* Now load basic configurations. */
	    	if(bundle.getString(machine_selected+PropertiesConstants.prop_port_config_Port) != null && 
		    		bundle.getString(machine_selected+PropertiesConstants.prop_port_config_Port).length() > 0)
	    		port_config_Port = bundle.getString(machine_selected+PropertiesConstants.prop_port_config_Port);
	    	
	    	if(bundle.getString(machine_selected+PropertiesConstants.prop_port_config_Baud_rate) != null && 
		    		bundle.getString(machine_selected+PropertiesConstants.prop_port_config_Baud_rate).length() > 0)
		    	port_config_Baud_rate = Integer.parseInt(bundle.getString(machine_selected+PropertiesConstants.prop_port_config_Baud_rate));
		    
		    if(bundle.getString(machine_selected+PropertiesConstants.prop_port_config_Databits) != null && 
		    		bundle.getString(machine_selected+PropertiesConstants.prop_port_config_Databits).length() > 0)
		    	port_config_Databits = Integer.parseInt(bundle.getString(machine_selected+PropertiesConstants.prop_port_config_Databits));
		    
		    if(bundle.getString(machine_selected+PropertiesConstants.prop_port_config_Stopbits) != null && 
		    		bundle.getString(machine_selected+PropertiesConstants.prop_port_config_Stopbits).length() > 0)
		        port_config_Stopbits = Integer.parseInt(bundle.getString(machine_selected+PropertiesConstants.prop_port_config_Stopbits));
		    
		    if(bundle.getString(machine_selected+PropertiesConstants.prop_port_config_SerialPort) != null && 
		    		bundle.getString(machine_selected+PropertiesConstants.prop_port_config_SerialPort).length() > 0)
		        port_config_SerialPort = Integer.parseInt(bundle.getString(machine_selected+PropertiesConstants.prop_port_config_SerialPort));
		    
		    System.out.println("Port configs loaded:"+port_config_Port+","+port_config_Baud_rate+","+port_config_Databits+","
		    		+port_config_Stopbits+","+port_config_SerialPort);
		    
		    // loading starting and ending chars depending on machines.
		    if(bundle.getString(machine_selected+PropertiesConstants.prop_Starting_char) != null && 
		    		bundle.getString(machine_selected+PropertiesConstants.prop_Starting_char).length() > 0)
		        port_config_Starting_char = Integer.parseInt(bundle.getString(machine_selected+PropertiesConstants.prop_Starting_char));
		    
		    if(bundle.getString(machine_selected+PropertiesConstants.prop_Ending_char) != null && 
		    		bundle.getString(machine_selected+PropertiesConstants.prop_Ending_char).length() > 0)
		    	port_config_Ending_char = Integer.parseInt(bundle.getString(machine_selected+PropertiesConstants.prop_Ending_char));
		    
		    
	    } else {
	    	System.out.println("loadConfigurations(): No machine is selected. Please select some machine.");
	    	return false;
	    }
	} catch (Exception e){
		System.out.println("Exception in loadConfigurations()");
		e.printStackTrace();
		return false;
	}
    return true;
}


public static void main(String[] args) {
	if(init())
		System.out.println("Initialization is done. A thread is dispatched to do the task.");
	else
		System.out.println("Not done.");
}

public WeighingMachineSimpleRead() {
	System.out.println("In simple read cons...");
    try {
        serialPort = (SerialPort) portId.open("SimpleReadApp", 2000);
    } catch (PortInUseException e) {System.out.println(e);}
    try {
        inputStream = serialPort.getInputStream();
    } catch (IOException e) {System.out.println(e);}
	try {
	        serialPort.addEventListener(this);
	} catch (TooManyListenersException e) {System.out.println(e);}
    serialPort.notifyOnDataAvailable(true);
    try {
        serialPort.setSerialPortParams(port_config_Baud_rate, 
        		port_config_Databits, port_config_Stopbits, port_config_SerialPort);
    } catch (UnsupportedCommOperationException e) {System.out.println(e);}
    readThread = new Thread(this);
    readThread.start();
}

public void run() {
	try {
		//System.out.println("initial sleep called..");
        Thread.sleep(2000);
        //System.out.println("inital sleep ended..");
    } catch (InterruptedException e) {System.out.println(e);}
}

public void serialEvent(SerialPortEvent event) {
	//System.out.println("serial event called...");
    switch(event.getEventType()) {
    case SerialPortEvent.BI:
    case SerialPortEvent.OE:
    case SerialPortEvent.FE:
    case SerialPortEvent.PE:
    case SerialPortEvent.CD:
    case SerialPortEvent.CTS:
    case SerialPortEvent.DSR:
    case SerialPortEvent.RI:
    case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
        break;
    case SerialPortEvent.DATA_AVAILABLE:
        byte[] readBuffer = new byte[20];
        DataInputStream di = new DataInputStream(inputStream);
        try {
            while (di.available() > 0) {
                di.readFully(readBuffer, 0, 20);
                //System.out.println(read);
            }
            System.out.println(readBuffer);
            display(readBuffer);
            /*try {
        		System.out.println("got one input.. after input sleep called..");
                Thread.sleep(20000);
            } catch (InterruptedException e) {System.out.println(e);}*/
        } catch (IOException e) {System.out.println(e);}
       
        break;
    }
}
/*private static JFrame frame = new JFrame();
JLabel jlbl = new JLabel("", JLabel.CENTER);*/
private void display(byte[] buffer) {
	String str = new String(buffer);
	StringBuffer org = new StringBuffer();
	//System.out.print("broken: ");
	boolean addition = false;
	
	for(int i=0;i<str.length();i++)
	{
		//System.out.print(str.charAt(i));
		if((int)str.charAt(i) == port_config_Ending_char){
			// Ending char
			if(addition)
				break;
		}
		if(addition)
			org.append(str.charAt(i));
		
		if((int)str.charAt(i) == port_config_Starting_char)
			// Starting char
			addition = true;
	}
	//System.out.println();
	//System.out.println("full: "+str);
	
	
		if(org.length() > 0)
		{	
			System.out.println("org: "+org);
			weight_Calculated = String.valueOf(org);
			/*frame.setTitle("Rfid Weigh Scale using machine +"+machine_selected);
			jlbl.setText(String.valueOf(org));
			jlbl.setBackground(Color.red);
			jlbl.setFont(new Font("Serif", Font.BOLD, 100));
	        frame.add(jlbl);
	        frame.setSize(800, 400);
	        frame.setVisible(true);*/
	        
			//previous_value = Long.parseLong(String.valueOf(org));
		}
		else
			System.out.println("Incorrect data");
}

}