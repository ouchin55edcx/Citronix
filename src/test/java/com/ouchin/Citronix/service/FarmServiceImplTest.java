package com.ouchin.Citronix.service;

import com.ouchin.Citronix.dto.request.FarmRequestDTO;
import com.ouchin.Citronix.dto.respense.FarmResponseDTO;
import com.ouchin.Citronix.entity.Farm;
import com.ouchin.Citronix.mapper.FarmMapper;
import com.ouchin.Citronix.repository.FarmRepository;
import com.ouchin.Citronix.service.impl.FarmServiceImpl;
import com.ouchin.Citronix.service.validation.FarmValidator;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FarmServiceImplTest {

    @Mock
    private FarmRepository farmRepository;

    @Mock
    private EntityManager entityManager;


    @Mock
    private FarmMapper farmMapper;

    @Mock
    private FarmValidator farmValidator;

    @InjectMocks
    private FarmServiceImpl farmService;

    private FarmRequestDTO validFarmRequest;
    private Farm mappedFarm;
    private Farm savedFarm;
    private FarmResponseDTO expectedResponse;

    @BeforeEach
    void setUp() {
        validFarmRequest = new FarmRequestDTO();
        validFarmRequest.setName("Green Valley Farm");
        validFarmRequest.setLocation("California");
        validFarmRequest.setTotalArea(100.0);
        validFarmRequest.setCreationDate(LocalDate.now());

        mappedFarm = Farm.builder()
                .name("Green Valley Farm")
                .location("California")
                .totalArea(100.0)
                .creationDate(LocalDate.now())
                .fields(new ArrayList<>())
                .build();

        savedFarm = Farm.builder()
                .id(1L)
                .name("Green Valley Farm")
                .location("California")
                .totalArea(100.0)
                .creationDate(LocalDate.now())
                .fields(new ArrayList<>())
                .build();

        expectedResponse = new FarmResponseDTO();
        expectedResponse.setId(1L);
        expectedResponse.setName("Green Valley Farm");
        expectedResponse.setLocation("California");
        expectedResponse.setTotalArea(100.0);
        expectedResponse.setCreationDate(LocalDate.now());
        expectedResponse.setFields(new ArrayList<>());
        expectedResponse.setFieldsTotalArea(0.0);
        expectedResponse.setAvailableArea(100.0);
    }

    @Test
    void createFarm_SuccessfulCreation() {
        when(farmMapper.toEntity(validFarmRequest)).thenReturn(mappedFarm);
        when(farmRepository.save(any(Farm.class))).thenReturn(savedFarm);
        when(farmMapper.toResponseDTO(savedFarm)).thenReturn(expectedResponse);
        doNothing().when(farmValidator).validateFarm(any(Farm.class));

        FarmResponseDTO result = farmService.create(validFarmRequest);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Green Valley Farm");
        assertThat(result.getTotalArea()).isEqualTo(100.0);
        assertThat(result.getFields()).isEmpty();
        assertThat(result.getAvailableArea()).isEqualTo(100.0);

        verify(farmMapper).toEntity(validFarmRequest);
        verify(farmValidator).validateFarm(any(Farm.class));
        verify(farmRepository).save(any(Farm.class));
        verify(farmMapper).toResponseDTO(savedFarm);
    }

    @Test
    void createFarm_WithExistingName_ThrowsException() {
        when(farmMapper.toEntity(validFarmRequest)).thenReturn(mappedFarm);
        doThrow(new IllegalStateException("Farm with name 'Green Valley Farm' already exists"))
                .when(farmValidator).validateFarm(any(Farm.class));

        assertThatThrownBy(() -> farmService.create(validFarmRequest))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("Farm with name 'Green Valley Farm' already exists");

        verify(farmRepository, never()).save(any(Farm.class));
    }

}
