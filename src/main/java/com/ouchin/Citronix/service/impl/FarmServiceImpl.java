package com.ouchin.Citronix.service.impl;

import com.ouchin.Citronix.dto.request.FarmRequestDTO;
import com.ouchin.Citronix.dto.respense.FarmResponseDTO;
import com.ouchin.Citronix.entity.Farm;
import com.ouchin.Citronix.mapper.FarmMapper;
import com.ouchin.Citronix.repository.FarmRepository;
import com.ouchin.Citronix.service.FarmService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final FarmMapper farmMapper;

    public FarmServiceImpl(FarmRepository farmRepository, FarmMapper farmMapper) {
        this.farmRepository = farmRepository;
        this.farmMapper = farmMapper;
    }


    @Override
    public List<FarmResponseDTO> findAll() {
        return farmRepository.findAll().stream()
                .map(farmMapper::toResponseDTO)
                .collect(Collectors.toList());
    }


    @Override
    public FarmResponseDTO findById(Long id) {
        Optional<Farm> farmOptional = farmRepository.findById(id);
        return farmOptional.map(farmMapper::toResponseDTO).orElse(null);
    }

    @Transactional
    @Override
    public FarmResponseDTO create(FarmRequestDTO farmRequestDTO) {
        Farm farm = farmMapper.toEntity(farmRequestDTO);
        Farm savedFarm = farmRepository.save(farm);
        return farmMapper.toResponseDTO(savedFarm);
    }

    @Override
    public FarmResponseDTO update(Long id, FarmRequestDTO farmRequestDTO) {
        Optional<Farm> farmOptional = farmRepository.findById(id);
        if (farmOptional.isPresent()) {
            Farm farm = farmOptional.get();
            farmMapper.updateFarmFromDto(farmRequestDTO, farm);
            Farm UpdatedFarm = farmRepository.save(farm);
            return farmMapper.toResponseDTO(UpdatedFarm);
        }else {
            throw new EntityNotFoundException("Farm not found with ID: " + id);
        }
    }

    @Override
    public void delete(Long id) {
        farmRepository.deleteById(id);
    }

    @Override
    public List<FarmResponseDTO> findFarmsByCriteria(String name, String location) {
        List<Farm> farms = farmRepository.findFarmsByCriteria(name, location);
        return farms.stream()
                .map(farmMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}
