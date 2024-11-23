package com.ouchin.Citronix.controller;

import com.ouchin.Citronix.dto.request.TreeRequestDTO;
import com.ouchin.Citronix.dto.respense.TreeResponseDTO;
import com.ouchin.Citronix.service.TreeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trees")
@RequiredArgsConstructor
public class TreeController {

    private final TreeService treeService;

    @Operation(summary = "Get all trees", description = "Retrieve a list of all trees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of trees")
    })
    @GetMapping
    public ResponseEntity<List<TreeResponseDTO>> getAllTrees() {
        List<TreeResponseDTO> trees = treeService.findAll();
        return ResponseEntity.ok(trees);
    }

    @Operation(summary = "Get tree by ID", description = "Retrieve a tree by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the tree"),
            @ApiResponse(responseCode = "404", description = "Tree not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TreeResponseDTO> getTreeById(@PathVariable Long id) {
        TreeResponseDTO tree = treeService.findById(id);
        return ResponseEntity.ok(tree);
    }

    @Operation(summary = "Create a new tree", description = "Add a new tree to the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tree successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<TreeResponseDTO> createTree(@RequestBody @Valid TreeRequestDTO treeRequestDTO) {
        TreeResponseDTO createdTree = treeService.create(treeRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTree);
    }

    @Operation(summary = "Update a tree", description = "Update an existing tree by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tree successfully updated"),
            @ApiResponse(responseCode = "404", description = "Tree not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<TreeResponseDTO> updateTree(
            @PathVariable Long id,
            @RequestBody @Valid TreeRequestDTO treeRequestDTO) {
        TreeResponseDTO updatedTree = treeService.update(id, treeRequestDTO);
        return ResponseEntity.ok(updatedTree);
    }

    @Operation(summary = "Delete a tree", description = "Delete an existing tree by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tree successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Tree not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTree(@PathVariable Long id) {
        treeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
