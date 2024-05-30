package com.iuno.fuel.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
public class FuelStation {

    @Id
    @Column(name = "time", nullable = false)
    private Instant time;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private double price;
}
