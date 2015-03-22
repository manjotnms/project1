//package com.mtss.plc;
//
//import mtss.Gui.Constants;
//
//public class InvokeUrls {
//	
//	public static PhantomAPI phApi = new PhantomAPI();
//	public final static String writeReq = "http://192.168.1.28/awp/MINE/writeVFour.htm?param=";
//	public final static String readReqUrl = "http://192.168.1.28/awp/MINE/index.htm";
//	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		String parameter = "v4";
//		String value = "4.0";
//		
//		// Write Request
//				phApi.makeWriteRequest( phApi.getWriteUrl(writeReq, parameter, value) );
//		
//		// Read Request
//		String sou = phApi.makeReadRequest(readReqUrl, Constants.READ_PARAM_FROM_PLC_RESTART_TRANSACTION);
//		String onlyValuesStr = sou;
//		if(sou.lastIndexOf("<body>") != 0)
//			onlyValuesStr = sou.substring(sou.lastIndexOf("<body>")+6, sou.lastIndexOf("</body>"));
//		String spr[] = onlyValuesStr.split("&");
//		for(String p : spr) {
//			String ard[] = p.split("@");
//			if(ard[0].startsWith("amp;"))
//				ard[0] = ard[0].substring(4);
//			log.debug(ard[0] + ":" + ard[1]);
//		}
//		
//		// Close All.
//		phApi.closeAll();
//		
//	}
//	
//}
