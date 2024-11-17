package com.ouchin.Citronix.controller;

import com.ouchin.Citronix.dto.request.FarmRequestDTO;
import com.ouchin.Citronix.dto.respense.FarmResponseDTO;
import com.ouchin.Citronix.service.FarmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/farms")
public class FarmController {

    @Autowired
    private FarmService farmService;

    @Operation(
            summary = "Retrieve all farms",
            description = "Get a list of all farms in the system.",
            tags = { "farm", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful retrieval of farms",
                    content = @Content(schema = @Schema(implementation = FarmResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<FarmResponseDTO>> getAllFarms() {
        List<FarmResponseDTO> farms = farmService.findAll();
        return ResponseEntity.ok(farms);
    }


    @Operation(
            summary = "Get a farm by ID",
            description = "Retrieve a specific farm using its ID.",
            tags = { "farm", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successful retrieval of farm",
                    content = @Content(schema = @Schema(implementation = FarmResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Farm not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<FarmResponseDTO> getFarmById(@PathVariable Long id) {
        FarmResponseDTO farm = farmService.findById(id);
        if (farm == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(farm);
    }

    @Operation(
            summary = "Create a new farm",
            description = "Create a new farm with the provided details.",
            tags = { "farm", "post" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Farm created successfully",
                    content = @Content(schema = @Schema(implementation = FarmResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<FarmResponseDTO> createFarm(@RequestBody FarmRequestDTO farmRequestDTO) {
        FarmResponseDTO createdFarm = farmService.create(farmRequestDTO);
        return new ResponseEntity<>(createdFarm, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update a farm",
            description = "Update an existing farm's details by its ID.",
            tags = { "farm", "put" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Farm updated successfully",
                    content = @Content(schema = @Schema(implementation = FarmResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Farm not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @PutMapping("/{id}")
    public ResponseEntity<FarmResponseDTO> updateFarm(
            @PathVariable Long id,
            @RequestBody FarmRequestDTO farmRequestDTO) {
        try {
            FarmResponseDTO updatedFarm = farmService.update(id, farmRequestDTO);
            return ResponseEntity.ok(updatedFarm);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @Operation(
            summary = "Delete a farm",
            description = "Delete a farm by its ID.",
            tags = { "farm", "delete" })
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Farm deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Farm not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFarm(@PathVariable Long id) {
        try {
            farmService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


}
