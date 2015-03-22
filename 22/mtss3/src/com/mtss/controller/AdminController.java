package com.mtss.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mtss.bean.EPC;
import com.mtss.bean.GPS;
import com.mtss.bean.ProductionVehicle;
import com.mtss.bean.User;
import com.mtss.services.AdminUpdations;

@Controller
public class AdminController {
	static Log log = LogFactory.getLog(AdminController.class.getName());
	
	@RequestMapping(value="/createUser",method=RequestMethod.GET)
	public String createNewUser(){
		
		log.info("*****AdminController(createNewUser) :Request for Create New User***** ");
		return "create_new_user";
	}
	
	@RequestMapping(value="/addNewUser")
	public String addNewUser(@ModelAttribute("user") User user,HttpServletRequest request){
		log.info("*****AdminController(addNewUser) : Add New User***** "+user.getUserName());
		if(new AdminUpdations().addNewUser(user)){
			log.info("*****AdminController(createNewUser) :Successfully User Is Create***** ");
			addUserListInSession(request);
			return "user_details";
		}
		else{
			log.info("*****AdminController(createNewUser) :User Is Not Created***** ");
			ModelAndView mad =  new ModelAndView();
			mad.addObject("userMessage", "Unable To Create New User:");
			return "create_new_user";
		}
	}
	
	
	@RequestMapping(value="/userAvail",method=RequestMethod.GET)
	@ResponseBody
	public String userAvailable(@RequestParam String userName){
		log.info("*****AdminController(userAvailable) : Check User Avalilability of User's Name***** "+userName);
		if(new AdminUpdations().userAvailability(userName))
			return "true";
		else 
			return "false";
	}
	
	@RequestMapping(value="/viewUserList",method=RequestMethod.GET)
	public String viewUserList(HttpServletRequest request){
		log.info("*****AdminController(viewUserList) : View User List***** ");
		addUserListInSession(request);
     	return "user_details";
	}
	
	@RequestMapping(value="/editUser",method=RequestMethod.GET)
	public String editUserAccount(@RequestParam String userName,HttpServletRequest request){
		log.info("*****AdminController(editUserAccount) : edit User Account***** "+userName);
		User user = new User();
		user.setUserName(userName);
		request.getSession().setAttribute("currentUname",user);
	    return "edit_user";
	}
	
	@RequestMapping(value="/removeUser",method=RequestMethod.GET)
	public String removeUserAccount(@RequestParam String userName,HttpServletRequest request){
		log.info("*****AdminController(removeUserAccount) : Remove User Account***** "+userName);
		new AdminUpdations().removeUser(userName);
	    addUserListInSession(request);
	    return "user_details";
	}
	
	@RequestMapping(value="/changePasswordRequest",method=RequestMethod.GET)
	public String changePasswordRequest(){
		log.info("*****AdminController(changePasswordRequest) : Change Password Request***** ");
		return "change_password";
	}
	
	@RequestMapping(value="/changePassword",method=RequestMethod.POST)
	public String changePassword(@ModelAttribute("user") User user,HttpServletRequest request){
		log.info("*****AdminController(changePassword) : Change Existing User's Password ***** ");
		new AdminUpdations().changeUserPassword(user);
		return "user_details";
	}
	
	@RequestMapping(value="/getPasswordRequest",method=RequestMethod.GET)
	public String getPasswordRequest(){
		log.info("*****AdminController(getPasswordRequest) : Get Password Request***** ");
		return "get_user_password";
	}
	
	@RequestMapping(value="/getPassword",method=RequestMethod.POST)
	public String getPassword(@ModelAttribute("user") User user,HttpServletRequest request){
		log.info("*****AdminController(getPassword) : Get Password Request***** ");
		User userInfo = new AdminUpdations().getUserPassword(user);
		request.getSession().setAttribute("userInfo",userInfo);
		return "existing_user_detail";
	}
	
	@RequestMapping(value="/blockEPCNoRequest",method=RequestMethod.GET)
	public String blockEPCNoRequest(){
		log.info("*****AdminController(blockEPCNoRequest) : Block EPC NO Request***** ");
		return "block_epc_no";
	}
	
	@RequestMapping(value="/blockEPCNo",method=RequestMethod.POST)
	public String blockEPCNo(@ModelAttribute("epc") EPC epc,HttpServletRequest request){
		log.info("*****AdminController(blockEPCNo) : Block EPC NO ***** ");
		if(new AdminUpdations().blockEPCNumber(epc))
			return "epc_details";
		else
			return "error_page";
	}
	
	@RequestMapping(value="/enterEPCNoRequest",method=RequestMethod.GET)
	public String enterEPCNoRequest(){
		log.info("*****AdminController(enterEPCNoRequest) : Enter EPC NO Request***** ");
		return "enter_epc_no";
	}
	
	@RequestMapping(value="/epcAvail",method=RequestMethod.GET)
	@ResponseBody
	public String epcNoAvailable(@RequestParam String epcNo){
		log.info("*****AdminController(epcNoAvailable) : Check EPCNo Avalilability***** "+epcNo);
		if(new AdminUpdations().epcAvailability(epcNo))
			return "true";
		else 
			return "false";
	}
	
	@RequestMapping(value="/enterEPCNo",method=RequestMethod.POST)
	public String enterEPCNo(@ModelAttribute("epc") EPC epc,HttpServletRequest request){
		String status ="";
		log.info("*****AdminController(enterEPCNo) : Enter EPC Number***** ");
		if(new AdminUpdations().enterEPCNumber(epc))
			status = "epc_details";
		else
			status = "error_page";
		
		List<EPC> epcList = new AdminUpdations().getEPCList();
		request.getSession().setAttribute("epcList", epcList);	
		log.info("*****AdminController(enterEPCNo) :  EPC Number List Size***** "+epcList.size());
		
		return status;
	}
	
	@RequestMapping(value="/lostEPCRequest",method=RequestMethod.GET)
	public String lostEPCNoRequest(){
		log.info("*****AdminController(lostEPCNoRequest) : Lost EPC No ***** ");
		return "lost_epc_no";
	}
	
	@RequestMapping(value="/lostEPC",method=RequestMethod.POST)
	public String lostEPCNo(@ModelAttribute("prodVeh") ProductionVehicle proVeh,HttpServletRequest request){
		log.info("*****AdminController(lostEPCNo) : Lost EPC Tag's Vehicle Number***** "+proVeh.getVehicleNo());
		if(new AdminUpdations().lostEPCNo(proVeh.getVehicleNo()))
			return "admin_user";
		else
			return "error_page";
	}
	
	@RequestMapping(value="/deleteEPC",method=RequestMethod.GET)
	public String deleteEPC(@RequestParam Integer epcNo,HttpServletRequest request){
		log.info("*****AdminController(deleteEPC) : Remove EPC Number***** "+epcNo);
		new AdminUpdations().deleteEPCNo(epcNo);
		List<EPC> epcList = new AdminUpdations().getEPCList();
		request.getSession().setAttribute("epcList", epcList);
	    return "epc_details";
	}
	
	@RequestMapping(value="/getEPCDetail",method=RequestMethod.GET)
	public String getEPCDetail(HttpServletRequest request){
		log.info("*****AdminController(getEPCDetial) : Get EPC Information***** ");
		List<EPC> epcList = new AdminUpdations().getEPCList();
		request.getSession().setAttribute("epcList", epcList);
	    return "epc_details";
	}
	
	@RequestMapping(value="/enterGPSNoRequest",method=RequestMethod.GET)
	public String enterGPSNoRequest(){
		log.info("*****AdminController(enterGPSNoRequest) : Enter GPS NO Request***** ");
		return "enter_gps_no";
	}
	
	@RequestMapping(value="/gpsAvail",method=RequestMethod.GET)
	@ResponseBody
	public String gpsNoAvailable(@RequestParam String gpsNo){
		log.info("*****AdminController(gpsNoAvailable) : Check gpsNo Avalilability***** "+gpsNo);
		if(new AdminUpdations().gpsAvailability(gpsNo))
			return "true";
		else 
			return "false";
	}
	
	@RequestMapping(value="/enterGPSNo",method=RequestMethod.POST)
	public String enterGPSNo(@ModelAttribute("gps") GPS gps,HttpServletRequest request){
		String status = "";
		log.info("*****AdminController(enterGPSNo) : Enter GPS Number***** ");
		
		if(new AdminUpdations().enterGPSNumber(gps))
			status =  "gps_details";
		else
			status = "error_page";
		
		List<GPS> gpsList = new AdminUpdations().getGPSList();
		request.getSession().setAttribute("gpsList", gpsList);
		
		return status;
	}
	
	@RequestMapping(value="/deleteGPS",method=RequestMethod.GET)
	public String deleteGPS(@RequestParam Integer gpsNo,HttpServletRequest request){
		log.info("*****AdminController(deleteGPS) : Remove GPS Number***** "+gpsNo);
		new AdminUpdations().deleteGPSNo(gpsNo);
		List<GPS> gpsList = new AdminUpdations().getGPSList();
		request.getSession().setAttribute("gpsList", gpsList);
	    return "gps_details";
	}
	
	@RequestMapping(value="/getGPSDetail",method=RequestMethod.GET)
	public String getGPSDetail(HttpServletRequest request){
		log.info("*****AdminController(getGPSDetail) : Get GPS Information***** ");
		List<GPS> gpsList = new AdminUpdations().getGPSList();
		request.getSession().setAttribute("gpsList", gpsList);
	    return "gps_details";
	}
	
	@RequestMapping(value="/lostGPSRequest",method=RequestMethod.GET)
	public String lostGPSNoRequest(){
		log.info("*****AdminController(lostGPSNoRequest) : Lost GPS No ***** ");
		return "lost_gps_no";
	}
	
	@RequestMapping(value="/lostGPS",method=RequestMethod.POST)
	public String lostGPSNo(@ModelAttribute("prodVeh") ProductionVehicle proVeh,HttpServletRequest request){
		log.info("*****AdminController(lostGPSNo) : Lost GPS's Vehicle Number***** "+proVeh.getVehicleNo());
		if(new AdminUpdations().lostGPSNo(proVeh.getVehicleNo()))
			return "admin_user";
		else
			return "error_page";
	}
	
	@RequestMapping(value="/enterPermaVehRequest",method=RequestMethod.GET)
	public String enterPermaVeh(){
		log.info("*****AdminController(enterPermaVeh) : Enter Permanent Vehicle Detail Request***** ");
		return "perma_epc_gps_detail";
	}
	
	@RequestMapping(value="/enterPermaVeh",method=RequestMethod.POST)
	public String enterPermaVeh(@ModelAttribute("prodVeh") ProductionVehicle proVeh,HttpServletRequest request){
		log.info("*****AdminController(enterPermaVeh) : Enter Product Vehicle Detail***** ");
		if(new AdminUpdations().enterPerEPCGPCNo(proVeh))
			return "admin_user";
		else
			return "error_page";
	}
	
	@RequestMapping(value="/vehicleAvail",method=RequestMethod.GET)
	@ResponseBody
	public String vehicleAvailable(@RequestParam String vehNo){
		log.info("*****AdminController(vehicleAvailable) : Check Vehicle_No Avalilability***** "+vehNo);
		if(new AdminUpdations().vehAvailability(vehNo))
			return "true";
		else 
			return "false";
	}
	
	@RequestMapping(value="/proVehicleAvail",method=RequestMethod.GET)
	@ResponseBody
	public String proVehicleAvailable(@RequestParam String vehNo){
		log.info("*****AdminController(proVehicleAvailable) : Check Production Vehicle_No Avalilability***** "+vehNo);
		if(new AdminUpdations().proVehAvailability(vehNo))
			return "true";
		else 
			return "false";
	}
			
	private void addUserListInSession(HttpServletRequest request){
		log.info("*****AdminController(addUserListInSession) : Set User In Session***** ");
		HttpSession session = request.getSession();
		List<User> userList = new AdminUpdations().getUserDetails();
		session.setAttribute("userList", userList);
	}
}
