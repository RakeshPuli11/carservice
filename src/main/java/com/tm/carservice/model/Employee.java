package com.tm.carservice.model;
 
import java.time.LocalDate;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
 
@Entity
//@Entity: Indicates that this class is a JPA entity and will be mapped to a database table.
public class Employee {
 //Fields and Annotations
	/*@Id: Specifies the primary key of the entity.
@GeneratedValue(strategy = GenerationType.IDENTITY): Indicates that the primary key value will be generated automatically.
@Column: Maps the field to a column in the database table.
@NotBlank: Ensures that the field is not blank.
@Size: Specifies the size constraints for the field.
@Pattern: Specifies a regular expression that the field must match.
@Email: Ensures that the field contains a valid email address.*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable=false)
	@NotBlank(message="Name is mandatory")
	@Size(min=3,max=50,message="Name must between 3to50 characters")
	private String name;
	@Column(nullable=false)
	@NotBlank(message=" Contact is mandatory")
	@Pattern(regexp="\\d{10}",message="Contact must be a 10-digit number")
	private String contact;
	@Column
	private LocalDate dob;
	@Column(nullable=false)
	@NotBlank(message=" address is mandatory")
	@Size(min=3,max=50,message="Name must between 3to150 characters")
	private String address;
	@Column
	private String availablity="FREE"; //FREE OR WORKING
	@Column
	private int vehicleid = 0;
	@Column(nullable=false,unique = true)
	@NotBlank(message="Username is required")
	@Email(message="Invalid email format")
	private String username;

	private String password;
	@Column
	private String type="";  //admin or service advisor
	@Column(nullable=false)
    @NotBlank(message="Role is mandatory")
    private String role;  // admin, employee, labour, advisor

    // getters and setters for role
	//Getters and Setters: Provide access to the private fields and allow them to be modified.
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public LocalDate getDob() {
		return dob;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getVehicleid() {
		return vehicleid;
	}
	public void setVehicleid(int vehicleid) {
		this.vehicleid = vehicleid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getname() {
		return name;
	}
	public void setname(String name) {
		this.name = name;
	}
	public String getcontact() {
		return contact;
	}
	public void setcontact(String contact) {
		this.contact = contact;
	}
	public String getAvailablity() {
		return availablity;
	}
	public void setAvailablity(String availablity) {
		this.availablity = availablity;
	}
	@Override
	//toString(): Returns a string representation of the employee, which is the employee's name in this case.
	public String toString() {
		//return id + "-" + name;
		return name;
	}

}