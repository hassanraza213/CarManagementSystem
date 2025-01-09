package com.cms.carManagementSystem.entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "department")
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "department_id")
	private Long departmentId;
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "ministry_id")
	private Ministry ministry;
	
	@Column(name = "created_it_up", nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdItUp;

	@Column(name = "updated_it_up")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedItUp;

	@Column(name = "description", length = 500)
	private String description;
}
