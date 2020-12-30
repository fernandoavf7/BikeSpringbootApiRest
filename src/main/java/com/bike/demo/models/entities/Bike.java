package com.bike.demo.models.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.validation.annotation.Validated;


@Entity
@Table(name="bike")
public class Bike implements Serializable {
	
	/*
	 * this datatypes would be integer in the sqlite database, but will be
	 * represent as its enum name when it been requested
	 */
	public enum BikeSize {NOT_SET,XS,S,M,L,XL,XXL}
	public enum Type {NOT_SET,MTB,FIXIE,TRIATLHON,ROAD}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	//unique doesn't work on sqlite, you have to do it manually
	@Column(nullable=false, unique = true)
	private String name;
	
	@Column(nullable=false)
	private Type type;
	
	@Column(nullable=false)
	private BikeSize size;
	
	@Temporal(TemporalType.DATE)
	@Column(name="created_at")
	private Date createdAt;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public BikeSize getSize() {
		return size;
	}

	public void setSize(BikeSize size) {
		this.size = size;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@PrePersist
	public void prePersist() {
		createdAt = new Date();
	}
}
