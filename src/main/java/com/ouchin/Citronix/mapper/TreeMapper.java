package com.ouchin.Citronix.mapper;

import com.ouchin.Citronix.dto.request.TreeRequestDTO;
import com.ouchin.Citronix.dto.respense.TreeResponseDTO;
import com.ouchin.Citronix.entity.Tree;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TreeMapper {

    TreeResponseDTO toResponseDTO(Tree tree);

    List<TreeResponseDTO> toResponseDTOs(List<Tree> trees);

    Tree toEntity(TreeRequestDTO treeRequestDTO);

}