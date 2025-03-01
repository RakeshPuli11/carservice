package com.tm.carservice.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tm.carservice.model.Employee;

@Repository
/*@Repository: Indicates that this interface is a Spring Data repository.
extends JpaRepository<Employee, Integer>: Extends the JpaRepository interface, 
specifying the Employee entity type and the type of its primary key (Integer).*/
public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

    List<Employee> findByavailablity(String availablity);

    Employee getByusername(String email);

    int countByavailablity(String string);

    Employee findByusername(String email);

    Employee findFirstByavailablity(String availablity);

    List<Employee> findByType(String string);

    Employee getById(int id);
}
/*findByavailablity(String availablity): Finds a list of employees by their availability status.
getByusername(String email): Retrieves an employee by their username (email).
countByavailablity(String availablity): Counts the number of employees by their availability status.
findByusername(String email): Finds an employee by their username (email).
findFirstByavailablity(String availablity): Finds the first employee by their availability status.
findByType(String type): Finds a list of employees by their type (e.g., service advisor, admin).
getById(int id): Retrieves an employee by their ID.*/