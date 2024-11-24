package com.ouchin.Citronix.service.validation;

import com.ouchin.Citronix.dto.request.HarvestRequestDTO;
import com.ouchin.Citronix.entity.Harvest;
import com.ouchin.Citronix.entity.enums.Season;
import com.ouchin.Citronix.repository.HarvestRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class HarvestValidator {

    private final HarvestRepository harvestRepository;

    public HarvestValidator(HarvestRepository harvestRepository) {
        this.harvestRepository = harvestRepository;
    }

    public void validateNewHarvest(HarvestRequestDTO requestDTO) {
        List<Harvest> existingHarvests = harvestRepository.findBySeason(requestDTO.getSeason());
        if (!existingHarvests.isEmpty()) {
            throw new IllegalStateException("A harvest already exists for this season");
        }

        validateHarvestDate(requestDTO.getHarvestDate(), requestDTO.getSeason());
    }

    public void validateSeasonForUpdate(Season newSeason, Long currentHarvestId) {
        List<Harvest> existingHarvests = harvestRepository.findBySeason(newSeason);

        boolean seasonExistsForOtherHarvest = existingHarvests.stream()
                .anyMatch(harvest -> !harvest.getId().equals(currentHarvestId));

        if (seasonExistsForOtherHarvest) {
            throw new IllegalStateException("A harvest already exists for this season");
        }
    }

    public void validateHarvestDate(LocalDate harvestDate, Season season) {
        if (harvestDate == null) {
            throw new IllegalArgumentException("Harvest date cannot be null");
        }

        if (harvestDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Harvest date cannot be in the future");
        }

        if (!isDateMatchingSeason(harvestDate, season)) {
            throw new IllegalArgumentException("Harvest date does not match the specified season");
        }
    }

    private boolean isDateMatchingSeason(LocalDate date, Season season) {
        int month = date.getMonthValue();
        return switch (season) {
            case WINTER -> month == 12 || month == 1 || month == 2;
            case SPRING -> month >= 3 && month <= 5;
            case SUMMER -> month >= 6 && month <= 8;
            case AUTUMN -> month >= 9 && month <= 11;
        };
    }
}
