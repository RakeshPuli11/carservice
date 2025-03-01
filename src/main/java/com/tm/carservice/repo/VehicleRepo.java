package com.tm.carservice.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tm.carservice.model.Vehicle;
/*@Repository: Indicates that this interface is a Spring Data repository.
extends JpaRepository<Vehicle, Integer>: Extends the JpaRepository interface, specifying the Vehicle entity type and the type of its primary key (Integer).*/
@Repository
public interface VehicleRepo extends JpaRepository<Vehicle, Integer> {
	Vehicle findById(int id);

	List<Vehicle> findByStatus(String status);

	Vehicle findFirstByemployeeIsNullOrderByEntryDateAsc();
	
	int countByStatus(String string);

	List<Vehicle> findByemployeeId(int id);

//	Vehicle findByRegistrationNumber(String registrationNumber);

	List<Vehicle> findByRegistrationNumber(String registrationNumber);
	

	

	
}
/*findById(int id): Finds a Vehicle entity by its ID.
findByStatus(String status): Finds a list of Vehicle entities by their status (e.g., "DUE", "UNDER SERVICING", "SERVICED").
findFirstByemployeeIsNullOrderByEntryDateAsc(): Finds the first Vehicle entity where the employee is null, ordered by the entry date in ascending order.
countByStatus(String status): Counts the number of Vehicle entities by their status.
findByemployeeId(int id): Finds a list of Vehicle entities by the employee ID.
findByRegistrationNumber(String registrationNumber): Finds a list of Vehicle entities by their registration number.*/
