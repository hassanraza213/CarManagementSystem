package com.cms.carManagementSystem.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;

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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getEmployeeRank() {
		return employeeRank;
	}

	public void setEmployeeRank(BigDecimal employeeRank) {
		this.employeeRank = employeeRank;
	}
}
