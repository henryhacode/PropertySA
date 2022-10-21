package com.rental.propertysa.dto;

import com.rental.propertysa.entity.Address;
import com.rental.propertysa.entity.PropertyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDto {
    private UUID id;
    private String owner;
    private PropertyType type;
    private double pricePerNight;
    private String description;
    private Address address;
    private Set<String> pictures;
}
