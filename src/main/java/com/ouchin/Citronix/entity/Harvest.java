package com.ouchin.Citronix.entity;


import com.ouchin.Citronix.entity.enums.Season;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "harvests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Harvest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Harvest date required ")
    @Column(name = "harvest_date", nullable = false)
    private LocalDate harvestDate;

    @NotNull(message = "Season required")
    @Enumerated(EnumType.STRING)
    private Season season;

    @PositiveOrZero
    @Column(name = "total_quantity", nullable = true)
    private Double totalQuantity;

    @OneToMany(mappedBy = "harvest", cascade = CascadeType.ALL)
    private List<HarvestDetails> harvestDetails = new ArrayList<>();

}






















