package com.ouchin.Citronix.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

@Entity
public class Farm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required ")
    @Column(unique = true, nullable = false)
    private String name;

    @NotBlank(message = "Location is required")
    @Column(nullable = false)
    private String location;

    @NotNull(message = "Total area is required ")
    @Positive
    @Column(nullable = false)
    private Double totalArea;

    @NotNull
    @PastOrPresent(message = "The date must be in the past or in the present ")
    @Column(nullable = false)
    private LocalDate creationDate;
    
}
