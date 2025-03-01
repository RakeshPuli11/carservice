package com.tm.carservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
//@Entity: Indicates that this class is a JPA entity and will be mapped to a database table.
@Entity
public class ServiceRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	private Vehicle vehicle;
	
	@ManyToOne
	private Employee employee;
	
	
	private StringBuilder billOfMaterials;
	
	private String status; //Pending approval or Serviced

	private double actualcost=0;
	
	/*@Id: Specifies the primary key of the entity.
@GeneratedValue(strategy = GenerationType.IDENTITY): Indicates that the primary key value will be generated automatically.
@ManyToOne: Specifies a many-to-one relationship with another entity.
Fields:
id: The unique identifier for each service record.
vehicle: The vehicle associated with the service record.
employee: The employee associated with the service record.
billOfMaterials: A string builder to store the bill of materials for the service.
status: The status of the service record (e.g., Pending approval, Serviced).
actualcost: The actual cost of the service.*/
	
	//Getters And Setters:Provide access to the private fields and allow them to be modified.
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public double getActualcost() {
		return actualcost;
	}

	public void setActualcost(double actualcost) {
		this.actualcost = actualcost;
	}

	public ServiceRecord() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	
	
	
	public String getStatus() {
		return status;
	}


	public StringBuilder getBillOfMaterials() {
		return billOfMaterials;
	}

	public void setBillOfMaterials(StringBuilder bom) {
		this.billOfMaterials = bom;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
