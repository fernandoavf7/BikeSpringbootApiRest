package com.bike.demo.models.services;

import java.util.List;

import com.bike.demo.models.entities.Bike;

/*
 * here we define our services that will be implemented in a class
 */
public interface IBikeService {

	public List<Bike> findAll();
	
	public Bike findById(Long id);
	
	public Bike save(Bike bike);
	
	public void delete(Long id);
}
