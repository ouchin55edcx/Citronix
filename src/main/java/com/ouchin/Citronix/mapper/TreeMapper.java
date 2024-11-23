package com.ouchin.Citronix.mapper;

import com.ouchin.Citronix.dto.request.TreeRequestDTO;
import com.ouchin.Citronix.dto.respense.TreeResponseDTO;
import com.ouchin.Citronix.entity.Tree;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TreeMapper {

    @Mapping(target = "productive", expression = "java(tree.isProductive())")
    TreeResponseDTO toResponseDTO(Tree tree);

    Tree toEntity(TreeRequestDTO treeRequestDTO);
}
