package com.cms.carManagementSystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "carAllocation")
public class EmpCarAllocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "carAllocation_id")
    private Long empCarAllocationId;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "employee_id")
    private Employee employee;

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
