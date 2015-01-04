package com.safeinstruments.test;

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
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TooManyListenersException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class TestSimpleRead implements Runnable, SerialPortEventListener {
static CommPortIdentifier portId;
//static Enumeration portList;

InputStream inputStream;
SerialPort serialPort;
Thread readThread;

public static void main(String[] args) {
	//System.out.println("Entered in main method...");
    //portList = CommPortIdentifier.getPortIdentifiers();
    //System.out.println("Portlist... "+portList+ " . Has any element:"+portList.hasMoreElements());
    //while (portList.hasMoreElements()) {
		//portId = (CommPortIdentifier) portList.nextElement();
		try {
			portId = CommPortIdentifier.getPortIdentifier("COM2") ;
		} catch (NoSuchPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("port ids... "+portId);
        if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
             //if (portId.getName().equals("COM2")) {
            	 System.out.println("Got COM1 port...");   
            	 //                if (portId.getName().equals("/dev/term/a")) {
                TestSimpleRead reader = new TestSimpleRead();
            //}
        }
    //}
}

public TestSimpleRead() {
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
        serialPort.setSerialPortParams(2400,
            SerialPort.DATABITS_8,
            SerialPort.STOPBITS_1,
            SerialPort.PARITY_NONE);
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
            
            display(readBuffer);
            /*try {
        		System.out.println("got one input.. after input sleep called..");
                Thread.sleep(20000);
            } catch (InterruptedException e) {System.out.println(e);}*/
        } catch (IOException e) {System.out.println(e);}
       
        break;
    }
}
private static JFrame frame = new JFrame();
JLabel jlbl = new JLabel("", JLabel.CENTER);
private void display(byte[] buffer) {
	String str = new String(buffer);
	StringBuffer org = new StringBuffer();
	//System.out.print("broken: ");
	boolean addition = false;
	for(int i=0;i<str.length();i++)
	{
		//System.out.print(str.charAt(i));
		if((int)str.charAt(i)==3){
			// Ending char
			if(addition)
				break;
		}
		if(addition)
			org.append(str.charAt(i));
		
		if((int)str.charAt(i)==2){
			// starting char
			addition = true;
		}
	}
	//System.out.println();
	//System.out.println("full: "+str);
	if(org.length() > 0)
	{	
		System.out.println("org: "+org);
		frame.setTitle("Application will close in seconds.");
        jlbl.setText(String.valueOf(org));
		jlbl.setBackground(Color.red);
		jlbl.setFont(new Font("Serif", Font.BOLD, 100));
        frame.add(jlbl);
        frame.setSize(400, 400);
        frame.setVisible(true);
       
	}
	/*else
		System.out.println("Incorrect data");*/
}

}