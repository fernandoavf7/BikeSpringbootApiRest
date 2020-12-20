package com.bike.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bike.demo.models.entities.Bike;
import com.bike.demo.models.services.IBikeService;

@RestController
@RequestMapping("/api")
public class BikeRestController {

	@Autowired
	//service get the first implementation that it found
	private IBikeService bikeService;
	
	@GetMapping("/bikes")
	public List<Bike> index(){
		return bikeService.findAll();
	}
	
	@GetMapping("bikes/{id}")
	public Bike show(@PathVariable Long id) {
		return bikeService.findById(id);
	}
	
	/*
	 * example json sent:
	 * {"name": "Ballistic DH","size": "S","type": "MTB"}
	 */
	@PostMapping("/bikes")
	@ResponseStatus(HttpStatus.CREATED)
	public Bike create(@RequestBody Bike bike) {
		return bikeService.save(bike);
	}
	
	/*
	 * example json sent:
	 * {"name": "id": 3, "Ballistic DH","size": "S","type": "MTB"}
	 */
	@PutMapping("/bikes")
	@ResponseStatus(HttpStatus.CREATED)
	public Bike update(@RequestBody Bike bike) {
		Bike currentBike = bikeService.findById(bike.getId());
		
		currentBike.setName(bike.getName());
		currentBike.setSize(bike.getSize());
		currentBike.setType(bike.getType());
		
		return bikeService.save(currentBike);
	}
	
	@DeleteMapping("/bikes/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public @ResponseBody void delete(@PathVariable Long id) {
		bikeService.delete(id);
	}
}

