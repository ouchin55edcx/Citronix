package com.ouchin.Citronix.service.impl;

import com.ouchin.Citronix.dto.request.FarmRequestDTO;
import com.ouchin.Citronix.dto.respense.FarmResponseDTO;
import com.ouchin.Citronix.entity.Farm;
import com.ouchin.Citronix.mapper.FarmMapper;
import com.ouchin.Citronix.mapper.FieldMapper;
import com.ouchin.Citronix.repository.FarmRepository;
import com.ouchin.Citronix.service.FarmService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final FarmMapper farmMapper;
    private final FieldMapper fieldMapper;

    @Override
    public List<FarmResponseDTO> findAll() {
        List<Farm> farms = farmRepository.findAll();
        return farms.stream()
                .map(farm -> {
                    FarmResponseDTO dto = farmMapper.toResponseDTO(farm);
                    dto.setFieldsTotalArea(farm.calculateFieldsTotalArea());
                    dto.setAvailableArea(farm.getAvailableArea());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public FarmResponseDTO findById(Long id) {
        return farmRepository.findById(id)
                .map(farm -> {
                    FarmResponseDTO dto = farmMapper.toResponseDTO(farm);
                    dto.setFieldsTotalArea(farm.calculateFieldsTotalArea());
                    dto.setAvailableArea(farm.getAvailableArea());
                    return dto;
                })
                .orElseThrow(() -> new EntityNotFoundException("Farm not found with ID: " + id));
    }

    @Transactional
    @Override
    public FarmResponseDTO create(FarmRequestDTO farmRequestDTO) {
        Farm farm = farmMapper.toEntity(farmRequestDTO);
        farm.setFields(new ArrayList<>());
        Farm savedFarm = farmRepository.save(farm);
        return farmMapper.toResponseDTO(savedFarm);
    }

    @Override
    public FarmResponseDTO update(Long id, FarmRequestDTO farmRequestDTO) {
        Farm farm = farmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Farm not found with ID: " + id));
        farmMapper.updateFarmFromDto(farmRequestDTO, farm);
        Farm updatedFarm = farmRepository.save(farm);
        return farmMapper.toResponseDTO(updatedFarm);
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public List<FarmResponseDTO> findFarmsByCriteria(String name, String location) {
        List<Farm> farms = farmRepository.findFarmsByCriteria(name, location);
        return farms.stream()
                .map(farmMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}