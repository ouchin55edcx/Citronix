package com.ouchin.Citronix.mapper;

import com.ouchin.Citronix.dto.request.HarvestDetailsRequestDTO;
import com.ouchin.Citronix.dto.request.HarvestRequestDTO;
import com.ouchin.Citronix.dto.respense.HarvestDetailsResponseDTO;
import com.ouchin.Citronix.dto.respense.HarvestResponseDTO;
import com.ouchin.Citronix.entity.Harvest;
import com.ouchin.Citronix.entity.HarvestDetails;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface HarvestMapper {

    @Mapping(target = "harvestDetails", source = "harvestDetails")
    HarvestResponseDTO toResponseDTO(Harvest harvest);

    Harvest toEntity(HarvestRequestDTO requestDTO);

    @Mapping(target = "harvestId", source = "details.id.harvestId")
    @Mapping(target = "treeId", source = "details.id.treeId")
    HarvestDetailsResponseDTO toResponseDTO(HarvestDetails details);

    HarvestDetails toEntity(HarvestDetailsRequestDTO requestDTO);

    List<HarvestDetailsResponseDTO> toHarvestDetailsResponseDTOList(List<HarvestDetails> details);
}
