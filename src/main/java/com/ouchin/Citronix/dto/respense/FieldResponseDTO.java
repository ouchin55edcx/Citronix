package com.ouchin.Citronix.dto.respense;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FieldResponseDTO {
    private Long id;
    private String name;
    private Double area;
    private LocalDate creationDate;
    private FarmResponseDTO farm;
}
