package com.mtss.plc;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.PropertyResourceBundle;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.mtss.logs.LogsMtss;

import mtss.Gui.Constants;
import mtss.PassiveReader.main.PassiveRead;
import mtss.PassiveReader.main.PassiveReaderPropertyConstants;

public class Requests {
	/* Get actual class name to be printed on */
	static Logger log = LogsMtss.getLogger(Requests.class.getName());
	
	public static PhantomAPI phApi = new PhantomAPI();
	public static String writeReq = "http://192.168.1.28/awp/MINE/writeVFour.htm?param=";
	public static String readReqUrl = "http://192.168.1.28/awp/MINE/index.htm";
	static {
		FileInputStream fis = null;
        java.util.ResourceBundle bundle = null;
	        // Read these parameters from Resource Bundle.
	        try {
	        	fis = new FileInputStream("C:/Mtss/Properties/PlcConfig.properties");
	        	//fis = new FileInputStream("/home/mtss/tools/PlcConfig.properties");
	    		
	        	bundle = new PropertyResourceBundle(fis);
	        	if(bundle.getString( "write_url" ) != null && bundle.getString( "write_url" ).length() > 1) {
	        		writeReq = bundle.getString( "write_url" );
	        	}
	        	if(bundle.getString( "read_url" ) != null && bundle.getString( "read_url" ).length() > 1) {
		        	readReqUrl = bundle.getString( "read_url" );
	        	}
	        } catch(Exception ee) {
	        	ee.printStackTrace();
	        	log.debug("There is some problem in loading the parameters fromm PlcConfig.properties file.");
	        } finally {
	        	try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	bundle = null;
	        }
	}
	
	public static String readRequest() {
		log.debug("Inside read request !!!");
		String res = null;
		// invoke the PLC code here.
		try {
			phApi = getInstance();
			log.debug("Got instance !!"+phApi);
			// Read Request
			res = phApi.makeReadRequest(readReqUrl);
			//log.debug("Value of ");
			/*String onlyValuesStr = sou;
			log.debug(sou);
			if (sou.lastIndexOf("<body>") != 0) {
				onlyValuesStr = sou.substring(sou.lastIndexOf("<body>") + 6, sou.lastIndexOf("</body>"));
			}
			String spr[] = onlyValuesStr.split("&");
			for (String p : spr) {
				String ard[] = p.split("@");
				if (ard[0].startsWith("amp;"))
					ard[0] = ard[0].substring(4);
				log.debug(ard[0] + ":" + ard[1]);
			}*/
		} catch (Exception e) {
			//e.printStackTrace();
			log.debug("---Requests.readRequest()----- PHANTOM EXCEPTION ----------  ");
		}
		return res;
	}

	public static boolean writeRequest(String param, String value) {
		boolean res = true;
		// invoke the PLC code here.
		try {
			// Write Request
			res = phApi.makeWriteRequest(phApi.getWriteUrl(writeReq, param, value));
			
		} catch (Exception e) {
			//e.printStackTrace();
			log.debug("MAKE WRITE REQUEST ---- PHANTOM -- EXCEPTION  ------");
		}

		return res;
	}
	
	public static PhantomAPI getInstance() {
		if(phApi == null ){
			phApi = new PhantomAPI();
		}
		return phApi;
	}
}