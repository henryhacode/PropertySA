package com.rental.propertysa.service.impl;

import com.rental.propertysa.dto.PropertyDto;
import com.rental.propertysa.entity.Property;
import com.rental.propertysa.entity.PropertyType;
import com.rental.propertysa.mapper.PropertyMapper;
import com.rental.propertysa.repository.PropertyRepository;
import com.rental.propertysa.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PropertyServiceImpl implements PropertyService {
    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;

    @Override
    public PropertyDto save(PropertyDto propertyDto) {
        if (propertyDto.getId() == null) {
            propertyDto.setId(UUID.randomUUID());
        }
        return propertyMapper.toDto(propertyRepository.save(propertyMapper.toEntity(propertyDto)));
    }

    @Override
    public List<PropertyDto> findAll() {
        List<Property> properties = (List<Property>) propertyRepository.findAll();
        return propertyMapper.toDtoList(properties);
    }

    @Override
    public PropertyDto findById(UUID id) {
        return propertyRepository.findById(id).map(propertyMapper::toDto).orElse(null);
    }

    @Override
    public List<PropertyDto> search(String type,
                                    int minPrice,
                                    int maxPrice,
                                    String street,
                                    String city,
                                    String state,
                                    String zipCode,
                                    Boolean onlyLocation) {
        // Property type
        PropertyType propType = PropertyType.HOUSE;
        for (PropertyType pt : PropertyType.values()) {
            if (pt.toString().equalsIgnoreCase(type)) {
                propType = pt;
                break;
            }
        }

        // Location
        String streetPattern = street;
        if (!street.equals("%")) {
            streetPattern = "%" + street + "%";
        }

        String cityPattern = city;
        if (!city.equals("%")) {
            cityPattern = "%" + city + "%";
        }

        String zipCodePattern = zipCode;
        if (!zipCode.equals("%")) {
            zipCodePattern = "%" + zipCode + "%";
        }

        List<Property> properties;
//        properties = (List<Property>) propertyRepository.findAll();
        if (!onlyLocation) {
            properties = propertyRepository.findByTypeAndPricePerNightGreaterThanEqualAndPricePerNightLessThanEqualAndAddressStreetEqualsIgnoreCaseAndAddressCityEqualsIgnoreCaseAndAddressZipCodeEqualsIgnoreCase(
                    propType, minPrice, maxPrice, street, city, zipCode
            );
        } else {
            properties = propertyRepository.findByTypeAndAddressStreetEqualsIgnoreCaseAndAddressCityEqualsIgnoreCaseAndAddressStateEqualsIgnoreCaseAndAddressZipCodeEqualsIgnoreCase(
                    propType, street, city, state, zipCode
            );
        }

        return propertyMapper.toDtoList(properties);
    }

    @Override
    public PropertyDto update(PropertyDto propertyDto) {
        return propertyMapper.toDto(propertyRepository.save(propertyMapper.toEntity(propertyDto)));
    }

    @Override
    public PropertyDto delete(UUID id) {
        PropertyDto propertyDto = propertyRepository.findById(id).map(propertyMapper::toDto).orElse(null);
        propertyRepository.deleteById(id);
        return propertyDto;
    }


}
