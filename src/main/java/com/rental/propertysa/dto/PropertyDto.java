package com.rental.propertysa.dto;

import com.rental.propertysa.entity.Address;
import com.rental.propertysa.entity.PropertyType;
import lombok.*;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDto implements Serializable {
    private UUID id;
    private String owner;
    private PropertyType type;
    private boolean available;
    private double pricePerNight;
    private String description;
    private Address address;
    private Set<String> pictures;
}
