package com.ouchin.Citronix.dto.respense;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreeResponseDTO {
    private Long id;
    private LocalDate plantingDate;
    private int age;
    private String productivityCategory;
    private Double expectedYieldPerSeason;



    public void calculateAgeAndProductivity() {
        this.age = (int) ChronoUnit.YEARS.between(this.plantingDate, LocalDate.now());

        if (age < 3) {
            this.productivityCategory = "Young";
            this.expectedYieldPerSeason = 2.5;
        } else if (age >= 3 && age <= 10) {
            this.productivityCategory = "Mature";
            this.expectedYieldPerSeason = 12.0;
        } else if (age > 10 && age <= 20) {
            this.productivityCategory = "Old";
            this.expectedYieldPerSeason = 20.0;
        } else {
            this.productivityCategory = "Non-Productive";
            this.expectedYieldPerSeason = 0.0;
        }

    }


}

