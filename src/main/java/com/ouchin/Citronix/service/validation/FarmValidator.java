package com.ouchin.Citronix.service.validation;


import com.ouchin.Citronix.entity.Farm;
import org.springframework.stereotype.Component;

@Component
public class FarmValidator {

    private static final double MINIMUM_FARM_AREA = 2000.0;

    public void validateFarm(Farm farm) {
        validateFarmArea(farm);
    }

    private void validateFarmArea(Farm farm) {
        if (farm.getTotalArea() < MINIMUM_FARM_AREA) {
            throw new IllegalArgumentException(
                    "Farm total area must be at least 2,000 m²"
            );
        }
    }

}