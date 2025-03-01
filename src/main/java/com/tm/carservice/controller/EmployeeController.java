package com.tm.carservice.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.carservice.model.MaterialsPrice;
import com.tm.carservice.model.Employee;
import com.tm.carservice.model.Vehicle;
import com.tm.carservice.repo.MaterialsPriceRepo;
import com.tm.carservice.repo.VehicleRepo;
import com.tm.carservice.service.EmployeeService;
import com.tm.carservice.service.VehicleService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/employee")
//@Controller: Indicates that this class is a Spring MVC controller.
//@RequestMapping("/employee"): Maps all requests starting with /employee to this controller.
public class EmployeeController {
//@Autowired: Injects the required service and repository beans into the controller.
	@Autowired
	private EmployeeService es;
	
	@Autowired
	private VehicleService vs;
	
	@Autowired
	MaterialsPriceRepo mrp;
	
	@Autowired
	VehicleRepo vr;
	
	/*@GetMapping("/addemployeeform"): Maps the /addemployeeform URL to this method.
	addnewemployee(): Returns the addemployee view with a new Employee object and the 
	maximum date of birth (18 years ago).*/
	@GetMapping("/addemployeeform")
	public ModelAndView addnewemployee() {
		LocalDate maxDOB = LocalDate.now().minusYears(18);
		ModelAndView mv = new ModelAndView("/employee/addemployee","employee", new Employee());
		mv.addObject("maxDOB", maxDOB);
		return mv;
	}
	//Register New Employee
	/*@PostMapping("/registeremployee"): Maps the /registeremployee URL to this method.
registeremployee(): Registers a new employee and redirects to the admin dashboard.*/
	@PostMapping("/registeremployee")
	public ModelAndView registervehicle(@ModelAttribute("employee") Employee employee) {
		
		es.addemployee(employee);
		ModelAndView mv = new ModelAndView("redirect:/admin/admindashboard");
		return mv;
	}
	//Employee Dashboard
	/*@GetMapping("/employeedashboard"): Maps the /employeedashboard URL to this method.
goto_employee_dashboard(): Fetches the employee details, service history, and materials price 
list, and returns the employeedashboard view with the data.*/
	@GetMapping("/employeedashboard")
	public ModelAndView goto_employee_dashboard(HttpSession hs, @ModelAttribute("vehiclehistory") List<Vehicle> vh) {
		System.out.println(vh);
		
		ModelAndView mv = new ModelAndView("/employee/employeedashboard");
		String email = (String) hs.getAttribute("email");
		System.out.println(email);
		Employee employee = es.getemployeebyusername(email);
		hs.setAttribute("id", employee.getId());
	    List<Vehicle> list = vs.findByemployeeId( employee.getId());
	    mv.addObject("servicehistory", list);
	    mv.addObject("employee", employee);
	     
	    List<MaterialsPrice> pricelist = mrp.findAll();
	    mv.addObject("pricelist", pricelist);
	    
	    mv.addObject("vehiclehistory", vh);
		return mv;
	}
	//Back to Dashboard
	/*@GetMapping("/backtodashboard"): Maps the /backtodashboard URL to this method.
backtodashboard(): Redirects to the employee dashboard with an empty vehicle history list.*/
	@GetMapping("/backtodashboard")
	public ModelAndView backtodashboard(RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView("redirect:/employee/employeedashboard");
		redirectAttributes.addFlashAttribute("vehiclehistory", new ArrayList<Vehicle>());
		return mv;
	}
	
	//Check Vehicle Issue
	/*@GetMapping("/checkvehicleissue"): Maps the /checkvehicleissue URL to this method.
checkvehicleissue(): Fetches the vehicle details by ID and returns the vehicleissuedescription
 view with the vehicle data.*/
	@GetMapping("/checkvehicleissue")
	public ModelAndView checkvehicleissue(@RequestParam("id") int id) {
//		System.out.println(id);
		Vehicle v = vs.getVehicleById(id);
		ModelAndView mv = new ModelAndView("/employee/vehicleissuedescription","vehicle",v);		
		return mv;
	}
	//Check Vehicle History
	/*@GetMapping("/checkhistory"): Maps the /checkhistory URL to this method.
gethistory(): Fetches the vehicle history by registration number and redirects to the employee dashboard with the vehicle history data.*/
	@GetMapping("/checkhistory")
	public ModelAndView gethistory(@RequestParam String registrationNumber,RedirectAttributes redirectAttributes) {
		List<Vehicle> vh = vr.findByRegistrationNumber(registrationNumber);
		if(vh==null) {
				vh=new ArrayList<>();
		}
		System.out.println(vh);
		redirectAttributes.addFlashAttribute("vehiclehistory", vh);
		ModelAndView mv = new ModelAndView("redirect:/employee/employeedashboard");
		
//		mv.addObject("vehiclehistory", vh);
		return mv;
	}
	

}
