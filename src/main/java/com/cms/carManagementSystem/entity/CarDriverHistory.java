package com.cms.carManagementSystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@Table(name = "cardriverhistory")
public class CarDriverHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cardriverhistory_id")
    private Long carDriverHistoryId;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "driver_id")
    private Driver driver;

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
