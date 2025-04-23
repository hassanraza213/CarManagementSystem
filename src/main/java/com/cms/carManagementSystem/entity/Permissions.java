package com.cms.carManagementSystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "permissions")
public class Permissions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Long permissionId;

    @Column(name = "permission_name", nullable = false, unique = true)
    private String permissionName;

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
