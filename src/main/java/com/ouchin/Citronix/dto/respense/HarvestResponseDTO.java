package com.ouchin.Citronix.dto.respense;

import com.ouchin.Citronix.entity.enums.Season;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HarvestResponseDTO {

    private Long id;
    private LocalDate harvestDate;
    private Season season;
    private Double totalQuantity;

    private List<HarvestDetailsResponseDTO> harvestDetails;

}