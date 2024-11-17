package com.ouchin.Citronix.service.impl;

import com.ouchin.Citronix.dto.request.FieldRequestDTO;
import com.ouchin.Citronix.dto.respense.FieldResponseDTO;
import com.ouchin.Citronix.service.FieldService;

import java.util.List;

public class FieldServiceImpl implements FieldService {
    @Override
    public List<FieldResponseDTO> findAll() {
        return List.of();
    }

    @Override
    public FieldResponseDTO findById(Long aLong) {
        return null;
    }

    @Override
    public FieldResponseDTO create(FieldRequestDTO fieldRequestDTO) {
        return null;
    }

    @Override
    public FieldResponseDTO update(Long aLong, FieldRequestDTO fieldRequestDTO) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
