package com.cms.carManagementSystem.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "budget")
public class Budget {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "budget_id")
	private Long budgetId;
	
	@Column(name = "newcar_budget")
	private BigDecimal newCarBudget;
	
	@Column(name = "maintenance_budget")
	private BigDecimal maintenanceBudget;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "department_id")
	private Department department;

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Long getBudgetId() {
		return budgetId;
	}

	public void setBudgetId(Long budgetId) {
		this.budgetId = budgetId;
	}

	public BigDecimal getNewCarBudget() {
		return newCarBudget;
	}

	public void setNewCarBudget(BigDecimal newCarBudget) {
		this.newCarBudget = newCarBudget;
	}

	public BigDecimal getMaintenanceBudget() {
		return maintenanceBudget;
	}

	public void setMaintenanceBudget(BigDecimal maintenanceBudget) {
		this.maintenanceBudget = maintenanceBudget;
	}
	
}
