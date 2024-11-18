package com.ouchin.Citronix.mapper;

import com.ouchin.Citronix.dto.request.FieldRequestDTO;
import com.ouchin.Citronix.dto.respense.FieldResponseDTO;
import com.ouchin.Citronix.entity.Field;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FieldMapper {
    FieldResponseDTO toResponseDTO(Field field);

    List<FieldResponseDTO> toResponseDTOs(List<Field> fields);

    Field toEntity(FieldRequestDTO fieldRequestDTO);
}