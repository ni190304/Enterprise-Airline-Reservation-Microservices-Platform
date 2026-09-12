package com.project.ancillary_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.ancillary_service.services.AncillaryService;
import com.project.payload.request.AncillaryRequest;
import com.project.payload.response.AncillaryResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ancillaries")
public class AncillaryController {

    private final AncillaryService ancillaryService;

    @PostMapping
    public ResponseEntity<AncillaryResponse> createAncillary(
            @Valid @RequestBody AncillaryRequest ancillaryRequest,
            @RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(ancillaryService.createAncillary(userId, ancillaryRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AncillaryResponse> getById(@PathVariable Long id)
            throws Exception {
        return ResponseEntity.ok(ancillaryService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<AncillaryResponse>> getAllByAirlineId(
            @RequestHeader("X-User-Id") Long userId) {

        return ResponseEntity.ok(ancillaryService.getByAirlineId(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AncillaryResponse> update(
            @PathVariable Long id,
            @RequestBody AncillaryRequest request) throws Exception {
        return ResponseEntity.ok(ancillaryService.updateAncillary(id, request));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
        ancillaryService.deleteAncillary(id);
        return ResponseEntity.noContent().build();
    }
}
