package com.ouchin.Citronix.controller;

import com.ouchin.Citronix.dto.request.FarmRequestDTO;
import com.ouchin.Citronix.dto.respense.FarmResponseDTO;
import com.ouchin.Citronix.service.FarmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get all farms\", description = \"Retrieve a list of all farms.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of farms"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @GetMapping
    public ResponseEntity<List<FarmResponseDTO>> getAllFarms() {
        List<FarmResponseDTO> farms = farmService.findAll();
        return ResponseEntity.ok(farms);
    }


    @Operation(summary = "Get farm by ID", description = "Retrieve a farm based on the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the farm"),
            @ApiResponse(responseCode = "404", description = "Farm not found with the provided ID")
    })

    @GetMapping("/{id}")
    public ResponseEntity<FarmResponseDTO> getFarmById(@PathVariable Long id) {
        FarmResponseDTO farm = farmService.findById(id);
        if (farm == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(farm);
    }


    @Operation(summary = "Create a new farm", description = "Create a new farm using the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the farm"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    @PostMapping
    public ResponseEntity<FarmResponseDTO> createFarm(@RequestBody FarmRequestDTO farmRequestDTO) {
        FarmResponseDTO createdFarm = farmService.create(farmRequestDTO);
        return new ResponseEntity<>(createdFarm, HttpStatus.CREATED);
    }


    @Operation(summary = "Update a farm", description = "Update an existing farm by ID with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the farm"),
            @ApiResponse(responseCode = "404", description = "Farm not found with the provided ID"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    @PutMapping("/{id}")
    public ResponseEntity<FarmResponseDTO> updateFarm(
            @PathVariable Long id,
            @RequestBody FarmRequestDTO farmRequestDTO) {
        FarmResponseDTO updatedFarm = farmService.update(id, farmRequestDTO);
        return ResponseEntity.ok(updatedFarm);
    }



    @Operation(summary = "Delete a farm", description = "Delete an existing farm by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the farm"),
            @ApiResponse(responseCode = "404", description = "Farm not found with the provided ID")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFarm(@PathVariable Long id) {
        farmService.delete(id);
        return ResponseEntity.noContent().build();
    }



    @Operation(summary = "Search farms", description = "Search farms based on the provided name and/or location.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of farms"),
            @ApiResponse(responseCode = "400", description = "Invalid search parameters")
    })
    @GetMapping("/search")
    public ResponseEntity<List<FarmResponseDTO>> searchFarms(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location) {
        List<FarmResponseDTO> farms = farmService.findFarmsByCriteria(name, location);
        return ResponseEntity.ok(farms);
    }


}
