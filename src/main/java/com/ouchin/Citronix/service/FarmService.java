package com.ouchin.Citronix.service;

import com.ouchin.Citronix.dto.request.FarmRequestDTO;
import com.ouchin.Citronix.dto.respense.FarmResponseDTO;
import com.ouchin.Citronix.service.base.CrudService;

import java.util.List;

public interface FarmService extends CrudService<Long, FarmRequestDTO, FarmResponseDTO> {

    List<FarmResponseDTO> findFarmsByCriteria(String name, String location);

}
