package com.cms.carManagementSystem.entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "driverAllocation")
public class CarDriverAllocation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "driverAllocation_id")
	private Long carDriverAllocationId;
	
	@OneToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(name = "car_id")
	private Car car;
	
	@OneToOne(cascade = {CascadeType.MERGE})
	@JoinColumn(name = "driver_id")
	private Driver driver;

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
