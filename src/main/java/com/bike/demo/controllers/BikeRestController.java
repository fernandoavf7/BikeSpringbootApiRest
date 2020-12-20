package com.bike.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	// service get the first implementation that it found
	private IBikeService bikeService;

	/*
	 * http://localhost:8080/api/bikes
	 */
	@GetMapping("/bikes")
	public List<Bike> index() {
		return bikeService.findAll();
	}

	/*
	 * http://localhost:8080/api/bikes/6
	 */
	@GetMapping("bikes/{id}")
	public ResponseEntity<?> show(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();
		Bike bike = null;
		try {
			bike = bikeService.findById(id);
		} catch (DataAccessException e) {
			response.put("message", "Error to query database");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (bike == null) {
			response.put("message", "Bike with ID ".concat(id.toString().concat(" does not exist in the database.")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(bike, HttpStatus.OK);
	}

	/*
	 * example json sent: {"name": "Ballistic DH","size": "S","type": "MTB"}
	 */
	@PostMapping("/bikes")
	public ResponseEntity<?> create(@RequestBody Bike bike) {
		Map<String, Object> response = new HashMap<>();
		Bike bikeNew = null;

		try {
			bikeNew = bikeService.save(bike);
		} catch (DataAccessException e) {
			response.put("message", "Error to do insert in the database.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("message", "Bike was created successfully.");
		response.put("bike", bikeNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	/*
	 * example json sent: {"name": "id": 3, "Ballistic DH","size": "S","type":
	 * "MTB"}
	 */
	@PutMapping("/bikes")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> update(@RequestBody Bike bike) {

		Bike currentBike = bikeService.findById(bike.getId());

		Bike updatedBike = null;
		Map<String, Object> response = new HashMap<>();

		if (currentBike == null) {
			response.put("message", "Error: problem trying to edit bike with ID "
					.concat(bike.getId().toString().concat("does't exist on the database")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			currentBike.setName(bike.getName());
			currentBike.setSize(bike.getSize());
			currentBike.setType(bike.getType());

			updatedBike = bikeService.save(currentBike);
		} catch (DataAccessException e) {
			response.put("message", "Error to do insert in the database.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("message", "Bike was updated successfully.");
		response.put("bike", updatedBike);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	/*
	 * http://localhost:8080/api/bikes/6
	 */
	@DeleteMapping("/bikes/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		try {
			bikeService.delete(id);
		} catch (DataAccessException e) {
			response.put("message", "Error to delete bike in the database.");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("message", "Bike was deleted successfully.");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}
}
