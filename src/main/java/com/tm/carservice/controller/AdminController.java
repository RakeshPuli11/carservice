package com.tm.carservice.controller;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.tm.carservice.model.Employee;
import com.tm.carservice.model.ServiceRecord;
import com.tm.carservice.model.Vehicle;
import com.tm.carservice.service.EmployeeService;
import com.tm.carservice.service.ServiceRecordService;
import com.tm.carservice.service.VehicleService;
import jakarta.servlet.http.HttpSession;
@Controller
@RequestMapping("/admin")
/*class is a Spring MVC controller.
@RequestMapping("/admin"): Maps all requests starting with /admin to
 this controller.*/
public class AdminController {
	//@Autowired: Injects the required service beans into the controller
	@Autowired
	VehicleService vs;
	@Autowired
	EmployeeService es;
	@Autowired
	ServiceRecordService srs;
	// vehicle dashboard controller
	/*@RequestMapping("/vehiclelist"): Maps the /vehiclelist URL to this method.
gotovehiclemanagementpage(): Fetches the list of vehicles 
and returns the vehicledashboard view with the vehicle list.*/
	@RequestMapping("/vehiclelist")
	public ModelAndView gotovehiclemanagementpage()
	{
		ModelAndView mv = new ModelAndView("/admin/vehicledashboard");
		List<Vehicle> list = vs.getVehiclelist();
		mv.addObject("vehiclelist", list);
		return mv;
	}
	
	
	
	
	
	
	//its used for the admin dashboard vehicle history details
	
	@RequestMapping("/vehiclehistory")
	public ModelAndView goto_vehicle_history(@RequestParam(value = "registrationNumber", required = false) String registrationNumber) {
	    ModelAndView mv = new ModelAndView("/admin/vehiclehistory");
	    List<Vehicle> vehicleHistory;
	    if (registrationNumber != null && !registrationNumber.isEmpty()) {
	        vehicleHistory = vs.findByRegistrationNumber(registrationNumber);
	    } else {
	        vehicleHistory = vs.getVehicleHistory();
	    }
	    mv.addObject("vehiclehistory", vehicleHistory);
	    return mv;
	}
	
	
	
	
	
	
	
	
	//Filter Vehicle by Status
	/*@RequestMapping("/filtervehicle"): Maps the /filtervehicle URL to this method.
gotovehiclemanagementpage_byfilter(): Filters the vehicle list based on the status and 
returns the vehicledashboard view with the filtered list.*/
	@RequestMapping("/filtervehicle")
	public ModelAndView gotovehiclemanagementpage_byfilter(@RequestParam("status") String status)
	{
		ModelAndView mv = new ModelAndView("/admin/vehicledashboard");
		List<Vehicle> list;
		if(status == null || status.isEmpty())
				list = vs.getVehiclelist();
		else
				list = vs.getVehiclelistbystatus(status);
		mv.addObject("vehiclelist", list);
		return mv;
	}
	// Password Reset Form
	/*@RequestMapping("/passwordresetform"): Maps the /passwordresetform URL to this method.
resetpasswordform(): Returns the passwordreset view for resetting the password.*/
	@RequestMapping("/passwordresetform")
	public ModelAndView resetpasswordform() {
		ModelAndView mv = new ModelAndView("passwordreset");
		return mv;
	}
	/*@RequestMapping("/resetpassword")
	public ModelAndView resetpassword(@RequestParam String password1,@RequestParam String password2,HttpSession hs) {
		String email = (String) hs.getAttribute("email");

		Employee employee = es.getemployeebyusername(email);
		
		ModelAndView mv = new ModelAndView();
		if(employee.getPassword().equals(password1)) {
			 mv = new ModelAndView("passwordreset");
			 mv.addObject("error", "NEW PASSWORD IS SAME AS OLD PASSWORD");
		}
		else if(!password1.equals(password2)) {
			 mv = new ModelAndView("redirect:/passwordresetform");
			 mv.addObject("error", "PASSWORDS  NOT MATCHED");
		}
		else {
			employee.setPassword(password2);
			es.resetpassword(employee);
			mv.setViewName("redirect:/login");
		}

		return mv;
	}*/
	
	// Reset Password
	/*@RequestMapping("/resetpassword"): Maps the /resetpassword URL to this method.
resetpassword(): Handles the password reset logic, checks for errors, and updates the password.*/
	@RequestMapping("/resetpassword")
	public ModelAndView resetpassword(@RequestParam String password1, @RequestParam String password2, HttpSession hs) {
	    String email = (String) hs.getAttribute("email");
	    Employee employee = es.getemployeebyusername(email);

	    ModelAndView mv = new ModelAndView();
	    if (employee.getPassword().equals(password1)) {
	        mv.setViewName("passwordreset");
	        mv.addObject("error", "NEW PASSWORD IS SAME AS OLD PASSWORD");
	    } else if (!password1.equals(password2)) {
	        mv.setViewName("passwordreset");
	        mv.addObject("error", "PASSWORDS NOT MATCHED");
	    } else {
	        employee.setPassword(password2);
	        es.resetpassword(employee);
	        mv.setViewName("redirect:/login");
	    }
	    return mv;
	}
	
	//Admin Dashboard
	/*@RequestMapping("/admindashboard"): Maps the /admindashboard URL to this method.
goto_admin_dashboard(): Fetches various counts and pending approvals, and returns the admindashboard view with the data.*/
	@RequestMapping("/admindashboard")
	public ModelAndView goto_admin_dashboard() {
		ModelAndView mv = new ModelAndView("/admin/admindashboard");
		 Map<String,Integer> map = vs.getVehicleStatusCount();
		 mv.addObject("statuscount", map);
		 Map<String,Integer> map1 = es.getemployeeavailablityCount();
		 mv.addObject("availablitycount", map1);
		 List<ServiceRecord> pendingapprovals = srs.getpendingApprovalRecords();
		 mv.addObject("pendingapprovals", pendingapprovals);
		 
		return mv;
	}
	//Approve Bill of Materials
	/*@RequestMapping("/approvebom"): Maps the /approvebom URL to this method.
approvebom(): Approves the bill of materials, updates the status, and redirects to the admin dashboard.*/
	@RequestMapping("/approvebom")
	public ModelAndView approvebom(@RequestParam int servicerecordid) {
		ServiceRecord record = srs.findById(servicerecordid);
		record.setStatus("Approved");
		srs.save(record);
		Employee employee = record.getEmployee();
		es.updateworkingstatus(employee);
		Vehicle v = record.getVehicle();
		v.setActual_cost(record.getActualcost());
		vs.updatevehiclestatus(v);
		ModelAndView mv = new ModelAndView("redirect:/admin/admindashboard");
		return mv;
	}
	
	//Employee Management
	/*@RequestMapping("/employeelist"): Maps the /employeelist URL to this method.
goto_employee_management(): Fetches the list of employees and returns the employeepage view with the employee list.
@RequestMapping("/filteremployeelist"): Maps the /filteremployeelist URL to this method.
goto_employee_page_byfilter(): Filters the employee list based on availability and returns the employeepage view with the filtered list.*/
	@RequestMapping("/employeelist")
	public ModelAndView goto_employee_management() {
		List<Employee> list = es.getemployeelist();
		ModelAndView mv = new ModelAndView("/admin/employeepage","employeelist",list);
		return mv;
	}
	@RequestMapping("/filteremployeelist")
	public ModelAndView goto_employee_page_byfilter(@RequestParam("availablity") String availablity)
	{		
		List<Employee> list;
		if(availablity==null || availablity.isEmpty()) {
			list= es.getemployeelist();
		}
		else {
			list = es.getemployeelist(availablity);
		}
		ModelAndView mv = new ModelAndView("/admin/employeepage","employeelist", list);
		return mv;
	}
}
