package com.cms.carManagementSystem.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "maintenance")
public class Maintenance {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "maintenance_id")
	private Long maintenanceId;
	
	@Column(name = "maintenance_date")
	private LocalDate maintenanceDate;
	
	@Column(name = "maintenance_cost")
	private BigDecimal maintenanceCost;
	
	@Column(name = "maintenance_description")
	private String maintenance_description;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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

}
