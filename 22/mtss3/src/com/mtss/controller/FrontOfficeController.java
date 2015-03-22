package com.mtss.controller;
	
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mtss.bean.EPC;
import com.mtss.bean.GPS;
import com.mtss.services.FrontOfficeUpdations;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
	
@Controller
public class FrontOfficeController {
	
	static Log log = LogFactory.getLog(FrontOfficeController.class.getName());
	
	/**
	 * This method will check that d.o number is valid or not.
	 * @return
	 */
	
	@RequestMapping(value = "/EPCNumber", method = RequestMethod.GET)
	@ResponseBody
	public String getEPCNumber() {
		log.info("*****FrontOfficeController:getEPCNumber() ***** ");

		System.out.println("Started the UDP session");
		BufferedReader inFromUser =   new BufferedReader(new InputStreamReader(System.in));
		String epc = null;
		try{
				log.info("*****FrontOfficeController:getEPCNumber() Before Reading *****");
			    DatagramSocket clientSocket = new DatagramSocket();
				InetAddress IPAddress = InetAddress.getByName("192.168.1.106");			
		        byte[] sendData = new byte[1024];
		        byte[] receiveData = new byte[1024];
		        sendData = "GET".getBytes();
		        //System.out.println("before reading");
		        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 5000);
		        clientSocket.send(sendPacket);
		        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		        clientSocket.receive(receivePacket);
		        String modifiedSentence = new String(receivePacket.getData());
		        String a = modifiedSentence;
		        String success = a.substring(a.indexOf("<success>")+9, a.indexOf("</success>") );
		        //System.out.println(success);
		        if(null != success && success.equalsIgnoreCase("true")){
		        	epc = a.substring(a.indexOf("<epc>")+5, a.indexOf("</epc>") );
		        	log.info("*****FrontOfficeController:(getEPCNumber) Success. Epc Taken:***** "+epc);
		        	log.info("*****FrontOfficeController:(getEPCNumber) Success. Epc Taken:***** "+epc.length());
		        } else {
		        	log.info("*****FrontOfficeController:(getEPCNumber) Searching Failed *****");
		        }
		        // System.out.println("FROM SERVER:" + modifiedSentence);
		        clientSocket.close();
		        clientSocket=  null;
		}
		catch(Exception e)
		{
			log.info("*****FrontOfficeController:getEPCNumber() Exception in loadConfigurations()***** "+e);
			e.printStackTrace();
		}
		return epc;
	}
	
	
	@RequestMapping(value="/epcAvailStatus",method=RequestMethod.GET)
	@ResponseBody
	public String epcNoStatus(@RequestParam String epcNo){
		log.info("*****FrontOfficeController(epcNoStatus) : Check EPCNo Status***** "+epcNo);
		if(new FrontOfficeUpdations().epcAvailability(epcNo))
			return "true";
		else 
			return "false";
	}
	
	@RequestMapping(value = "/backToHomeFrontOffice", method = RequestMethod.GET)
	public String backToHome() {
		log.info("*****FrontOfficeController:backToHome() ***** ");
		return "operator_user";
	}
	
	@RequestMapping(value = "/inReq", method = RequestMethod.GET)
	public String InRequest() {
		log.info("*****FrontOfficeController:inRequest() ***** ");
		return "front_stepOne";
	}
	
	@RequestMapping(value = "/frontIn", method = RequestMethod.GET)
	public String frontInRequest(@RequestParam String MaterialChooser,HttpServletRequest request) {
		String TypeChooser = request.getParameter("TypeChooser");
		String otherValue = request.getParameter("otherValue");
		log.info("*****FrontOfficeController:frontInRequest() ***** Material Chooser: "+MaterialChooser+" TypeChooser: "+TypeChooser+" Other Type: "+otherValue);
		request.setAttribute("requestType",TypeChooser);
		request.setAttribute("transName",MaterialChooser);
		request.setAttribute("transType",TypeChooser);
		if(otherValue!=null && otherValue.length()>0){
			log.info("*****FrontOfficeController:frontInRequest() ***** Material Chooser: "+MaterialChooser+" Other Type: "+otherValue+" Not Required DoNo *****");
			String trans = "2:"+otherValue+":";
			request.setAttribute("transaction",trans);
			return "front_in_two";
		}
		return "front_in_one";
	}
	
	@RequestMapping(value="/doNoAvailForIntern",method=RequestMethod.GET)
	@ResponseBody
	public String doNoAvailable(@RequestParam String doNo){
		log.info("*****FrontOfficeController(doNoAvailable) : Check User Avalilability of Do_No***** "+doNo);
		if(new FrontOfficeUpdations().doNoAvailability(doNo))
			return "true";
		else 
			return "false";
	}
	
	@RequestMapping(value = "/frontInOne", method = RequestMethod.GET)
	public String frontInOneRequest(@RequestParam String transaction,HttpServletRequest request) {
		log.info("*****FrontOfficeController:frontInOneRequest() ***** ");
		log.info("*****FrontOfficeController:frontInOneRequest(): Transaction Detail ***** "+transaction);
		request.setAttribute("transaction", transaction);
		return "front_in_two";
	}
	
	@RequestMapping(value = "/frontInTwo", method = RequestMethod.GET)
	public String frontInTwoRequest(@RequestParam String transaction,HttpServletRequest request) {
		log.info("*****FrontOfficeController:frontInTwoRequest() ***** ");
		request.setAttribute("transaction", transaction);
		List<GPS> imeiNumber = new FrontOfficeUpdations().getIMEIList();
		request.getSession().setAttribute("imeiNoList",imeiNumber);
		log.info("*****FrontOfficeController:frontInTwoRequest() Transaction Detail***** "+transaction);
		
		String isOutRequest = request.getParameter("frontOfOut");
		log.info("*****FrontOfficeController:frontInTwoRequest(): Front Office Is Out Request?***** "+isOutRequest);
		
		String trans[] = transaction.split(":");
		
		//Request Type IN and Material Is COAL and Transfer Type INTERNAL
		if(trans.length>0){
			if(trans[0].equals("2")){
				request.setAttribute("showRLW",false);
				return "front_in_four";
			}else {
				return "front_in_three";
			}
		}
		
		//Front Office Out Request
		else if(isOutRequest.equalsIgnoreCase("frontOfOut")) {
			log.info("*****FrontOfficeController:frontInTwoRequest():Front_Office_Out_Request ***** ");
			FrontOfficeUpdations fou = new FrontOfficeUpdations();
			String epcNo = request.getParameter("epcNumber");
			EPC epcDetail = fou.epcDetail(epcNo);
			return "";
		}
		
		//Request Type IN and Material Is COAL and Transfer Type EXTERNAL
		else {
			return "front_in_three";
		}
	}
	
	@RequestMapping(value = "/frontInThree", method = RequestMethod.GET)
	public String frontInThreeRequest(@RequestParam String imeiNumber,@RequestParam String transaction,HttpServletRequest request) {
		log.info("*****FrontOfficeController:frontInThreeRequest() Selected IMEI Number ***** "+imeiNumber);
		request.setAttribute("transaction", transaction);
		log.info("*****FrontOfficeController:frontInThreeRequest() Transaction detail ***** "+transaction);
		request.setAttribute("showRLW",true);
		return "front_in_four";
	}
	
	@RequestMapping(value = "/frontInFour", method = RequestMethod.GET)
	public String frontInFiveRequest(@RequestParam String vechNumber,@RequestParam String transaction,HttpServletRequest request) {
		String rlw = request.getParameter("rlw");
		log.info("*****FrontOfficeController:frontInFourRequest() ***** ");
		log.info("*****FrontOfficeController:frontInFourRequest() Vehicle No "+vechNumber+"  RLW :"+rlw+" *****");
		request.setAttribute("transaction", transaction);
		log.info("*****FrontOfficeController:frontInFourRequest() Transaction Detail ***** "+transaction);
		String trans[] = transaction.split(":");
		FrontOfficeUpdations fo = new FrontOfficeUpdations();
		log.info("*****FrontOfficeController:frontInFourRequest() Transactial Detail Size ***** "+trans.length);
		if(trans.length>0 && trans.length>2){
			if(trans[0].equals("1") && trans[1].equals("1")){
				boolean result = fo.updateDispatchExternal(trans[2],trans[3],trans[4],vechNumber,rlw);
				if(result){
					request.setAttribute("transMessage", "Transaction Completed");
				}
				else
				{
					request.setAttribute("transMessage", "Transaction Uncompleted");
				}
			}
			else if(trans[0].equals("1") && trans[1].equals("2")){
				boolean result = fo.updateDispatchInternal(trans[2],trans[3],trans[4],vechNumber,rlw);
				if(result){
					request.setAttribute("transMessage", "Transaction Completed");
				}
				else
				{
					request.setAttribute("transMessage", "Transaction Uncompleted");
				}
			}
			else if(trans[0].equals("2")){
				boolean result = fo.updateForOtherGoods(trans[1],trans[2], vechNumber);
				if(result){
					request.setAttribute("transMessage", "Transaction Completed");
				}
				else
				{
					request.setAttribute("transMessage", "Transaction Uncompleted");
				}
			}
		}
		return "operator_user";
	}
	
	@RequestMapping(value = "/internCoalTransDoNo", method = RequestMethod.GET)
	@ResponseBody
	public String internCoalTransDoNo(@RequestParam int doNo) {
		log.info("*****FrontOfficeController:internCoalTransDoNo() Do_No***** "+doNo);
		return "front_in_five";
	}
	
	@RequestMapping(value = "/outReq", method = RequestMethod.GET)
	public String outRequest(HttpServletRequest request) {
		log.info("*****FrontOfficeController:outRequest() ***** ");
		request.setAttribute("frontOfOut","frontOfOut");
		return "front_in_two";
	}
}	
