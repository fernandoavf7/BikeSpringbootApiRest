package com.bike.demo.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.bike.demo.models.entity.Bike;

public interface IBikeDao extends CrudRepository<Bike, Long> {

}
