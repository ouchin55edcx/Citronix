package com.ouchin.Citronix.dto.respense;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleResponseDTO {
    private Long id;
    private Double prixUnitaire;
    private LocalDate saleDate;
    private Double saleQuantity;
    private Double revenue;
    private String clientName;
    private Long harvestId;
    private LocalDate harvestDate;
    private String season;
}

