package com.ouchin.Citronix.service.impl;

import com.ouchin.Citronix.dto.request.HarvestRequestDTO;
import com.ouchin.Citronix.dto.respense.HarvestDetailsResponseDTO;
import com.ouchin.Citronix.dto.respense.HarvestResponseDTO;
import com.ouchin.Citronix.entity.Harvest;
import com.ouchin.Citronix.mapper.HarvestMapper;
import com.ouchin.Citronix.repository.HarvestRepository;
import com.ouchin.Citronix.service.HarvestService;
import com.ouchin.Citronix.service.validation.HarvestValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional
public class HarvestServiceImpl implements HarvestService {

    private final HarvestRepository harvestRepository;
    private final HarvestMapper harvestMapper;
    private final HarvestValidator harvestValidator;


    @Override
    public List<HarvestResponseDTO> findAll() {
        return harvestRepository.findAll().stream()
                .map(harvestMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public HarvestResponseDTO findById(Long id) {
        return harvestRepository.findById(id)
                .map(harvestMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Harvest not found with id: " + id));
    }

    @Override
    public HarvestResponseDTO create(HarvestRequestDTO requestDTO) {
        harvestValidator.validateNewHarvest(requestDTO);

        Harvest harvest = harvestMapper.toEntity(requestDTO);

        harvest.setHarvestDetails( new ArrayList<>());
        harvest = harvestRepository.save(harvest);

        return harvestMapper.toResponseDTO(harvest);
    }

    @Override
    public HarvestResponseDTO update(Long id, HarvestRequestDTO requestDTO) {
        Harvest existingHarvest = harvestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Harvest not found with id: " + id));

        if (!existingHarvest.getSeason().equals(requestDTO.getSeason())) {
            harvestValidator.validateSeasonForUpdate(requestDTO.getSeason(), id);
        }

        harvestValidator.validateHarvestDate(requestDTO.getHarvestDate(), requestDTO.getSeason());

        existingHarvest.setHarvestDate(requestDTO.getHarvestDate());
        existingHarvest.setSeason(requestDTO.getSeason());
        existingHarvest.setTotalQuantity(requestDTO.getTotalQuantity());

        existingHarvest = harvestRepository.save(existingHarvest);
        return harvestMapper.toResponseDTO(existingHarvest);
    }

    @Override
    public void delete(Long id) {
        if (!harvestRepository.existsById(id)) {
            throw new EntityNotFoundException("Harvest not found with id: " + id);
        }
        harvestRepository.deleteById(id);
    }





}

