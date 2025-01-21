package com.cms.carManagementSystem.entity;

import java.util.Date;
import jakarta.persistence.*;
import lombok.Data;

@Data
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
