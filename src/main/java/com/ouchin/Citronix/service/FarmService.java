package com.ouchin.Citronix.service;

import com.ouchin.Citronix.dto.request.FarmRequestDTO;
import com.ouchin.Citronix.dto.respense.FarmResponseDTO;
import com.ouchin.Citronix.service.base.CrudService;

public interface FarmService extends CrudService<Long, FarmRequestDTO, FarmResponseDTO> {
}
