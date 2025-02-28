package com.cms.carManagementSystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "driver")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_id")
    private Long driverId;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "name")
    private String driverName;

    @Column(name = "license_number")
    private String licenseNumber;

    @Column(name = "availability")
    private String availability;

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
