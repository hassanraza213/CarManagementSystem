package com.cms.carManagementSystem.entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "car")
public class Car {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "car_id")
	private Long carId;
	
	@Column(name = "car_model")
	private int carModel;
	
	@Column(name = "car_make")
	private String carMake;
	
	@Column(name = "car_condition")
	private String carCondition;
	
	@Column(name = "car_status")
	private String carStatus;

	@Column(name = "created_it_up", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdItUp;

	@Column(name = "updated_it_up")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedItUp;

	@Column(name = "description", length = 500)
	private String description;	
}
