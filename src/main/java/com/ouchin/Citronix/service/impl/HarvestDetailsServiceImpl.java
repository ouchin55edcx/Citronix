package com.ouchin.Citronix.service.impl;

import com.ouchin.Citronix.dto.request.HarvestDetailsRequestDTO;
import com.ouchin.Citronix.dto.respense.HarvestDetailsResponseDTO;
import com.ouchin.Citronix.entity.Harvest;
import com.ouchin.Citronix.entity.HarvestDetails;
import com.ouchin.Citronix.entity.Tree;
import com.ouchin.Citronix.entity.embedded.HarvestDetailsId;
import com.ouchin.Citronix.entity.enums.Season;
import com.ouchin.Citronix.mapper.HarvestMapper;
import com.ouchin.Citronix.repository.HarvestDetailsRepository;
import com.ouchin.Citronix.repository.HarvestRepository;
import com.ouchin.Citronix.repository.TreeRepository;
import com.ouchin.Citronix.service.HarvestDetailsService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class HarvestDetailsServiceImpl implements HarvestDetailsService {

    private final HarvestDetailsRepository harvestDetailsRepository;
    private final HarvestRepository harvestRepository;
    private final TreeRepository treeRepository;
    private final HarvestMapper harvestDetailsMapper;


    @Override
    public List<HarvestDetailsResponseDTO> findAll() {
        List<HarvestDetails> harvestDetails = harvestDetailsRepository.findAll();
        return harvestDetailsMapper.toHarvestDetailsResponseDTOList(harvestDetails);
    }

    @Override
    public HarvestDetailsResponseDTO findById(HarvestDetailsId id) {
        HarvestDetails harvestDetails = harvestDetailsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Harvest details not found with id: " + id));
        return harvestDetailsMapper.toResponseDTO(harvestDetails);
    }

    @Override
    public HarvestDetailsResponseDTO create(HarvestDetailsRequestDTO requestDTO) {
        Harvest harvest = harvestRepository.findById(requestDTO.getHarvestId())
                .orElseThrow(() -> new IllegalArgumentException("Harvest not found"));

        Tree tree = treeRepository.findById(requestDTO.getTreeId())
                .orElseThrow(() -> new IllegalArgumentException("Tree not found"));

        HarvestDetailsId id = new HarvestDetailsId(harvest.getId(), tree.getId());

        HarvestDetails details = HarvestDetails.builder()
                .id(id)
                .harvest(harvest)
                .tree(tree)
                .quantity(requestDTO.getQuantity())
                .build();

        HarvestDetails savedDetails = harvestDetailsRepository.save(details);

        return harvestDetailsMapper.toResponseDTO(savedDetails);
    }

    @Override
    public HarvestDetailsResponseDTO update(HarvestDetailsId id, HarvestDetailsRequestDTO requestDTO) {
        HarvestDetails existingDetails = harvestDetailsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Harvest details not found"));

        double quantityDifference = requestDTO.getQuantity() - existingDetails.getQuantity();

        updateHarvestTotalQuantity(existingDetails.getHarvest(), quantityDifference);

        existingDetails.setQuantity(requestDTO.getQuantity());
        existingDetails = harvestDetailsRepository.save(existingDetails);

        return harvestDetailsMapper.toResponseDTO(existingDetails);
    }

    @Override
    public void delete(HarvestDetailsId id) {
        HarvestDetails details = harvestDetailsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Harvest details not found"));

        updateHarvestTotalQuantity(details.getHarvest(), -details.getQuantity());

        harvestDetailsRepository.delete(details);
    }

    private void updateHarvestTotalQuantity(Harvest harvest, double quantityChange) {
        double newTotal = harvest.getTotalQuantity() + quantityChange;
        if (newTotal < 0) {
            throw new IllegalArgumentException("Total harvest quantity cannot be negative");
        }
        harvest.setTotalQuantity(newTotal);
        harvestRepository.save(harvest);
    }
}


