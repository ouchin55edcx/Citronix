package com.ouchin.Citronix.mapper;

import com.ouchin.Citronix.dto.request.FarmRequestDTO;
import com.ouchin.Citronix.dto.respense.FarmResponseDTO;
import com.ouchin.Citronix.entity.Farm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FarmMapper {

    @Mapping(target = "fields", source = "fields")
    @Mapping(target = "fieldsTotalArea", expression = "java(farm.calculateFieldsTotalArea())")
    @Mapping(target = "availableArea", expression = "java(farm.getAvailableArea())")
    FarmResponseDTO toResponseDTO(Farm farm);

    List<FarmResponseDTO> toResponseDTOs(List<Farm> farms);

    Farm toEntity(FarmRequestDTO farmRequestDTO);

    void updateFarmFromDto(FarmRequestDTO farmRequestDto, @MappingTarget Farm farm);
}
