package mtss.Weighbridge;

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
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.TooManyListenersException;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

public class WeighingMachineSimpleRead implements Runnable, SerialPortEventListener {
	 /* Get actual class name to be printed on */
	 static Logger log = Logger.getLogger(WeighingMachineSimpleRead.class.getName());
	 static CommPortIdentifier portId;

// Ethernet usage variables.
static Socket client = null;
DataOutputStream out = null;
InputStream inFromServer = null;

//static Enumeration portList;
private static int port_config_Starting_char = -1;
private static int port_config_Ending_char = -1;

// One weight to be taken.
public static String weight_Calculated = "0";

// Machine config
private static String machine_selected=null;
private static String medium_selected = null;

// Ethernet port configuration
private static String ethernet_ip=null;
private static String ethernet_port=null;

// Comm Port configuration
static private String port_config_Port = null;
private static int port_config_Baud_rate = -1;
private static int port_config_Databits = -1;
private static int port_config_Stopbits = -1;
private static int port_config_SerialPort = -1;

// Comm usage variables.
InputStream inputStream;
SerialPort serialPort;
Thread readThread;

public static boolean Net_loadConfigurations() {
	try {
		// Read values from config file to load the machine.
	    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("com.weighbridge/WeighMachineConfig");
	    //System.out.println("value is :"+bundle.getString(PropertiesConstants.prop_selected_machine)+"_");
	    // First read which machine is selected.
	    if(bundle.getString(PropertiesConstants.prop_selected_machine) != null && 
	    		bundle.getString(PropertiesConstants.prop_selected_machine).length() > 1)
	    	machine_selected = bundle.getString(PropertiesConstants.prop_selected_machine)+"_";
	    	
	    if(machine_selected != null && machine_selected.length() > 0) {
	    	System.out.println("Machine selected is :"+machine_selected+" & "+"Medium Selected. By default as ETHERNET if not COMM.");
				// Reading basic params.
		    	if(bundle.getString(machine_selected+PropertiesConstants.prop_ethernet_ip) != null && 
			    		bundle.getString(machine_selected+PropertiesConstants.prop_ethernet_ip).length() > 0)
			    	ethernet_ip = bundle.getString(machine_selected+PropertiesConstants.prop_ethernet_ip);
		    	
		    	if(bundle.getString(machine_selected+PropertiesConstants.prop_ethernet_port) != null && 
			    		bundle.getString(machine_selected+PropertiesConstants.prop_ethernet_port).length() > 0)
		    		ethernet_port = bundle.getString(machine_selected+PropertiesConstants.prop_ethernet_port);
		    	
		    	System.out.println("Ip is:"+ethernet_ip+" & Port:"+ethernet_port);
		    	
		        // loading starting and ending chars depending on machines.
			    if(bundle.getString(machine_selected+PropertiesConstants.prop_Starting_char) != null && 
			    		bundle.getString(machine_selected+PropertiesConstants.prop_Starting_char).length() > 0)
			        port_config_Starting_char = Integer.parseInt(bundle.getString(machine_selected+PropertiesConstants.prop_Starting_char));
			    
			    if(bundle.getString(machine_selected+PropertiesConstants.prop_Ending_char) != null && 
			    		bundle.getString(machine_selected+PropertiesConstants.prop_Ending_char).length() > 0)
			    	port_config_Ending_char = Integer.parseInt(bundle.getString(machine_selected+PropertiesConstants.prop_Ending_char));
			    
			    System.out.println(ethernet_ip);
			    System.out.println(ethernet_port);
			    System.out.println(machine_selected);
			    System.out.println(medium_selected);
			    System.out.println(port_config_Starting_char);
			    System.out.println(port_config_Ending_char);
			    
				// Check if any port configuration is not happening then say bad connection parameters error.
				if(ethernet_ip== null || ethernet_port==null || machine_selected == null || medium_selected == null || 
						port_config_Starting_char == -1 || port_config_Ending_char == -1 ) {
					 System.out.println("Some parameters are not coming from properties files.");
				     return false;
				}
				
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

public static boolean Net_init() {	
	// Call load configuration. If return true then go further.
	if(Net_loadConfigurations())
	{
		// Configure ethernet port.
				try {
					client = new Socket(ethernet_ip, Integer.parseInt(ethernet_port));
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					System.out.println("Just connected to "+ client.getRemoteSocketAddress());	        
		// call the reader.
		WeighingMachineSimpleRead reader = new WeighingMachineSimpleRead();
		
	    //System.out.println("All parameters are done. Succesfully configured. ");
	    return true;
	} else {
	    System.out.println("Denied: There is some problem in loadConfigurations");
	    return false;
	}
}

public void Net_startThread() {
	Net_startReading();
	readThread = new Thread(this);
    readThread.start();
}

public void Net_startReading() {
	//System.out.println("In Net_startReading....");
	        OutputStream outToServer;
			try {
				outToServer = client.getOutputStream();
				out= new DataOutputStream(outToServer);
		        out.writeUTF("Hello from " + client.getLocalSocketAddress());
		        inFromServer = client.getInputStream();
		        DataInputStream di = new DataInputStream(inFromServer);
		        // System.out.println("Server says " + in.readUTF());
		        // byte readBuffer[] = di.readUTF().getBytes(Charset.forName("UTF-8"));
		        byte readBuffer[] = new byte[20];
		        //while (di.available() > 0) {
	                di.readFully(readBuffer, 0, 20);
	            // }
	            display(readBuffer);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
}

public static boolean init(){	
	// Call load configuration. If return true then go further.
	if(loadConfigurations())
	{
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
	    	
	    	medium_selected = bundle.getString(PropertiesConstants.prop_selected_medium)+"_";
	    	System.out.println("Machine selected is :"+machine_selected+" & "+"Medium Selected is:"+medium_selected);
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
				    
				    
				    
				 // Check if any port configuration is not happening then say bad connection parameters error.
				    if(port_config_Baud_rate == -1 || port_config_Databits == -1 || port_config_Stopbits == -1 || 
				    	port_config_SerialPort == -1 || port_config_Port == null || port_config_Starting_char == -1
				    	|| port_config_Ending_char == -1 || machine_selected == null || medium_selected == null) {
				    	System.out.println("Some parameters are not coming from properties files.");
				    	return false;
				    }
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

public void startReading() {
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

public static boolean loadOnceWeightService() {
	boolean isStarted = false;
	java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("com.weighbridge/WeighMachineConfig");
	medium_selected = bundle.getString(PropertiesConstants.prop_selected_medium)+"_";
	System.out.println("Medium Selected is:"+medium_selected);
	if(medium_selected.equals(PropertiesConstants.medium_comm))	// Comm port is chosen.
		isStarted = WeighingMachineSimpleRead.init();
	else // supposing that ethernet is needed.
		isStarted = WeighingMachineSimpleRead.Net_init();
	
	return isStarted;
}


public static void main(String[] args) {
	java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("com.weighbridge/WeighMachineConfig");
	medium_selected = bundle.getString(PropertiesConstants.prop_selected_medium)+"_";
	System.out.println("Medium Selected is:"+medium_selected);
	
	if(medium_selected.equals(PropertiesConstants.medium_comm)) {
		// Comm port is chosen.
		init();
		/*if(init())
			System.out.println("Initialization is done. A thread is dispatched to do the task.");
		else
			System.out.println("Not done.");*/
	} else{
		// supposing that ethernet is needed.
		Net_init();
		/*if(Net_init())
			System.out.println("Initialization is done. A thread is dispatched to do the task.");
		else
			System.out.println("Not done.");*/
	}
}

public WeighingMachineSimpleRead() {
	if(machine_selected.equals(PropertiesConstants.medium_comm)){
		// Calling for Comm.
		startReading();
	} else {
		// Supposing that ethernet is selected.
		Net_startThread();
	}
}

public void run() {
	try {
		//System.out.println("initial sleep called..");
		if(!machine_selected.equals(PropertiesConstants.medium_comm)){
			while(true) {
				//Thread.sleep(1000); // Sleep ok's.
	        	// Supposing that Ethernet is selected.
	    		Net_startReading();
	        }
		} else {
			Thread.sleep(2000);
		}
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