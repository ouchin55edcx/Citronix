package com.ouchin.Citronix.service.impl;

import com.ouchin.Citronix.dto.request.FieldRequestDTO;
import com.ouchin.Citronix.dto.respense.FieldResponseDTO;
import com.ouchin.Citronix.entity.Farm;
import com.ouchin.Citronix.entity.Field;
import com.ouchin.Citronix.mapper.FieldMapper;
import com.ouchin.Citronix.repository.FarmRepository;
import com.ouchin.Citronix.repository.FieldRepository;
import com.ouchin.Citronix.service.FieldService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FieldServiceImpl implements FieldService {

    private final FieldRepository fieldRepository;
    private final FarmRepository farmRepository;
    private final FieldMapper fieldMapper;

    @Override
    @Transactional(readOnly = true)
    public List<FieldResponseDTO> findAll() {
        return fieldRepository.findAll()
                .stream()
                .map(fieldMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public FieldResponseDTO findById(Long id) {
        return fieldRepository.findById(id)
                .map(fieldMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Field not found with id: " + id));
    }

    @Override
    @Transactional
    public FieldResponseDTO create(FieldRequestDTO fieldRequestDTO) {
        // Find the farm
        Farm farm = farmRepository.findById(fieldRequestDTO.getFarmId())
                .orElseThrow(() -> new EntityNotFoundException("Farm not found with id: " + fieldRequestDTO.getFarmId()));

        // Validate if the new field's area meets the minimum requirement (1,000 m²)
        if (fieldRequestDTO.getArea() < 1000) {
            throw new IllegalArgumentException("Field area must be at least 1,000 m²");
        }

        // Validate if the new field's area exceeds 50% of the farm's total area
        if (fieldRequestDTO.getArea() > farm.getTotalArea() * 0.5) {
            throw new IllegalArgumentException("Field area cannot exceed 50% of the farm's total area");
        }

        // Validate if the new field's area doesn't exceed farm's remaining area
        double currentFieldsArea = farm.calculateFieldsTotalArea();
        if (currentFieldsArea + fieldRequestDTO.getArea() > farm.getTotalArea()) {
            throw new IllegalArgumentException("Total field area would exceed farm's total area");
        }

        // Validate the maximum number of fields (10 per farm)
        if (farm.getFields().size() >= 10) {
            throw new IllegalArgumentException("A farm cannot have more than 10 fields");
        }

        // Convert DTO to entity
        Field field = fieldMapper.toEntity(fieldRequestDTO);
        field.setCreationDate(LocalDate.now());
        field.setFarm(farm);

        // Save and return
        Field savedField = fieldRepository.save(field);
        return fieldMapper.toResponseDTO(savedField);
    }


    @Override
    @Transactional
    public FieldResponseDTO update(Long id, FieldRequestDTO fieldRequestDTO) {
        // Find existing field
        Field existingField = fieldRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Field not found with id: " + id));

        // Find the farm
        Farm farm = farmRepository.findById(fieldRequestDTO.getFarmId())
                .orElseThrow(() -> new EntityNotFoundException("Farm not found with id: " + fieldRequestDTO.getFarmId()));

        // Calculate total area excluding the current field
        double totalAreaExcludingCurrent = farm.calculateFieldsTotalArea() - existingField.getArea();

        // Validate minimum field size (1,000 m²)
        if (fieldRequestDTO.getArea() < 1000) {
            throw new IllegalArgumentException("Field area must be at least 1,000 m²");
        }

        // Validate if the new area exceeds 50% of the farm's total area
        if (fieldRequestDTO.getArea() > farm.getTotalArea() * 0.5) {
            throw new IllegalArgumentException("Field area cannot exceed 50% of the farm's total area");
        }

        // Validate if the total area (excluding current field) plus the new area exceeds the farm's total area
        if (totalAreaExcludingCurrent + fieldRequestDTO.getArea() > farm.getTotalArea()) {
            throw new IllegalArgumentException("Updated field area would exceed farm's total area");
        }

        // Update field attributes
        existingField.setName(fieldRequestDTO.getName());
        existingField.setArea(fieldRequestDTO.getArea());
        existingField.setFarm(farm);

        // Save and return
        Field updatedField = fieldRepository.save(existingField);
        return fieldMapper.toResponseDTO(updatedField);
    }


    @Override
    @Transactional
    public void delete(Long id) {
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Field not found with id: " + id));

        fieldRepository.delete(field);
    }
}