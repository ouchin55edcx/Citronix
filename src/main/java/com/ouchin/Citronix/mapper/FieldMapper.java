package com.ouchin.Citronix.mapper;

import com.ouchin.Citronix.dto.request.FieldRequestDTO;
import com.ouchin.Citronix.dto.respense.FieldResponseDTO;
import com.ouchin.Citronix.entity.Field;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FieldMapper {
    FieldResponseDTO toResponseDTO(Field field);

    Field toEntity(FieldRequestDTO fieldRequestDTO);
}