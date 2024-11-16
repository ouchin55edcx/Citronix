package com.ouchin.Citronix.mapper;


import com.ouchin.Citronix.dto.request.FarmRequestDTO;
import com.ouchin.Citronix.entity.Farm;
import org.mapstruct.Mapper;

@Mapper
public interface FarmMapper {

    Farm toEntity(FarmRequestDTO dto);
    FarmRequestDTO toDTO(Farm entity);
}
