package com.bike.demo.models.services;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bike.demo.models.dao.IBikeDao;
import com.bike.demo.models.entity.Bike;

@Service
public class BikeServiceImplemented implements IBikeService{

	@Autowired
	private IBikeDao bikeDao;
	@Override
	//(crudRepository have their methods transactionals)
	@Transactional(readOnly = true)
	public List<Bike> findAll() {
		return (List<Bike>)bikeDao.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Bike findById(Long id) {
		return bikeDao.findById(id).orElse(null);
	}
	@Override
	@Transactional
	public Bike save(Bike bike) {
		return bikeDao.save(bike);
	}
	@Override
	@Transactional
	public void delete(Long id) {
		bikeDao.deleteById(id);
	}

}
