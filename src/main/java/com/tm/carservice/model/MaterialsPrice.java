package com.tm.carservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
//@Entity: Indicates that this class is a JPA entity and will be mapped to a database table.
public class MaterialsPrice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String item;
	private double price;
	/*@Id: Specifies the primary key of the entity.
@GeneratedValue(strategy = GenerationType.IDENTITY): Indicates that the primary key value will be generated automatically.
Fields:
id: The unique identifier for each material price entry.
item: The name of the material.
price: The price of the material.*/
	//getters and Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	/*Getters and Setters: Provide access to the private fields and allow them to be modified.*/
	
}
