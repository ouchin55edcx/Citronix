package com.ouchin.Citronix.dto.respense;

import com.ouchin.Citronix.entity.Tree;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@

        Builder
@NoArgsConstructor
@AllArgsConstructor
public class FieldResponseDTO {
    private Long id;
    private String name;
    private Double area;
    private LocalDate creationDate;
    private List<TreeResponseDTO> trees;
    private int treeCount;
    private Double treeDensity; // trees per hectare

    // Method to populate tree details and calculate density
    public void populateTreeDetails(List<Tree> treeEntities) {
        if (treeEntities == null) {
            this.trees = new ArrayList<>();
            this.treeCount = 0;
            this.treeDensity = 0.0;
            return;
        }

        // Map tree entities to response DTOs
        this.trees = treeEntities.stream()
                .map(tree -> {
                    TreeResponseDTO treeDto = TreeResponseDTO.builder()
                            .id(tree.getId())
                            .plantingDate(tree.getPlantingDate())
                            .build();
                    treeDto.calculateAgeAndProductivity();
                    return treeDto;
                })
                .collect(Collectors.toList());

        // Calculate tree count and density
        this.treeCount = trees.size();
        this.treeDensity = this.treeCount / (this.area == null ? 1 : this.area);
    }
}
