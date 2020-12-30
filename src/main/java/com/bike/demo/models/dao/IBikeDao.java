package com.bike.demo.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bike.demo.models.entities.Bike;

@Repository
public interface IBikeDao extends CrudRepository<Bike, Long>, JpaRepository<Bike, Long>  {

	@Query("SELECT b FROM Bike b WHERE b.name = ?1")
	public Bike findByName(String name);
}
