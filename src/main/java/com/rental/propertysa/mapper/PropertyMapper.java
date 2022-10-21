package com.rental.propertysa.mapper;

import com.rental.propertysa.dto.PropertyDto;
import com.rental.propertysa.entity.Property;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PropertyMapper {
    private final ModelMapper modelMapper;

    public PropertyDto toDto(Property property) {
        return modelMapper.map(property, PropertyDto.class);
    }

    public Property toEntity(PropertyDto propertyDto) {
        return modelMapper.map(propertyDto, Property.class);
    }

    public List<PropertyDto> toDtoList(List<Property> properties) {
        return properties.stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<Property> toEntityList(List<PropertyDto> propertyDtos) {
        return propertyDtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
