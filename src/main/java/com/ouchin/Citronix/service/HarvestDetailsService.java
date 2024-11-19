package com.ouchin.Citronix.service;

import com.ouchin.Citronix.dto.request.HarvestDetailsRequestDTO;
import com.ouchin.Citronix.dto.respense.HarvestDetailsResponseDTO;
import com.ouchin.Citronix.entity.embedded.HarvestDetailsId;

import java.util.List;


public interface HarvestDetailsService {
    List<HarvestDetailsResponseDTO> findAll();

    HarvestDetailsResponseDTO findById(HarvestDetailsId id);

    HarvestDetailsResponseDTO create(HarvestDetailsRequestDTO requestDTO);

    HarvestDetailsResponseDTO update(HarvestDetailsId id, HarvestDetailsRequestDTO requestDTO);

    void delete(HarvestDetailsId id);
}
