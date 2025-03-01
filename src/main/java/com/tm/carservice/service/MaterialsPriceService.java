package com.tm.carservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tm.carservice.model.MaterialsPrice;
import com.tm.carservice.repo.MaterialsPriceRepo;
//@Service: Indicates that this class is a Spring service component.
@Service
public class MaterialsPriceService {

	@Autowired
	MaterialsPriceRepo mrp;
	
	public MaterialsPrice getMaterialsPriceByitem(String item) {
		// TODO Auto-generated method stub
		return mrp.findByItemIgnoreCase(item);
	}

}
/*getMaterialsPriceByitem(String item): Retrieves a MaterialsPrice entity by its item name, ignoring case differences. It calls the findByItemIgnoreCase method from the MaterialsPriceRepo.*/