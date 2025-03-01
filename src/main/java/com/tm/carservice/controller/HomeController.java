package com.tm.carservice.controller;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tm.carservice.model.Employee;
import com.tm.carservice.model.Vehicle;
import com.tm.carservice.service.EmployeeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
//@Controller: Indicates that this class is a Spring MVC controller.
@Controller
public class HomeController {
	//@Autowired: Injects the EmployeeService bean into the controller.
	@Autowired
	EmployeeService es;
	//Home Page
	/*@GetMapping("/"): Maps the root URL (/) to this method.
method1(): Returns the index view.*/
	@GetMapping("/")
	public String method1() {
		return "index";
	}
	
	//Login
	/*@GetMapping("/login"): Maps the /login URL to this method.
loginpage(): Returns the login view.*/
	@GetMapping("/login")
	public String loginpage() {
		return "login";
	}
	//Logout
	/*@PostMapping("/logout"): Maps the /logout URL to this method.
logout(): Invalidates the current session and redirects to the home page.*/
	@PostMapping("/logout")
	public ModelAndView logout(HttpServletRequest req) {	
		HttpSession hs = req.getSession(false);
		if(hs!=null) {
			hs.invalidate();
		}
		ModelAndView  mv = new ModelAndView("redirect:/");
		return mv;
	}
	
	
//check dashboard
	/*@RequestMapping("/checkdashboard"): Maps the /checkdashboard URL to this method.
goto_admin_user_page(): Handles login requests, checks credentials, and redirects to the appropriate dashboard (admin or employee) based on the user's role and login type.*/
	@RequestMapping("/checkdashboard")
    public ModelAndView goto_admin_user_page(HttpServletRequest req, HttpSession hs, RedirectAttributes redirectAttributes) {
        ModelAndView mv;
        String email = req.getParameter("email");
        String rawPassword = req.getParameter("password");
        String loginType = req.getParameter("loginType");
 
        hs.setAttribute("email", email);
 
        Employee employee = es.getemployeebyusername(email);
        if (employee == null || !es.checkcredentials(email, rawPassword)) {
            mv = new ModelAndView("login");
            mv.addObject("error", "CREDENTIALS ARE NOT VALID");
            return mv;
        }
 
        String role = employee.getRole();
        if ("admin".equalsIgnoreCase(role) && "admin".equalsIgnoreCase(loginType)) {
            mv = new ModelAndView("redirect:/admin/admindashboard");
        } else if (!"admin".equalsIgnoreCase(role) && "employee".equalsIgnoreCase(loginType)) {
            redirectAttributes.addFlashAttribute("vehiclehistory", new ArrayList<Vehicle>());
            mv = new ModelAndView("redirect:/employee/employeedashboard");
        } else {
            mv = new ModelAndView("login");
            mv.addObject("error", "YOU ARE NOT ALLOWED TO ACCESS THIS PAGE");
        }
 
        return mv;
    }
}
	
