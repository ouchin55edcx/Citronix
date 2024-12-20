package com.ouchin.Citronix.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Month;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TreeRequestDTO {

    @NotNull(message = "Planting date is required")
    @PastOrPresent(message = "Planting date must be in the past or present")
    private LocalDate plantingDate;

    @NotNull(message = "Field ID is required")
    private Long fieldId;

    @AssertTrue(message = "Trees can only be planted between March and May")
    public boolean isValidPlantingSeason() {
        if (plantingDate == null) return false;
        Month month = plantingDate.getMonth();
        return month == Month.MARCH || month == Month.APRIL || month == Month.MAY;
    }
}
