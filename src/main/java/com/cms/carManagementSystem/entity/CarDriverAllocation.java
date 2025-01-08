package com.cms.carManagementSystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "driverAllocation")
public class CarDriverAllocation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "driverAllocation_id")
	private Long carDriverAllocationId;
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "car_id")
	private Car car;
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "driver_id")
	private Driver driver;

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public Long getCarDriverAllocationId() {
		return carDriverAllocationId;
	}

	public void setCarDriverAllocationId(Long carDriverAllocationId) {
		this.carDriverAllocationId = carDriverAllocationId;
	}
	
}
