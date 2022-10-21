package com.rental.propertysa.service;

import com.rental.propertysa.dto.PropertyDto;

import java.util.List;
import java.util.UUID;

public interface PropertyService {
    PropertyDto save(PropertyDto propertyDto);
    List<PropertyDto> findAll();
    PropertyDto findById(UUID id);
    List<PropertyDto> search(String type, int minPrice, int maxPrice, String street, String city, String state, String zipCode, Boolean onlyLocation);
    PropertyDto update(UUID id, PropertyDto propertyDto);
    PropertyDto delete(UUID id);
}
