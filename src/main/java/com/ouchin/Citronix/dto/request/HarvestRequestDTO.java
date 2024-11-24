package com.ouchin.Citronix.dto.request;

import com.ouchin.Citronix.entity.enums.Season;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HarvestRequestDTO {

    @NotNull(message = "Harvest date is required")
    private LocalDate harvestDate;

    @NotNull(message = "Season is required")
    private Season season;


    @PositiveOrZero(message = "Total quantity cannot be negative")
    private Double totalQuantity = 0.0;
}
