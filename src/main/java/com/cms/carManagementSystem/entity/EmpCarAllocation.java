package com.cms.carManagementSystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "carAllocation")
public class EmpCarAllocation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "carAllocation_id")
	private Long empCarAllocationId;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "car_id")
	private Car car;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "employee_id")
	private Employee employee;

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Long getEmpCarAllocationId() {
		return empCarAllocationId;
	}

	public void setEmpCarAllocationId(Long empCarAllocationId) {
		this.empCarAllocationId = empCarAllocationId;
	}

}
