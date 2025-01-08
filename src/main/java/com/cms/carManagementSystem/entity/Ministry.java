package com.cms.carManagementSystem.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ministry")
public class Ministry {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ministry_id")
	private Long ministryId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "address")
	private String address;

	public Long getMinistryId() {
		return ministryId;
	}

	public void setMinistryId(Long ministryId) {
		this.ministryId = ministryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
