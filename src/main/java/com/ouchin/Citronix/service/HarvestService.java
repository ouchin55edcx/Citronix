package com.ouchin.Citronix.service;

import com.ouchin.Citronix.dto.request.HarvestRequestDTO;
import com.ouchin.Citronix.dto.respense.HarvestResponseDTO;
import com.ouchin.Citronix.service.base.CrudService;

public interface HarvestService extends CrudService<Long, HarvestRequestDTO, HarvestResponseDTO> {
}
