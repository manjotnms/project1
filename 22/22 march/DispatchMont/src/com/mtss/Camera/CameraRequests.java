package com.mtss.Camera;

import java.io.FileInputStream;
import java.util.PropertyResourceBundle;

public class CameraRequests {
	public static String url = "";
	public static FileInputStream fis = null;
	public static java.util.ResourceBundle bundle = null;
    
	static {
		// Read these parameters from Resource Bundle.
        try {
        	fis = new FileInputStream("C:/Mtss/Properties/CameraConfig.properties");
        	//fis = new FileInputStream("/home/mtss/tools/CameraConfig.properties");
    		
    	    bundle = new PropertyResourceBundle(fis);
    	    
    	    url = bundle.getString( "url" ); //"192.168.0.200";	//Host IP
    	    
        } catch(Exception ee) {
        	ee.printStackTrace();
        }
	}
	
	public static String getSnapShot( String name ) {
		
		
		return "";
	}
}
