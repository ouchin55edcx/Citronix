package com.ouchin.Citronix.dto.respense;

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
public class FieldResponseDTO {
    private Long id;
    private String name;
    private Double area;
    private LocalDate creationDate;
    private List<TreeResponseDTO> trees;
    private int treeCount;
}
