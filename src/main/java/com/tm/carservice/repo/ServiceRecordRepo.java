package com.tm.carservice.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tm.carservice.model.ServiceRecord;
import com.tm.carservice.model.Vehicle;

public interface ServiceRecordRepo extends JpaRepository<ServiceRecord, Integer> {
/*@Repository: Indicates that this interface is a Spring Data repository.
 * extends JpaRepository<ServiceRecord, Integer>: Extends the JpaRepository interface, specifying the ServiceRecord entity type and the type of its primary key (Integer).*/
	Optional<ServiceRecord> findByVehicle(Vehicle v);

	List<ServiceRecord> findByStatus(String string);

	ServiceRecord getByVehicle(Vehicle vehicleById);

}
/*findByVehicle(Vehicle v): Finds a ServiceRecord entity by the associated Vehicle entity. Returns an Optional<ServiceRecord> to handle cases where the service record may not exist.
findByStatus(String status): Finds a list of ServiceRecord entities by their status (e.g., "Pending Approval", "Serviced").
getByVehicle(Vehicle vehicleById): Retrieves a ServiceRecord entity by the associated Vehicle entity.*/