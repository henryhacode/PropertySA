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

//    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_ADMIN')")
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

        List<Property> properties;
        if (!onlyLocation) {
            properties = propertyRepository.findByTypeAndPricePerNightGreaterThanEqualAndPricePerNightLessThanEqualAndAddressStreetEqualsIgnoreCaseAndAddressCityEqualsIgnoreCaseAndAddressZipCodeEqualsIgnoreCase(
                    propType, minPrice, maxPrice, street, city, zipCode
            );
        } else {
            if (street.length() != 0 && city.length() != 0 && state.length() != 0) {
                properties = propertyRepository.findByTypeAndAddressStreetEqualsIgnoreCaseAndAddressCityEqualsIgnoreCaseAndAddressStateEqualsIgnoreCase(
                        propType, street, city, state);
            } else if (street.length() != 0) {
                properties = propertyRepository.findByTypeAndAddressStreetEqualsIgnoreCase(propType, street);
            } else if (city.length() != 0) {
                properties = propertyRepository.findByTypeAndAddressCityEqualsIgnoreCase(propType, city);
            } else if (state.length() != 0) {
                properties = propertyRepository.findByTypeAndAddressStateEqualsIgnoreCase(propType, state);
            } else {
                properties = propertyRepository.findByType(propType);
            }
//            properties = propertyRepository.findByTypeAndAddressStreetEqualsIgnoreCaseAndAddressCityEqualsIgnoreCaseAndAddressStateEqualsIgnoreCaseAndAddressZipCodeEqualsIgnoreCase(
//                    propType, street, city, state, zipCode
//            );
        }

        return propertyMapper.toDtoList(properties);
    }

    @Override
//    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_ADMIN')")
    public PropertyDto update(UUID id, PropertyDto propertyDto) {
        propertyDto.setId(id);
        return propertyMapper.toDto(propertyRepository.save(propertyMapper.toEntity(propertyDto)));
    }

    @Override
//    @PreAuthorize("hasRole('ROLE_OWNER') or hasRole('ROLE_ADMIN')")
    public PropertyDto delete(UUID id) {
        PropertyDto propertyDto = propertyRepository.findById(id).map(propertyMapper::toDto).orElse(null);
        propertyRepository.deleteById(id);
        return propertyDto;
    }


}
