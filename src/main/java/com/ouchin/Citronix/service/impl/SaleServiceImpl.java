package com.ouchin.Citronix.service.impl;

import com.ouchin.Citronix.dto.request.SaleRequestDTO;
import com.ouchin.Citronix.dto.respense.SaleResponseDTO;
import com.ouchin.Citronix.entity.Harvest;
import com.ouchin.Citronix.entity.Sale;
import com.ouchin.Citronix.mapper.SaleMapper;
import com.ouchin.Citronix.repository.HarvestRepository;
import com.ouchin.Citronix.repository.SaleRepository;
import com.ouchin.Citronix.service.SaleService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final HarvestRepository harvestRepository;
    private final SaleMapper saleMapper;

    @Override
    @Transactional
    public SaleResponseDTO createSale(SaleRequestDTO requestDTO) {
        // Map SaleRequestDTO to Sale entity
        Sale sale = saleMapper.toEntity(requestDTO);

        // Fetch the associated Harvest
        Harvest harvest = harvestRepository.findById(requestDTO.getHarvestId())
                .orElseThrow(() -> new EntityNotFoundException("Harvest not found"));

        // Ensure the requested sale quantity does not exceed available quantity
        if (harvest.getTotalQuantity() < requestDTO.getQuantity()) {
            throw new IllegalArgumentException("Insufficient quantity available in harvest");
        }

        // Set the harvest in the Sale entity
        sale.setHarvest(harvest);

        // Save the sale entity
        Sale savedSale = saleRepository.save(sale);

        // Map the saved Sale entity to SaleResponseDTO and return it
        return saleMapper.toDTO(savedSale);
    }


    @Override
    public SaleResponseDTO getSaleById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sale not found"));
        return saleMapper.toDTO(sale);
    }

    @Override
    public List<SaleResponseDTO> getAllSales() {
        return saleRepository.findAll().stream()
                .map(saleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SaleResponseDTO updateSale(Long id, SaleRequestDTO requestDTO) {
        Sale existingSale = saleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sale not found"));

        Double quantityDifference = requestDTO.getQuantity() - existingSale.getQuantity();

        Harvest harvest = harvestRepository.findById(requestDTO.getHarvestId())
                .orElseThrow(() -> new EntityNotFoundException("Harvest not found"));

        if (harvest.getTotalQuantity() < quantityDifference) {
            throw new IllegalArgumentException("Insufficient quantity available in harvest");
        }

        existingSale.setPrixUnitaire(requestDTO.getPrixUnitaire());
        existingSale.setSaleDate(requestDTO.getSaleDate());
        existingSale.setQuantity(requestDTO.getQuantity());
        existingSale.setClientName(requestDTO.getClientName());

        harvest.setTotalQuantity(harvest.getTotalQuantity() - quantityDifference);
        harvestRepository.save(harvest);

        Sale updatedSale = saleRepository.save(existingSale);
        return saleMapper.toDTO(updatedSale);
    }

    @Override
    @Transactional
    public void deleteSale(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sale not found"));
        Harvest harvest = sale.getHarvest();
        harvest.setTotalQuantity(harvest.getTotalQuantity() + sale.getQuantity());
        harvestRepository.save(harvest);

        saleRepository.delete(sale);
    }
}
