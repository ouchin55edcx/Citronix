package com.ouchin.Citronix.controller;

import com.ouchin.Citronix.dto.request.HarvestDetailsRequestDTO;
import com.ouchin.Citronix.dto.respense.HarvestDetailsResponseDTO;
import com.ouchin.Citronix.entity.embedded.HarvestDetailsId;
import com.ouchin.Citronix.service.HarvestDetailsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/harvest-details")
@RequiredArgsConstructor
public class HarvestDetailsController {

    private final HarvestDetailsService harvestDetailsService;

    @GetMapping
    public ResponseEntity<List<HarvestDetailsResponseDTO>> getAllHarvestDetails() {
        List<HarvestDetailsResponseDTO> detailsList = harvestDetailsService.findAll();
        return ResponseEntity.ok(detailsList);
    }

    @GetMapping("/{harvestId}/{treeId}")
    public ResponseEntity<HarvestDetailsResponseDTO> getHarvestDetailsById(
            @PathVariable Long harvestId,
            @PathVariable Long treeId) {

        HarvestDetailsId id = new HarvestDetailsId(harvestId, treeId);
        HarvestDetailsResponseDTO details = harvestDetailsService.findById(id);
        return ResponseEntity.ok(details);
    }

    @PostMapping
    public ResponseEntity<HarvestDetailsResponseDTO> createHarvestDetails(
            @RequestBody @Valid HarvestDetailsRequestDTO requestDTO) {

        HarvestDetailsResponseDTO createdDetails = harvestDetailsService.create(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDetails);
    }

    @PutMapping("/{harvestId}/{treeId}")
    public ResponseEntity<HarvestDetailsResponseDTO> updateHarvestDetails(
            @PathVariable Long harvestId,
            @PathVariable Long treeId,
            @RequestBody @Valid HarvestDetailsRequestDTO requestDTO) {

        HarvestDetailsId id = new HarvestDetailsId(harvestId, treeId);
        HarvestDetailsResponseDTO updatedDetails = harvestDetailsService.update(id, requestDTO);
        return ResponseEntity.ok(updatedDetails);
    }

    @DeleteMapping("/{harvestId}/{treeId}")
    public ResponseEntity<Void> deleteHarvestDetails(
            @PathVariable Long harvestId,
            @PathVariable Long treeId) {

        HarvestDetailsId id = new HarvestDetailsId(harvestId, treeId);
        harvestDetailsService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
