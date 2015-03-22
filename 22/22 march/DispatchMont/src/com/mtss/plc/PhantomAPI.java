package com.mtss.plc;

import java.util.concurrent.TimeUnit;

import mtss.PassiveReader.main.PassiveRead;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.mtss.logs.LogsMtss;

public class PhantomAPI {
	/* Get actual class name to be printed on */
	static Logger log = LogsMtss.getLogger(PhantomAPI.class.getName()); //Logger.getLogger(PhantomAPI.class.getName());
	
	static DesiredCapabilities caps = null;
	static WebDriver driver = null;
	PhantomAPI() {
		if(caps==null && driver==null) {
			caps = new DesiredCapabilities();
			caps.setJavascriptEnabled(true); // not really needed: JS enabled by default
			caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "C://ph/phantomjs.exe");
			driver = new PhantomJSDriver(caps);
		}
	}
	
	public boolean makeWriteRequest(String s) {
		try {
			log.debug(s);
			driver.get(s);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // If wait needed, then un-comment this.
		} catch(Exception e) {
			//e.printStackTrace();
			log.debug("-------------------  PHANTOM EXCEPTION  --------------------");
			return false;
		}
		return true;
	}
	
	public String makeReadRequest(String readReqUrl) {
		log.debug("Inside makeReadRequest() : PahntomApi");
		//driver.get("http://192.168.1.28/awp/MINE/index.htm");
		try {
			driver.get(readReqUrl);
		} catch(Exception e){
			log.debug("---------PHANTOM EXCEPTION--- PahntomAPI.MakeReadRequest() -------");
		}
		String sou = driver.getPageSource();
		if( null == sou && sou.length()<1 ) {
			log.debug("Error ... So wait for 4 seconds... And try again ...");
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
				//e.printStackTrace();
				log.debug("---------PHANTOM EXCEPTION--- PahntomAPI.MakeReadRequest() -------");
			}
			makeReadRequest(readReqUrl);
		}
		
		//log.debug("Sou:"+sou);
		String onlyValuesStr = sou.substring(sou.lastIndexOf("<body>")+6, sou.lastIndexOf("</body>"));
		log.debug("onlyValuesStr:"+onlyValuesStr);
		/*String spr[] = onlyValuesStr.split("&");
		log.debug("spr:"+spr);
		for(String p : spr) {
			String ard[] = p.split("@");
			if( ard[0].startsWith("amp;") )
				ard[0] = ard[0].substring(4);
			log.debug(ard[0] + ":" + ard[1]);
			if(param.equals(ard[0])) {
				return ard[1];
			}
		}*/
		return onlyValuesStr;
	}
	
	public String getWriteUrl(String writeReq, String parameter, String value) {
		String finalUrl = writeReq + parameter + "@" + value;
		return finalUrl;
	}
	
	public void closeAll() {
		driver.close();
		driver.quit();
	}
	
	/*
	public static void main(String[] args) {
		String writeReq = "http://192.168.1.28/awp/MINE/writeVFour.htm?param=";
		String parameter = "v4";
		String value = "4.0";
		String finalUrl = writeReq + parameter + "@" + value;
		
		PhantomAPI phApi = new PhantomAPI();
		//phApi.getRequest(finalUrl);
		
		log.debug("Done. Parameter:"+parameter+" updated with value:"+value);
		driver.get("http://192.168.1.28/awp/MINE/index.htm");
		String sou = driver.getPageSource();
		String onlyValuesStr = sou.substring(sou.lastIndexOf("<body>")+6, sou.lastIndexOf("</body>"));
		String spr[] = onlyValuesStr.split("&");
		for(String p : spr) {
			String ard[] = p.split("@");
			if(ard[0].startsWith("amp;"))
				ard[0] = ard[0].substring(4);
			log.debug(ard[0] + ":" + ard[1]);
		}
		
		phApi.closeAll();
	}
	*/
	
}