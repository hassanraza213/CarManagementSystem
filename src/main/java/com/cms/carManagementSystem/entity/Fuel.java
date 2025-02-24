package com.cms.carManagementSystem.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "fuel")
	public class Fuel {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "fuel_id")
		private Long fuelId;

		@Column(name = "fuel_type")
		private String fuelType;

		@Column(name = "fuel_quantity")
		private BigDecimal fuelQuantity;

		@Column(name = "date")
		private LocalDate date;

		@ManyToOne(cascade = CascadeType.MERGE)
		@JoinColumn(name = "car_id")
		private Car car;
	
	@Column(name = "created_it_up", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdItUp;

	@Column(name = "updated_it_up")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedItUp;

	@Column(name = "description", length = 500)
	private String description;

	@PrePersist
	protected void onCreate() {
		Date currentDate = new Date();
		this.createdItUp = currentDate;
		this.updatedItUp = currentDate;
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedItUp = new Date();
	}

}
