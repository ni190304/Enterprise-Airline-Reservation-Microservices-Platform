package com.project.ancillary_service.model;

import com.project.ancillary_service.services.AncillaryMetadataConverter;
import com.project.domain.AncillaryMetadata;
import com.project.enums.AncillaryType;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Ancillary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AncillaryType type;

    private String subType;

    private String rfisc;

    @Column(nullable = false)
    private String name;

    private String description;

    @Convert(converter = AncillaryMetadataConverter.class)
    private AncillaryMetadata metadata;

    private Integer displayOrder;

    private Long airlineId;

}
