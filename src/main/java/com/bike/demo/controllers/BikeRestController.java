package com.bike.demo.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
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
import com.bike.demo.response.CommonResponse;

@RestController
@RequestMapping("/api")
public class BikeRestController {

	@Autowired
	// service get by default the first implementation that it found
	private IBikeService bikeService;

	/*
	 * http://localhost:8080/api/bikes
	 */
	@GetMapping("/bikes")
	public List<Bike> index() {
		return bikeService.findAll();
	}

	/*
	 * http://localhost:8080/api/bikes/6 this response a custom Map created at
	 * moment
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
	 * example json sent: {"name": "Ballistic DH","size": "S","type": "MTB"} This
	 * returns a predefined Object specifically created to response a request
	 */
	@PostMapping("/bikes")
	public ResponseEntity<CommonResponse> create(@Valid @RequestBody Bike bike, Errors errors) {

		CommonResponse response = new CommonResponse();
		ResponseEntity<CommonResponse> responseEntity = null;
		Bike newBike = null;

		if (errors.hasErrors()) {
			response.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
			response.setMessage(String.join(",",
					errors.getAllErrors().stream().map(err -> err.getDefaultMessage()).collect(Collectors.toList())));
			responseEntity = ResponseEntity.badRequest().body(response);
			return responseEntity;
		}

		Bike oldBike = bikeService.findByName(bike.getName());
		if (oldBike != null) {
			response.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
			response.setMessage("Name already exists in database");
			response.setObjResponse(oldBike);
			responseEntity = ResponseEntity.badRequest().body(response);
			return responseEntity;
		}

		try {
			newBike = bikeService.save(bike);
		} catch (DataAccessException e) {
			response.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
			response.setMessage(e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			responseEntity = ResponseEntity.badRequest().body(response);
			return responseEntity;
		}

		response.setCode(String.valueOf(HttpStatus.CREATED.value()));
		response.setMessage("Bike created successfully");
		response.setObjResponse(newBike);
		responseEntity = ResponseEntity.ok().body(response);
		return responseEntity;

	}

	/*
	 * example json sent: {"name": "id": 3, "Ballistic DH","size": "S","type":
	 * "MTB"}
	 */
	@PutMapping("/bikes")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<CommonResponse> update(@Valid @RequestBody Bike bike, Errors errors) {

		Bike currentBike = bikeService.findById(bike.getId());
		Bike updatedBike = null;

		CommonResponse response = new CommonResponse();
		ResponseEntity<CommonResponse> responseEntity = null;

		if (errors.hasErrors()) {
			response.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
			response.setMessage(String.join(",",
					errors.getAllErrors().stream().map(err -> err.getDefaultMessage()).collect(Collectors.toList())));
			responseEntity = ResponseEntity.badRequest().body(response);
			return responseEntity;
		}

		Bike oldBike = bikeService.findByName(bike.getName());

		if (oldBike != null) {
			response.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
			response.setMessage("Name already exists in database");
			responseEntity = ResponseEntity.badRequest().body(response);
			return responseEntity;
		}

		if (currentBike == null) {
			response.setCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
			response.setMessage("Error: problem trying to edit bike with ID: "
					.concat(bike.getId().toString().concat(" does't exist on the database")));
			responseEntity = ResponseEntity.badRequest().body(response);
			return responseEntity;
		}

		try {
			currentBike.setName(bike.getName());
			currentBike.setSize(bike.getSize());
			currentBike.setType(bike.getType());
			updatedBike = bikeService.save(currentBike);
		} catch (DataAccessException e) {
			response.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
			response.setMessage(e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			responseEntity = ResponseEntity.badRequest().body(response);
			return responseEntity;
		}

		response.setCode(String.valueOf(HttpStatus.OK.value()));
		response.setMessage("Bike id: " + updatedBike.getId() + " updated");
		response.setObjResponse(updatedBike);
		responseEntity = ResponseEntity.ok().body(response);
		return responseEntity;
	}

	/*
	 * http://localhost:8080/api/bikes/6
	 */
	@DeleteMapping("/bikes/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<CommonResponse> delete(@PathVariable Long id) {
		CommonResponse response = new CommonResponse();
		ResponseEntity<CommonResponse> responseEntity = null;

		try {
			bikeService.delete(id);
		} catch (DataAccessException e) {
			response.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
			response.setMessage("Error to delete bike in the database. "
					+ e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			responseEntity = ResponseEntity.badRequest().body(response);
			return responseEntity;
		}

		response.setCode(String.valueOf(HttpStatus.OK.value()));
		response.setMessage("Bike deleted successfully");
		response.setObjResponse(null);
		responseEntity = ResponseEntity.ok().body(response);
		return responseEntity;
	}
}
