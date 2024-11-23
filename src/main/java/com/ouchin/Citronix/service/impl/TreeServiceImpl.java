package com.ouchin.Citronix.service.impl;

import com.ouchin.Citronix.dto.request.TreeRequestDTO;
import com.ouchin.Citronix.dto.respense.TreeResponseDTO;
import com.ouchin.Citronix.entity.Field;
import com.ouchin.Citronix.entity.Tree;
import com.ouchin.Citronix.mapper.TreeMapper;
import com.ouchin.Citronix.repository.FieldRepository;
import com.ouchin.Citronix.repository.TreeRepository;
import com.ouchin.Citronix.service.TreeService;
import com.ouchin.Citronix.service.validation.TreeValidator;
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
    private final TreeValidator treeValidator;

    @Override
    public List<TreeResponseDTO> findAll() {
        return treeRepository.findAll().stream()
                .map(treeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TreeResponseDTO findById(Long id) {
        Tree tree = treeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tree not found with ID: " + id));
        return treeMapper.toResponseDTO(tree);
    }

    @Override
    public TreeResponseDTO create(TreeRequestDTO treeRequestDTO) {
        treeValidator.validateTreeRequest(treeRequestDTO);

        Field field = fieldRepository.findById(treeRequestDTO.getFieldId())
                .orElseThrow(() -> new IllegalArgumentException("Field not found with ID: " + treeRequestDTO.getFieldId()));

        Tree tree = treeMapper.toEntity(treeRequestDTO);
        tree.setField(field);

        Tree savedTree = treeRepository.save(tree);

        return treeMapper.toResponseDTO(savedTree);
    }

    @Override
    public TreeResponseDTO update(Long id, TreeRequestDTO treeRequestDTO) {
        treeValidator.validateTreeRequest(treeRequestDTO);

        Tree existingTree = treeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tree not found with ID: " + id));

        Field field = fieldRepository.findById(treeRequestDTO.getFieldId())
                .orElseThrow(() -> new IllegalArgumentException("Field not found with ID: " + treeRequestDTO.getFieldId()));

        existingTree.setPlantingDate(treeRequestDTO.getPlantingDate());
        existingTree.setField(field);

        Tree updatedTree = treeRepository.save(existingTree);

        return treeMapper.toResponseDTO(updatedTree);
    }

    @Override
    public void delete(Long id) {
        Tree tree = treeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tree not found with ID: " + id));
        treeRepository.delete(tree);
    }
}
