package com.ouchin.Citronix.service.impl;

import com.ouchin.Citronix.dto.request.HarvestRequestDTO;
import com.ouchin.Citronix.dto.respense.HarvestResponseDTO;
import com.ouchin.Citronix.service.HarvestService;

import java.util.List;

public class HarvestServiceImpl implements HarvestService {
    @Override
    public List<HarvestResponseDTO> findAll() {
        return List.of();
    }

    @Override
    public HarvestResponseDTO findById(Long aLong) {
        return null;
    }

    @Override
    public HarvestResponseDTO create(HarvestRequestDTO harvestRequestDTO) {
        return null;
    }

    @Override
    public HarvestResponseDTO update(Long aLong, HarvestRequestDTO harvestRequestDTO) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
