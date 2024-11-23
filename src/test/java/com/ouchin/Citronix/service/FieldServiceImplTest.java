package com.ouchin.Citronix.service;

import com.ouchin.Citronix.entity.Farm;
import com.ouchin.Citronix.entity.Field;
import com.ouchin.Citronix.service.validation.FieldValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FieldServiceImplTest {

    private FieldValidator fieldValidator;
    private Farm farm;
    private Field field;

    @BeforeEach
    void setUp() {
        fieldValidator = new FieldValidator();

        List<Field> fields = new ArrayList<>();
        farm = Farm.builder()
                .id(1L)
                .totalArea(10000.0)
                .fields(fields)
                .build();

        field = Field.builder()
                .id(1L)
                .name("Test Field")
                .area(2000.0)
                .farm(farm)
                .build();
    }

    @Test
    void validateFieldCreation_ShouldPassWithValidField() {
        assertDoesNotThrow(() -> fieldValidator.validateFieldCreation(field, farm));
    }

    @Test
    void validateFieldCreation_ShouldThrowExceptionIfFieldAreaIsTooSmall() {
        field.setArea(500.0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> fieldValidator.validateFieldCreation(field, farm));

        assertEquals("Field area must be at least 1,000 m²", exception.getMessage());
    }

    @Test
    void validateFieldCreation_ShouldThrowExceptionIfFieldExceedsMaxFarmPercentage() {
        field.setArea(6000.0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> fieldValidator.validateFieldCreation(field, farm));

        assertEquals("Field area cannot exceed 50% of the farm's total area", exception.getMessage());
    }


    @Test
    void validateFieldCreation_ShouldThrowExceptionIfMaxFieldCountExceeded() {
        for (int i = 0; i < 10; i++) {
            Field existingField = Field.builder()
                    .id((long) (i + 2))
                    .area(500.0)
                    .farm(farm)
                    .build();
            farm.getFields().add(existingField);
            existingField.setFarm(farm);
        }

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> fieldValidator.validateFieldCreation(field, farm));

        assertEquals("A farm cannot have more than 10 fields", exception.getMessage());
    }

    @Test
    void validateFieldUpdate_ShouldPassIfUpdateIsValid() {
        field.setArea(3000.0);
        assertDoesNotThrow(() -> fieldValidator.validateFieldUpdate(field, farm));
    }

    @Test
    void validateFieldUpdate_ShouldThrowExceptionIfUpdatedAreaIsTooSmall() {
        field.setArea(500.0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> fieldValidator.validateFieldUpdate(field, farm));

        assertEquals("Field area must be at least 1,000 m²", exception.getMessage());
    }

    @Test
    void validateFieldUpdate_ShouldThrowExceptionIfUpdatedFieldExceedsMaxFarmPercentage() {
        field.setArea(6000.0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> fieldValidator.validateFieldUpdate(field, farm));

        assertEquals("Field area cannot exceed 50% of the farm's total area", exception.getMessage());
    }

    @Test
    void validateFieldUpdate_ShouldThrowExceptionIfUpdatedFieldExceedsFarmTotalArea() {
        Field existingField = Field.builder()
                .id(2L)
                .area(8000.0)
                .farm(farm)
                .build();

        farm.getFields().add(existingField);
        existingField.setFarm(farm);

        farm.getFields().add(field);
        field.setFarm(farm);


        field.setArea(3000.0);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> fieldValidator.validateFieldUpdate(field, farm));

        assertEquals("Total field area would exceed farm's total area", exception.getMessage());
    }
}