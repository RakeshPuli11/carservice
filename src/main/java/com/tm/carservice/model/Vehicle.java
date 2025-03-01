package com.tm.carservice.model;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
//@Entity: Indicates that this class is a JPA entity and will be mapped to a database table.
public class Vehicle {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;

@Column(nullable=false)
@NotBlank(message="Name is mandatory")
@Size(min=3,max=50,message="Name must between 3to50 characters")
private String customerName;

@Column(nullable=false)
@NotBlank(message=" Contact is mandatory")
@Pattern(regexp="\\d{10}",message="Contact must be a 10-digit number")
private String customerContact;

@Column(nullable = false)
@NotBlank(message="Registration Number is mandatory")
@Pattern(regexp="^[A-Z]{2}\\d{2}[A-Z]{1,2}\\d{4}$", message="Invalid Registration Number")
private String registrationNumber;

@Column(nullable=false)
@NotBlank(message=" Vehicle model is mandatory")
@Size(min=3,max=50,message="Vechile Model Name must between 3to50 characters")
private String model;

@Column
private String status="DUE";// due or under servicing or serviced

@Column(nullable=false)
private String warranty;//yes or no
@Column(nullable=false)
private LocalDate entryDate  = LocalDate.now();

@Column
private LocalDate ReleasedDate;
@Column
private String assigned = "UNASSIGNED";
@NotBlank(message="Service type is required")
private String servicetype;

private String issuedescription;
private String issuefirst;
private String issuesecond;
private String issuethird;

@ManyToOne
@JoinColumn(name="employee")
private Employee employee;

private double estimated_cost=0;
private double actual_cost=0;

/*@Id: Specifies the primary key of the entity.
@GeneratedValue(strategy = GenerationType.IDENTITY): Indicates that the primary key value will be generated automatically.
@Column: Maps the field to a column in the database table.
@NotBlank: Ensures that the field is not blank.
@Size: Specifies the size constraints for the field.
@Pattern: Specifies a regular expression that the field must match.
@ManyToOne: Specifies a many-to-one relationship with another entity.
@JoinColumn: Specifies the foreign key column.*/



public String getIssuefirst() {
	return issuefirst;
}
public void setIssuefirst(String issuefirst) {
	this.issuefirst = issuefirst;
}
public String getIssuesecond() {
	return issuesecond;
}
public void setIssuesecond(String issuesecond) {
	this.issuesecond = issuesecond;
}
public String getIssuethird() {
	return issuethird;
}
public void setIssuethird(String issuethird) {
	this.issuethird = issuethird;
}
public double getEstimated_cost() {
	return estimated_cost;
}
public void setEstimated_cost(double estimated_cost) {
	this.estimated_cost = estimated_cost;
}
public double getActual_cost() {
	return actual_cost;
}
public void setActual_cost(double actual_cost) {
	this.actual_cost = actual_cost;
}
public String getServicetype() {
	return servicetype;
}
public void setServicetype(String servicetype) {
	this.servicetype = servicetype;
}
public String getIssuedescription() {
	return issuedescription;
}
public void setIssuedescription(String issuedescription) {
	this.issuedescription = issuedescription;
}
public LocalDate getEntrydate() {
	return entryDate;
}
public void setEntrydate(LocalDate entrydate) {
	this.entryDate = entrydate;
}
public String getAssigned() {
	return assigned;
}
public void setAssigned(String assigned) {
	this.assigned = assigned;
}

public Employee getEmployee() {
	return employee;
}
public void setEmployee(Employee employee) {
	this.employee = employee;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getCustomerName() {
	return customerName;
}
public void setCustomerName(String customerName) {
	this.customerName = customerName;
}
public String getCustomerContact() {
	return customerContact;
}
public void setCustomerContact(String customerContact) {
	this.customerContact = customerContact;
}
public String getRegistrationNumber() {
	return registrationNumber;
}
public void setRegistrationNumber(String registrationNumber) {
	this.registrationNumber = registrationNumber;
}
public String getModel() {
	return model;
}
public void setModel(String model) {
	this.model = model;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getWarranty() {
	return warranty;
}
public void setWarranty(String warranty) {
	this.warranty = warranty;
}

public LocalDate getEntryDate() {
	return entryDate;
}
public void setEntryDate(LocalDate entryDate) {
	this.entryDate = entryDate;
}
public LocalDate getReleasedDate() {
	return ReleasedDate;
}
public void setReleasedDate(LocalDate releasedDate) {
	ReleasedDate = releasedDate;
}

/*Getters and Setters: Provide access to the private fields and allow them to be modified.*/
@Override
public String toString() {
	return "Vehicle [id=" + id + ", customerName=" + customerName + ", customerContact=" + customerContact
			+ ", registrationNumber=" + registrationNumber + ", model=" + model + ", status=" + status + ", warranty="
			+ warranty + "]";
}

/*toString(): Returns a string representation of the vehicle, including its ID, customer name, contact, registration number, model, status, and warranty.*/

}
