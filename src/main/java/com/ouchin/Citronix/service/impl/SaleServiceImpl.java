package com.ouchin.Citronix.service.impl;

import com.ouchin.Citronix.dto.request.SaleRequestDTO;
import com.ouchin.Citronix.dto.respense.SaleResponseDTO;
import com.ouchin.Citronix.service.SaleService;

import java.util.List;

public class SaleServiceImpl implements SaleService {
    @Override
    public SaleResponseDTO createSale(SaleRequestDTO requestDTO) {
        return null;
    }

    @Override
    public SaleResponseDTO getSaleById(Long id) {
        return null;
    }

    @Override
    public List<SaleResponseDTO> getAllSales() {
        return List.of();
    }

    @Override
    public SaleResponseDTO updateSale(Long id, SaleRequestDTO requestDTO) {
        return null;
    }

    @Override
    public void deleteSale(Long id) {

    }
}
