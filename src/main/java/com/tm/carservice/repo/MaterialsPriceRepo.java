package com.tm.carservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tm.carservice.model.MaterialsPrice;

/*@Repository: Indicates that this interface is a Spring Data repository.
extends JpaRepository<MaterialsPrice, Integer>: Extends the JpaRepository interface, specifying the MaterialsPrice entity type and the type of its primary key (Integer).*/
@Repository
public interface MaterialsPriceRepo extends JpaRepository<MaterialsPrice, Integer> {

	MaterialsPrice findByItemIgnoreCase(String item);

}
/*findByItemIgnoreCase(String item): Finds a MaterialsPrice entity by its item name, ignoring case differences.*/
