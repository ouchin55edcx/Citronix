package com.ouchin.Citronix.mapper;

import com.ouchin.Citronix.dto.request.SaleRequestDTO;
import com.ouchin.Citronix.dto.respense.SaleResponseDTO;
import com.ouchin.Citronix.entity.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleMapper {
    @Mapping(target = "harvest", ignore = true)
    @Mapping(target = "id", ignore = true)
    Sale toEntity(SaleRequestDTO dto);

    @Mapping(target = "revenue", source = "revenue")
    @Mapping(target = "harvestId", source = "harvest.id")
    @Mapping(target = "harvestDate", source = "harvest.harvestDate")
    @Mapping(target = "season", source = "harvest.season")
    @Mapping(target = "saleQuantity", source = "quantity")
    SaleResponseDTO toDTO(Sale sale);

}

