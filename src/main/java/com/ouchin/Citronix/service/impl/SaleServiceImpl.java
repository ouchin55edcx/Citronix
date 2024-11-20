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
        Sale sale = saleMapper.toEntity(requestDTO);

        Harvest harvest = harvestRepository.findById(requestDTO.getHarvestId())
                .orElseThrow(() -> new EntityNotFoundException("Harvest not found"));

        if (harvest.getTotalQuantity() < requestDTO.getQuantity()) {
            throw new IllegalArgumentException("Insufficient quantity available in harvest");
        }

        sale.setHarvest(harvest);
        harvest.setTotalQuantity(harvest.getTotalQuantity() - requestDTO.getQuantity());
        harvestRepository.save(harvest);

        Sale savedSale = saleRepository.save(sale);
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

