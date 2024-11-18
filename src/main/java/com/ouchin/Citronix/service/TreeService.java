package com.ouchin.Citronix.service;

import com.ouchin.Citronix.dto.request.TreeRequestDTO;
import com.ouchin.Citronix.dto.respense.TreeResponseDTO;
import com.ouchin.Citronix.service.base.CrudService;

public interface TreeService extends CrudService<Long, TreeRequestDTO, TreeResponseDTO> {
}
