package com.ouchin.Citronix.controller;

import com.ouchin.Citronix.dto.request.FieldRequestDTO;
import com.ouchin.Citronix.dto.respense.FieldResponseDTO;
import com.ouchin.Citronix.service.FieldService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/fields")
@RequiredArgsConstructor
public class FieldController {

    private final FieldService fieldService;

    @Operation(summary = "Get all fields", description = "Retrieve a list of all fields.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of fields"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<FieldResponseDTO>> getAllFields() {
        return ResponseEntity.ok(fieldService.findAll());
    }

    @Operation(summary = "Get a field by ID", description = "Retrieve a field by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the field"),
            @ApiResponse(responseCode = "404", description = "Field not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<FieldResponseDTO> getFieldById(@PathVariable Long id) {
        FieldResponseDTO field = fieldService.findById(id);
        return ResponseEntity.ok(field);
    }

    @Operation(summary = "Create a new field", description = "Add a new field.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Field successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<FieldResponseDTO> createField(@RequestBody FieldRequestDTO fieldRequestDTO) {
        FieldResponseDTO createdField = fieldService.create(fieldRequestDTO);
        return new ResponseEntity<>(createdField, HttpStatus.CREATED);
    }


    @Operation(summary = "Update a field", description = "Update details of an existing field.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Field successfully updated"),
            @ApiResponse(responseCode = "404", description = "Field not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<FieldResponseDTO> updateField(
            @PathVariable Long id,
            @RequestBody FieldRequestDTO fieldRequestDTO) {
        FieldResponseDTO updatedField = fieldService.update(id, fieldRequestDTO);
        return ResponseEntity.ok(updatedField);
    }


    @Operation(summary = "Delete a field", description = "Remove a field by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Field successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Field not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteField(@PathVariable Long id) {
        fieldService.delete(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Field deleted successfully");
        return ResponseEntity.ok(response);
    }

}
