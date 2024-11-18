package com.ouchin.Citronix.service.impl;

import com.ouchin.Citronix.dto.request.TreeRequestDTO;
import com.ouchin.Citronix.dto.respense.TreeResponseDTO;
import com.ouchin.Citronix.entity.Field;
import com.ouchin.Citronix.entity.Tree;
import com.ouchin.Citronix.mapper.TreeMapper;
import com.ouchin.Citronix.repository.FieldRepository;
import com.ouchin.Citronix.repository.TreeRepository;
import com.ouchin.Citronix.service.TreeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor

public class TreeServiceImpl implements TreeService {

    private final TreeRepository treeRepository;
    private final TreeMapper treeMapper;
    private final FieldRepository fieldRepository;


    @Override
    public List<TreeResponseDTO> findAll() {
        List<Tree> trees = treeRepository.findAll();

        List<TreeResponseDTO> responseDTOs = trees.stream()
                .map(tree -> {
                    TreeResponseDTO responseDTO = treeMapper.toResponseDTO(tree);
                    responseDTO.calculateAgeAndProductivity();
                    return responseDTO;
                })
                .collect(Collectors.toList());

        return responseDTOs;
    }

    @Override
    public TreeResponseDTO findById(Long id) {
        Tree tree = treeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tree not found with ID: " + id));
        TreeResponseDTO responseDTO = treeMapper.toResponseDTO(tree);
        responseDTO.calculateAgeAndProductivity();
        return responseDTO;
    }

    @Override
    public TreeResponseDTO create(TreeRequestDTO treeRequestDTO) {
        validateTreeRequest(treeRequestDTO);

        Field field = fieldRepository.findById(treeRequestDTO.getFieldId())
                .orElseThrow(() -> new IllegalArgumentException("Field not found with ID: " + treeRequestDTO.getFieldId()));

        Tree tree = treeMapper.toEntity(treeRequestDTO);
        tree.setField(field);

        Tree savedTree = treeRepository.save(tree);

        TreeResponseDTO responseDTO = treeMapper.toResponseDTO(savedTree);
        responseDTO.calculateAgeAndProductivity();

        return responseDTO;
    }

    @Override
    public TreeResponseDTO update(Long id, TreeRequestDTO treeRequestDTO) {
        validateTreeRequest(treeRequestDTO);

        Tree existingTree = treeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tree not found with ID: " + id));

        Field field = fieldRepository.findById(treeRequestDTO.getFieldId())
                .orElseThrow(() -> new IllegalArgumentException("Field not found with ID: " + treeRequestDTO.getFieldId()));

        existingTree.setPlantingDate(treeRequestDTO.getPlantingDate());
        existingTree.setField(field);

        Tree updatedTree = treeRepository.save(existingTree);

        TreeResponseDTO responseDTO = treeMapper.toResponseDTO(updatedTree);
        responseDTO.calculateAgeAndProductivity();

        return responseDTO;
    }

    @Override
    public void delete(Long id) {
        Tree tree = treeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tree not found with id: " + id));

        treeRepository.delete(tree);
    }

    private void validateTreeRequest(TreeRequestDTO treeRequestDTO) {
        if (!treeRequestDTO.isValidPlantingPeriod()) {
            throw new IllegalArgumentException("Trees can only be planted between March and May");
        }

        if (!treeRequestDTO.isValidTreeAge()) {
            throw new IllegalArgumentException("Tree age must not exceed 20 years");
        }
    }
}
