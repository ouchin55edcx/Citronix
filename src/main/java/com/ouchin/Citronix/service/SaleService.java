package com.ouchin.Citronix.service;


import com.ouchin.Citronix.dto.request.SaleRequestDTO;
import com.ouchin.Citronix.dto.respense.SaleResponseDTO;

import java.util.List;

public interface SaleService {
    SaleResponseDTO createSale(SaleRequestDTO requestDTO);

    SaleResponseDTO getSaleById(Long id);

    List<SaleResponseDTO> getAllSales();

    SaleResponseDTO updateSale(Long id, SaleRequestDTO requestDTO);

    void deleteSale(Long id);
}