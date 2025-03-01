package com.tm.carservice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tm.carservice.model.Employee;
import com.tm.carservice.model.Vehicle;
import com.tm.carservice.repo.EmployeeRepo;
import com.tm.carservice.repo.VehicleRepo;

@Service
//@Service: Indicates that this class is a Spring service component.
public class EmployeeService {
//@Autowired: Injects the required repository beans into the service class.
    @Autowired
    EmployeeRepo er;
    
    @Autowired
    VehicleRepo vr;
//getemployeelist(): Retrieves a list of employees with the type "service advisor" from the EmployeeRepo.
    public List<Employee> getemployeelist() {
        return er.findByType("service advisor");
    }
//getemployeelist(String availablity): Retrieves a list of employees by their availability status from the EmployeeRepo.    
    public List<Employee> getemployeelist(String availablity) {
        return er.findByavailablity(availablity);
    }
 //addemployee(Employee e): Adds a new employee to the database. Sets the password to the employee's date of birth and assigns a default role if not provided.
    public void addemployee(Employee e) {
        e.setPassword(e.getDob().toString());
        if (e.getRole() == null || e.getRole().isEmpty()) {
            e.setRole("employee");  // default role
        }
        er.save(e);
    }

    /*public void resetpassword(Employee e) {
        er.save(e);
    }*/
    
 // resetpassword(Employee e): Resets the password for an employee and saves the updated employee to the database.
    public void resetpassword(Employee e) {
        e.setPassword(e.getPassword()); // Ensure the password is set correctly
        er.save(e);
    }
//getemployeebyId(int id): Retrieves an employee by their ID from the EmployeeRepo.
    public Employee getemployeebyId(int id) {
        return er.getById(id);
    }

    //assignvehicle(Employee e, Vehicle v): Assigns a vehicle to an employee, updates the employee's availability and vehicle ID, and updates the vehicle's status and assignment.
    public void assignvehicle(Employee e, Vehicle v) {
        e.setVehicleid(v.getId());
        e.setAvailablity("WORKING");
        er.save(e);
        v.setEmployee(e);
        v.setAssigned("ASSIGNED");
        v.setStatus("UNDER SERVICING");
        vr.save(v);
    }

    //checkcredentials(String email, String pass): Checks if the provided email and password match an existing employee's credentials.
    public boolean checkcredentials(String email, String pass) {
        Employee e = er.getByusername(email);
        return e != null && email.equals(e.getUsername()) && pass.equals(e.getPassword());
    }
    
//getemployeeavailablityCount(): Retrieves the count of employees by their availability status and returns it as a map.
    public Map<String, Integer> getemployeeavailablityCount() {
        Map<String, Integer> map = new HashMap<>();
        map.put("FREE", er.countByavailablity("FREE"));
        map.put("WORKING", er.countByavailablity("WORKING"));
        return map;
    }

    //getemployeebyusername(String email): Retrieves an employee by their username (email) from the EmployeeRepo.
    public Employee getemployeebyusername(String email) {
        return er.findByusername(email);
    }

    //getemployeebyavailablity(String availablity): Retrieves the first employee by their availability status from the EmployeeRepo.
    public Employee getemployeebyavailablity(String availablity) {
        return er.findFirstByavailablity(availablity);
    }

    //updateworkingstatus(Employee employee): Updates the working status of an employee to "FREE" and resets their vehicle ID.
    public void updateworkingstatus(Employee employee) {
        employee.setAvailablity("FREE");
        employee.setVehicleid(0);
        er.save(employee);
    }

    //getEmployeesByAvailability(String availability): Retrieves a list of employees by their availability status from the EmployeeRepo.
    public List<Employee> getEmployeesByAvailability(String availability) {
        return er.findByavailablity(availability);
    }
}