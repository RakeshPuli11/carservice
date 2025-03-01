package com.tm.carservice.controller;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.tm.carservice.model.MaterialsPrice;
import com.tm.carservice.model.Employee;
import com.tm.carservice.model.ServiceRecord;
import com.tm.carservice.model.Vehicle;
import com.tm.carservice.repo.MaterialsPriceRepo;
import com.tm.carservice.repo.ServiceRecordRepo;
import com.tm.carservice.repo.VehicleRepo;
import com.tm.carservice.service.MaterialsPriceService;
import com.tm.carservice.service.EmployeeService;
import com.tm.carservice.service.VehicleService;
/*@Controller: Indicates that this class is a Spring MVC controller.
@RequestMapping("/vehicle"): Maps all requests starting with /vehicle to this controller.*/

@Controller
@RequestMapping("/vehicle")
public class VehicleController {
	
	@Autowired
	EmployeeService es;
	
	@Autowired
	ServiceRecordRepo srr;

	@Autowired
	VehicleService vs;
	
	@Autowired
	MaterialsPriceRepo mrp;
	
	@Autowired
	MaterialsPriceService mps;
	
	@Autowired
	VehicleRepo vr;
	
	//add new vehicle form
	/*@GetMapping("/vehicleEntry"): Maps the /vehicleEntry URL to this method.
addnewvehicle(): Returns the addvehicle view with a new Vehicle object and the materials price list.*/
	@GetMapping("/vehicleEntry")
	public ModelAndView addnewvehicle() {
		List<MaterialsPrice> pricelist = mrp.findAll();
		ModelAndView mv = new ModelAndView("/vehicle/addvehicle","vehicle", new Vehicle());
		mv.addObject("pricelist", pricelist);
		return mv;
	}
	
	//Register New Vehicle
	/*@PostMapping("/registervehicle"): Maps the /registervehicle URL to this method.
registervehicle(): Registers a new vehicle, calculates the estimated cost, and redirects to the admin dashboard.*/
	@PostMapping("/registervehicle")
	public ModelAndView registervehicle(@ModelAttribute("vehicle") Vehicle vehicle) {		
		
	
		ModelAndView mv = new ModelAndView("redirect:/admin/admindashboard");
		
		String servicetype = vehicle.getServicetype();
		if(servicetype.equals("OTHERS")) {
			servicetype = "LABOUR FEE";
		}
		
		double serviceprice = mps.getMaterialsPriceByitem(servicetype).getPrice();
		double issue1 = mps.getMaterialsPriceByitem(vehicle.getIssuefirst()).getPrice();
		double issue2 = mps.getMaterialsPriceByitem(vehicle.getIssuesecond()).getPrice();
		double issue3 = mps.getMaterialsPriceByitem(vehicle.getIssuethird()).getPrice();
		
		double cost = serviceprice+issue1+issue2+issue3;
		vehicle.setEstimated_cost(cost);
		vs.addvehicle(vehicle);
		
		return mv;
		
	}
	
	//Update Vehicle Status Form
	/*@GetMapping("/updateVehiclestatus"): Maps the /updateVehiclestatus URL to this method.
	updateVehiclestatus(): Fetches the vehicle details by ID and returns the updateVehicle view with the vehicle data.*/
	@GetMapping("/updateVehiclestatus")
	public ModelAndView updateVehiclestatus(@RequestParam("id") int id) {
		
		Vehicle v=vs.getVehicleById(id);
		ModelAndView mv=new ModelAndView("/vehicle/updateVehicle","vehicle", v);
		System.out.println(v);
	
		return mv;
	}
	
	
	//Update Vehicle Information
	/*@PostMapping("/updateVehicleinfo"): Maps the /updateVehicleinfo URL to this method.
postUpdateVehicledetails(): Updates the vehicle details and returns the successupdate view.*/
	@PostMapping("/updateVehicleinfo")
	public ModelAndView postUpdateVehicledetails(@RequestParam int id, @RequestParam String name,@RequestParam String contact,@RequestParam String registrationNumber) {
		
		Vehicle v = vs.getVehicleById(id);
		v.setCustomerName(name);
		v.setCustomerContact(contact);
		v.setRegistrationNumber(registrationNumber);
		vs.addvehicle(v);

		ModelAndView mv = new ModelAndView("/vehicle/successupdate");
		return mv;
	}

	
	//Check Vehicle Issue
	/*@GetMapping("/checkvehicleissue"): Maps the /checkvehicleissue URL to this method.
checkvehicleissue(): Fetches the vehicle details by ID and returns the individualvehicle view with the vehicle data.*/
	@GetMapping("/checkvehicleissue")
	public ModelAndView checkvehicleissue(@RequestParam("id") int id) {

		Vehicle v = vs.getVehicleById(id);
		ModelAndView mv = new ModelAndView("/vehicle/individualvehicle","vehicle",v);		
		return mv;
	}
	
	//Assign Vehicle to Employee
	/*@GetMapping("/assignvehicle"): Maps the /assignvehicle URL to this method.
assignvehicle(): Fetches the available employees and vehicle details, and returns the assignVehicle view.*/
	@GetMapping("/assignvehicle")
	public ModelAndView assignvehicle(@RequestParam("id") int id) {
	    List<Employee> availableEmployees = es.getEmployeesByAvailability("FREE");
	    availableEmployees.forEach(employee -> System.out.println(employee.getName() + " - " + employee.getAvailablity()));
	    Vehicle v = vs.getVehicleById(id);
	    ModelAndView mv = new ModelAndView("vehicle/assignVehicle");
	    mv.addObject("vehicle", v);
	    mv.addObject("availableEmployees", availableEmployees);
	    return mv;
	}
/*@PostMapping("/assignvehicle"): Maps the /assignvehicle URL to this method.
assignVehicleToEmployee(): Assigns the vehicle to an employee and redirects to the vehicle list.*/
	@PostMapping("/assignvehicle")
	public ModelAndView assignVehicleToEmployee(@RequestParam("vehicleId") int vehicleId, @RequestParam("employeeId") int employeeId) {
	    Employee selectedEmployee = es.getemployeebyId(employeeId);
	    Vehicle v = vs.getVehicleById(vehicleId);
	    if (!v.getAssigned().equals("ASSIGNED")) {
	        es.assignvehicle(selectedEmployee, v);
	    } else {
	        System.out.println("UNDER SERVICING");
	    }
	    return new ModelAndView("redirect:/admin/vehiclelist");
	}
	
	//Print Bill of Materials
	/*@GetMapping("/printbom"): Maps the /printbom URL to this method.
printbom(): Fetches the vehicle and service record details, prepares the bill of materials, and returns the bill view.*/
	@GetMapping("/printbom")
	public ModelAndView printbom(@RequestParam int id) {
		Vehicle v=vs.getVehicleById(id);
		ServiceRecord sr = srr.getByVehicle( v);
		
		String[] materials = sr.getBillOfMaterials().toString().split(",");
		List<String[]> list1 = new ArrayList<>();
		list1.add(new String[] {v.getServicetype(),"1"});
		for(String s : materials) {
		 String[] str = s.split(":");
		 list1.add(str);

		}
			
		ModelAndView mv = new ModelAndView("/vehicle/bill");
		mv.addObject("list1",list1);
		mv.addObject("cost", sr.getActualcost());
		return mv;
	}
	
}
