package com.ouchin.Citronix.service.impl;

import com.ouchin.Citronix.dto.request.FarmRequestDTO;
import com.ouchin.Citronix.dto.respense.FarmResponseDTO;
import com.ouchin.Citronix.entity.Farm;
import com.ouchin.Citronix.mapper.FarmMapper;
import com.ouchin.Citronix.repository.FarmRepository;
import com.ouchin.Citronix.service.FarmService;
import com.ouchin.Citronix.service.validation.FarmValidator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FarmServiceImpl implements FarmService {

    private final FarmRepository farmRepository;
    private final FarmMapper farmMapper;
    private final FarmValidator farmValidator;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<FarmResponseDTO> findAll() {
        List<Farm> farms = farmRepository.findAll();
        return farmMapper.toResponseDTOs(farms);
    }


    @Override
    public FarmResponseDTO findById(Long id) {
        return farmRepository.findById(id)
            .map(farmMapper::toResponseDTO)
                .orElseThrow(() -> new EntityNotFoundException("Farm not found with ID: " + id));
    }


    @Transactional
    @Override
    public FarmResponseDTO create(FarmRequestDTO farmRequestDTO) {
        Farm farm = farmMapper.toEntity(farmRequestDTO);

        farm.setFields(new ArrayList<>());

        farmValidator.validateFarm(farm);

        Farm savedFarm = farmRepository.save(farm);
        return farmMapper.toResponseDTO(savedFarm);
    }


    @Transactional
    @Override
    public FarmResponseDTO update(Long id, FarmRequestDTO farmRequestDTO) {
        Farm existingFarm = farmRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Farm not found with ID: " + id));

        farmMapper.updateFarmFromDto(farmRequestDTO, existingFarm);

        farmValidator.validateFarm(existingFarm);

        Farm updatedFarm = farmRepository.save(existingFarm);
        return farmMapper.toResponseDTO(updatedFarm);
    }


    @Override
    public void delete(Long id) {
        if (!farmRepository.existsById(id)) {
            throw new EntityNotFoundException("Farm not found with ID: " + id);
        }
        farmRepository.deleteById(id);
    }


    @Override
    public List<FarmResponseDTO> findFarmsByCriteria(String name, String location) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Farm> query = cb.createQuery(Farm.class);
        Root<Farm> root = query.from(Farm.class);

        Predicate namePredicate = name == null ? cb.conjunction() : cb.like(root.get("name"), "%" + name + "%");
        Predicate locationPredicate = location == null ? cb.conjunction() : cb.like(root.get("location"), "%" + location + "%");

        query.select(root).where(cb.and(namePredicate, locationPredicate));

        List<Farm> farms = entityManager.createQuery(query).getResultList();

        return farms.stream().map(farmMapper::toResponseDTO).collect(Collectors.toList());
    }


}