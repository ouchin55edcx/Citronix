package com.ouchin.Citronix.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trees")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Tree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Planting date is required")
    @Column(name = "planting_date", nullable = false)
    private LocalDate plantingDate;

    @ManyToOne
    @JoinColumn(name = "field_id", nullable = false)
    @NotNull
    private Field field;

    @OneToMany(mappedBy = "tree", cascade = CascadeType.ALL)
    @Builder.Default
    private List<HarvestDetails> harvestDetails = new ArrayList<>();

    public boolean isProductive() {
        int age = Period.between(plantingDate, LocalDate.now()).getYears();
        return age >= 3 && age <= 20;
    }
}

