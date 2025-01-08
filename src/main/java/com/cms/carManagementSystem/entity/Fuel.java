package com.cms.carManagementSystem.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "fuel")
public class Fuel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "fuel_id")
	private Long fuelId;
	
	@Column(name = "fuel_type")
	private String fueldType;
	
	@Column(name = "fuel_quantity")
	private BigDecimal fuelQuantity;
	
	@Column(name = "date")
	private LocalDate date;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "car_id")
	private Car car;

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Long getFuelId() {
		return fuelId;
	}

	public void setFuelId(Long fuelId) {
		this.fuelId = fuelId;
	}

	public String getFueldType() {
		return fueldType;
	}

	public void setFueldType(String fueldType) {
		this.fueldType = fueldType;
	}

	public BigDecimal getFuelQuantity() {
		return fuelQuantity;
	}

	public void setFuelQuantity(BigDecimal fuelQuantity) {
		this.fuelQuantity = fuelQuantity;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
}
