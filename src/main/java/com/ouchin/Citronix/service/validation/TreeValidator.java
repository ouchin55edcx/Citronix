package com.ouchin.Citronix.service.validation;

import com.ouchin.Citronix.dto.request.TreeRequestDTO;
import com.ouchin.Citronix.entity.Field;
import com.ouchin.Citronix.repository.FieldRepository;
import org.springframework.stereotype.Component;


@Component
public class TreeValidator {

    private static final int TREES_PER_HECTARE = 100;

    private final FieldRepository fieldRepository;

    public TreeValidator(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    public void validateTreeRequest(TreeRequestDTO treeRequestDTO) {
        if (!treeRequestDTO.isValidPlantingSeason()) {
            throw new IllegalArgumentException("Trees can only be planted between March and May");
        }

        if (!fieldRepository.existsById(treeRequestDTO.getFieldId())) {
            throw new IllegalArgumentException("Field not found with ID: " + treeRequestDTO.getFieldId());
        }
    }

    public void validateFieldCapacity(Field field) {
        double fieldAreaInHectares = field.getArea() / 10_000.0;
        int maxAllowedTrees = (int) (fieldAreaInHectares * TREES_PER_HECTARE);

        if (field.getTrees().size() >= maxAllowedTrees) {
            throw new IllegalArgumentException(
                    String.format("Field '%s' exceeds maximum tree density of %d trees per hectare",
                            field.getName(), TREES_PER_HECTARE));
        }
    }
}


