package com.ouchin.Citronix.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleRequestDTO {
    @NotNull(message = "Unit price is required")
    @Positive(message = "Unit price must be positive")
    private Double prixUnitaire;

    @NotNull(message = "Sale date is required")
    private LocalDate saleDate;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private Double quantity;

    @NotNull(message = "Harvest ID is required")
    private Long harvestId;

    @NotNull(message = "Client name is required")
    private String clientName;
}
