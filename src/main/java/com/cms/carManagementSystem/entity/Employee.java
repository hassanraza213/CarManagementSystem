package com.cms.carManagementSystem.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "employee")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id")
	private Long employeeId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "employee_rank")
	private BigDecimal employeeRank;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "department_id")
	private Department department;
	
	@Column(name = "created_it_up", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdItUp;

	@Column(name = "updated_it_up")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedItUp;

	@Column(name = "description", length = 500)
	private String description;
}
