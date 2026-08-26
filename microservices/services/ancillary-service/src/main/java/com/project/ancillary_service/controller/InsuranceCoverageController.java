package com.project.ancillary_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ancillary_service.services.InsuranceCoverageService;
import com.project.payload.request.InsuranceCoverageRequest;
import com.project.payload.response.ApiResponse;
import com.project.payload.response.InsuranceCoverageResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/insurance-coverages")
@RequiredArgsConstructor
public class InsuranceCoverageController {

    private final InsuranceCoverageService insuranceCoverageService;

    @PostMapping
    public ResponseEntity<InsuranceCoverageResponse> createCoverage(
            @Valid @RequestBody InsuranceCoverageRequest insuranceCoverageRequest) throws Exception {
        InsuranceCoverageResponse response = insuranceCoverageService.createCoverage(
                insuranceCoverageRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InsuranceCoverageResponse> updateCoverage(
            @PathVariable Long id,
            @RequestBody InsuranceCoverageRequest request) throws Exception {
        return ResponseEntity.ok(insuranceCoverageService.updateCoverage(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCoverage(
            @PathVariable Long id) throws Exception {
        insuranceCoverageService.deleteCoverage(id);
        return ResponseEntity.ok(new ApiResponse("Coverage deleted successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InsuranceCoverageResponse> getCoverageById(@PathVariable Long id)
            throws Exception {
        return ResponseEntity.ok(insuranceCoverageService.getCoverage(id));
    }

    @GetMapping
    public ResponseEntity<List<InsuranceCoverageResponse>> getAllCoverages() {
        return ResponseEntity.ok(insuranceCoverageService.getAllCoverages());
    }

    @GetMapping("/ancillary/{ancillaryId}")
    public ResponseEntity<List<InsuranceCoverageResponse>> getCoveragesByAncillaryId(
            @PathVariable Long ancillaryId) {
        return ResponseEntity.ok(insuranceCoverageService.getCoverageByAncillaryId(ancillaryId));
    }

    @GetMapping("/ancillary/{ancillaryId}/active")
    public ResponseEntity<List<InsuranceCoverageResponse>> getActiveCoveragesByAncillaryId(
            @PathVariable Long ancillaryId) {
        return ResponseEntity.ok(insuranceCoverageService.getActiveCoverageByAncillaryId(ancillaryId));
    }

}
