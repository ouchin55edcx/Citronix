package com.ouchin.Citronix.service.impl;

import com.ouchin.Citronix.dto.request.FieldRequestDTO;
import com.ouchin.Citronix.dto.respense.FieldResponseDTO;
import com.ouchin.Citronix.entity.Farm;
import com.ouchin.Citronix.entity.Field;
import com.ouchin.Citronix.mapper.FieldMapper;
import com.ouchin.Citronix.repository.FarmRepository;
import com.ouchin.Citronix.repository.FieldRepository;
import com.ouchin.Citronix.service.FieldService;
import com.ouchin.Citronix.service.validation.FieldValidator;
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
    private final FieldValidator fieldValidator;

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
        Farm farm = farmRepository.findById(fieldRequestDTO.getFarmId())
                .orElseThrow(() -> new EntityNotFoundException("Farm not found with id: " + fieldRequestDTO.getFarmId()));

        Field field = fieldMapper.toEntity(fieldRequestDTO);
        field.setCreationDate(LocalDate.now());
        field.setFarm(farm);

        fieldValidator.validateFieldCreation(field, farm);

        Field savedField = fieldRepository.save(field);
        return fieldMapper.toResponseDTO(savedField);
    }

    @Override
    @Transactional
    public FieldResponseDTO update(Long id, FieldRequestDTO fieldRequestDTO) {
        Field existingField = fieldRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Field not found with id: " + id));

        Farm farm = farmRepository.findById(fieldRequestDTO.getFarmId())
                .orElseThrow(() -> new EntityNotFoundException("Farm not found with id: " + fieldRequestDTO.getFarmId()));

        existingField.setName(fieldRequestDTO.getName());
        existingField.setArea(fieldRequestDTO.getArea());
        existingField.setFarm(farm);

        fieldValidator.validateFieldUpdate(existingField, farm);

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