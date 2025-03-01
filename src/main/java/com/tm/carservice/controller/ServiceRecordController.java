package com.tm.carservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tm.carservice.service.ServiceRecordService;

@Controller
@RequestMapping("/servicerecord")
/*@Controller: Indicates that this class is a Spring MVC controller.
@RequestMapping("/servicerecord"): Maps all requests starting with /servicerecord to this controller.*/
public class ServiceRecordController {

	@Autowired// Injects the `ServiceRecordService` bean into the controller.
 
	private ServiceRecordService srs;
	
	@PostMapping("/generatebill")
	public ModelAndView generatebill(@RequestParam int vehicleid,@RequestParam String serviceItems,@RequestParam String quantities,@RequestParam int employeeid){
		srs.generateBillOfMaterials(vehicleid,serviceItems,quantities,employeeid);
		ModelAndView mv = new ModelAndView("redirect:/employee/backtodashboard");
		return mv;
	}
}
/*@PostMapping("/generatebill"): Maps the /generatebill URL to this method.
generatebill(): Handles the generation of the bill of materials for a service record. It takes the vehicle ID, service items, quantities, and employee ID as parameters, calls the generateBillOfMaterials method from the ServiceRecordService, and redirects to the employee dashboard.*/
