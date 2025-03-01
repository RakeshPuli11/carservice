package com.tm.carservice.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tm.carservice.model.Vehicle;
import com.tm.carservice.repo.ServiceRecordRepo;
import com.tm.carservice.repo.VehicleRepo;

@Service
//@Service: Indicates that this class is a Spring service component.
public class VehicleService {
	@Autowired
	//@Autowired: Injects the required repository beans into the service class.
	VehicleRepo vr;

	
	@Autowired
	ServiceRecordRepo srr;
	

	//getVehiclelist(): Retrieves a list of all vehicles from the VehicleRepo.
	public List<Vehicle> getVehiclelist() {
		return vr.findAll();
	}
	
	
	
	
	
	
	
	
	
	public List<Vehicle> getVehicleHistory() {
	    return vr.findAll(); // Assuming you want to fetch all vehicle records
	}
	
	public List<Vehicle> findByRegistrationNumber(String registrationNumber) {
	    return vr.findByRegistrationNumber(registrationNumber);
	}
	
	
	
	
	
	
	
	
	//getVehiclelistbystatus(String status): Retrieves a list of vehicles by their status from the VehicleRepo.
	public List<Vehicle> getVehiclelistbystatus(String status) {
		return vr.findByStatus(status);
	}

	//addvehicle(Vehicle vehicle): Adds a new vehicle to the database.
	public void addvehicle(Vehicle vehicle) {
		 vr.save(vehicle);
	}
	
	//getVehicleById(int id): Retrieves a vehicle by its ID from the VehicleRepo.
	public Vehicle getVehicleById(int id) {
		return vr.getById(id);
//		return vr.findById(id);
	}

	//getVehicleStatusCount(): Retrieves the count of vehicles by their status and returns it as a map.
	public Map<String, Integer> getVehicleStatusCount() {
		// TODO Auto-generated method stub
		Map<String,Integer> map = new HashMap<>();
//		System.out.println(vr.countByStatus("due"));
		map.put("DUE", vr.countByStatus("DUE"));
		map.put("UNDER SERVICING", vr.countByStatus("UNDER SERVICING"));
		map.put("SERVICED", vr.countByStatus("SERVICED"));
		return map;
	}

	//findByemployeeId(int id): Retrieves a list of vehicles by the employee ID from the VehicleRepo.
	public List<Vehicle> findByemployeeId(int id) {
		// TODO Auto-generated method stub
		return vr.findByemployeeId(id);
	}

	//updatevehiclestatus(Vehicle v): Updates the status of a vehicle to "SERVICED" and sets the release date to the current date.
	public void updatevehiclestatus(Vehicle v) {
		// TODO Auto-generated method stub
		v.setStatus("SERVICED");
		v.setReleasedDate(LocalDate.now());
		vr.save(v);
		
//		ServiceRecord sr = srr.findByVehicle(v).get();
	
		
	}

	



	
}
