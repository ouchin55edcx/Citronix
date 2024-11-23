package com.ouchin.Citronix.dto.respense;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class FarmResponseDTO {
    private Long id;
    private String name;
    private String location;
    private Double totalArea;
    private LocalDate creationDate;
    private List<FieldResponseDTO> fields;
    private Double fieldsTotalArea;
    private Double availableArea;
}
