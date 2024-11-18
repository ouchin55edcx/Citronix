package com.ouchin.Citronix.service.impl;

import com.ouchin.Citronix.dto.request.TreeRequestDTO;
import com.ouchin.Citronix.dto.respense.TreeResponseDTO;
import com.ouchin.Citronix.service.TreeService;

import java.util.List;

public class TreeServiceImpl implements TreeService {
    @Override
    public List<TreeResponseDTO> findAll() {
        return List.of();
    }

    @Override
    public TreeResponseDTO findById(Long aLong) {
        return null;
    }

    @Override
    public TreeResponseDTO create(TreeRequestDTO treeRequestDTO) {
        return null;
    }

    @Override
    public TreeResponseDTO update(Long aLong, TreeRequestDTO treeRequestDTO) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
