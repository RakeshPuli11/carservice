package com.tm.carservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tm.carservice.model.Employee;
import com.tm.carservice.model.ServiceRecord;
import com.tm.carservice.model.Vehicle;
import com.tm.carservice.repo.MaterialsPriceRepo;
import com.tm.carservice.repo.EmployeeRepo;
import com.tm.carservice.repo.ServiceRecordRepo;
import com.tm.carservice.repo.VehicleRepo;

@Service
//@Service: Indicates that this class is a Spring service component.
public class ServiceRecordService {
	
	@Autowired
	EmployeeRepo er;
	
	@Autowired
	ServiceRecordRepo srr;
	
	@Autowired
	VehicleRepo vr;
	
	@Autowired
	MaterialsPriceRepo mrp;
	/*generateBillOfMaterials(int vehicleid, String serviceItems, String quantities, int employeeid): 
	 * Generates the bill of materials for a service record. It calculates the cost based on the service items and quantities, checks for warranty items, and updates the service record with the calculated cost and status.*/
	public void generateBillOfMaterials(int vehicleid, String serviceItems, String quantities, int employeeid) {
		// TODO Auto-generated method stub
		
		Employee employee = er.findById(employeeid).orElseThrow(()->new RuntimeException("Employee not found"));
		
		Vehicle v = vr.findById(vehicleid);
		
		
		if(!v.getStatus().equalsIgnoreCase("serviced") ) {
			double cost = 0;
			StringBuilder bom = new StringBuilder();
			if(!serviceItems.isBlank() || !serviceItems.isEmpty()) {
				String[] itemsArray = serviceItems.split(",");
				String[] quantitiesArray = quantities.split(",");
				
				for(int i=0;i<itemsArray.length;i++) {
					if(i>0 && i<itemsArray.length) {
						bom.append(",");
					}
					bom.append(itemsArray[i]+":"+quantitiesArray[i]);
					 cost += mrp.findByItemIgnoreCase(itemsArray[i]).getPrice() * Integer.parseInt(quantitiesArray[i]);
					 
					 String itemwarrantycheck=mrp.findByItemIgnoreCase(itemsArray[i]).getItem();
					 if((itemwarrantycheck.equalsIgnoreCase("Engine Issue") || itemwarrantycheck.equalsIgnoreCase("Battery Issue") || itemwarrantycheck.equalsIgnoreCase("Brake Drum") || itemwarrantycheck.equalsIgnoreCase("Brake Disc")) && v.getWarranty().equalsIgnoreCase("yes")) {
						 cost -= mrp.findByItemIgnoreCase(itemsArray[i]).getPrice() * Integer.parseInt(quantitiesArray[i]);
					 }
				}
			}
		
			String servicetype = v.getServicetype();
			if(servicetype.equals("OTHERS")) {
				servicetype = "LABOUR FEE";
			}
			
			double service_cost =   mrp.findByItemIgnoreCase(servicetype).getPrice();
	
			
			ServiceRecord record = srr.findByVehicle(v).orElse(new ServiceRecord());
			record.setVehicle(v);
			record.setEmployee(employee);
			record.setBillOfMaterials(bom);
			record.setStatus("Pending Approval");
			record.setActualcost(cost+service_cost);
			srr.save(record);
		}
		
	}
	
	
	//getpendingApprovalRecords(): Retrieves a list of service records with the status "Pending Approval".
	public List<ServiceRecord> getpendingApprovalRecords() {
		// TODO Auto-generated method stub
		return srr.findByStatus("Pending Approval");
	}
	//findById(int servicerecordid): Retrieves a service record by its ID.
	public ServiceRecord findById(int servicerecordid) {
		// TODO Auto-generated method stub
		return srr.getById(servicerecordid);
	}
	//save(ServiceRecord record): Saves the service record to the database.
	public void save(ServiceRecord record) {
		// TODO Auto-generated method stub
		srr.save(record);
	}

}
