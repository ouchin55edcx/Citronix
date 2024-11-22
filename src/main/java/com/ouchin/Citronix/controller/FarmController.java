package com.ouchin.Citronix.controller;

import com.ouchin.Citronix.dto.request.FarmRequestDTO;
import com.ouchin.Citronix.dto.respense.FarmResponseDTO;
import com.ouchin.Citronix.service.FarmService;
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


    @GetMapping
    public ResponseEntity<List<FarmResponseDTO>> getAllFarms() {
        List<FarmResponseDTO> farms = farmService.findAll();
        return ResponseEntity.ok(farms);
    }


    @GetMapping("/{id}")
    public ResponseEntity<FarmResponseDTO> getFarmById(@PathVariable Long id) {
        FarmResponseDTO farm = farmService.findById(id);
        if (farm == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(farm);
    }



    @PostMapping
    public ResponseEntity<FarmResponseDTO> createFarm(@RequestBody FarmRequestDTO farmRequestDTO) {
        FarmResponseDTO createdFarm = farmService.create(farmRequestDTO);
        return new ResponseEntity<>(createdFarm, HttpStatus.CREATED);
    }



    @PutMapping("/{id}")
    public ResponseEntity<FarmResponseDTO> updateFarm(
            @PathVariable Long id,
            @RequestBody FarmRequestDTO farmRequestDTO) {
        FarmResponseDTO updatedFarm = farmService.update(id, farmRequestDTO);
        return ResponseEntity.ok(updatedFarm);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFarm(@PathVariable Long id) {
        farmService.delete(id);
        return ResponseEntity.noContent().build();
    }




    @GetMapping("/search")
    public ResponseEntity<List<FarmResponseDTO>> searchFarms(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location) {
        List<FarmResponseDTO> farms = farmService.findFarmsByCriteria(name, location);
        return ResponseEntity.ok(farms);
    }


}
