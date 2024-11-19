package com.ouchin.Citronix.service.impl;

import com.ouchin.Citronix.dto.request.HarvestDetailsRequestDTO;
import com.ouchin.Citronix.dto.respense.HarvestDetailsResponseDTO;
import com.ouchin.Citronix.service.HarvestDetailsService;

import java.util.List;

public class HarvestDetailsServiceImpl implements HarvestDetailsService {
    @Override
    public List<HarvestDetailsResponseDTO> findAll() {
        return List.of();
    }

    @Override
    public HarvestDetailsResponseDTO findById(Long id) {
        return null;
    }

    @Override
    public HarvestDetailsResponseDTO create(HarvestDetailsRequestDTO requestDTO) {
        return null;
    }

    @Override
    public HarvestDetailsResponseDTO update(Long id, HarvestDetailsRequestDTO requestDTO) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
