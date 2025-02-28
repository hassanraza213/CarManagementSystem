package com.cms.carManagementSystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long carId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "car_model")
    private int carModel;

    @Column(name = "car_make")
    private String carMake;

    @Column(name = "car_condition")
    private String carCondition;

    @Column(name = "car_status")
    private String carStatus;

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
