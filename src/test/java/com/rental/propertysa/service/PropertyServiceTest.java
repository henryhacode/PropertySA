package com.rental.propertysa.service;

import com.rental.propertysa.dto.PropertyDto;
import com.rental.propertysa.entity.Address;
import com.rental.propertysa.entity.Property;
import com.rental.propertysa.entity.PropertyType;
import com.rental.propertysa.mapper.PropertyMapper;
import com.rental.propertysa.repository.PropertyRepository;
import com.rental.propertysa.service.impl.PropertyServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class PropertyServiceTest {
    @InjectMocks
    PropertyServiceImpl propertyService;
    @Mock
    PropertyRepository propertyRepository;
    @Mock
    PropertyMapper propertyMapper;

    @Test
    public void should_return_property() throws Exception {
        UUID propertyId = UUID.randomUUID();
        Address address = new Address("10 1st", "FairField", "Iowa", "52556");
        Property property = new Property(propertyId, "henry@gmail.com", PropertyType.HOUSE, true,
                100.0, "Very good house", address, null);
        PropertyDto propertyDto = new PropertyDto(propertyId, "henry@gmail.com", PropertyType.HOUSE, true,
                100.0, "Very good house", address, null);
        Mockito.when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));
        Mockito.when(propertyMapper.toDto(any())).thenReturn(propertyDto);
        PropertyDto returnedProperty = propertyService.findById(propertyId);
        assert (returnedProperty.getId().toString().equals(propertyId.toString()));
        assert (returnedProperty.getOwner().equals(property.getOwner()));
        assert (returnedProperty.getType().equals(property.getType()));
        assert (returnedProperty.getAddress().getStreet().equals(property.getAddress().getStreet()));
        assert (returnedProperty.getAddress().getCity().equals(property.getAddress().getCity()));
        assert (returnedProperty.getAddress().getState().equals(property.getAddress().getState()));
        assert (returnedProperty.getAddress().getZipCode().equals(property.getAddress().getZipCode()));
        assert (returnedProperty.getPricePerNight() == property.getPricePerNight());
        assert (returnedProperty.getDescription().equals(property.getDescription()));
    }

    @Test
    public void should_return_property_list() throws Exception {
        UUID propertyId = UUID.randomUUID();
        Address address = new Address("10 1st", "FairField", "Iowa", "52556");
        Property property = new Property(propertyId, "henry@gmail.com", PropertyType.HOUSE, true,
                100.0, "Very good house", address, null);
        PropertyDto propertyDto = new PropertyDto(propertyId, "henry@gmail.com", PropertyType.HOUSE, true,
                100.0, "Very good house", address, null);
        Mockito.when(propertyRepository.findAll()).thenReturn(List.of(property));
        Mockito.when(propertyMapper.toDtoList(any())).thenReturn(List.of(propertyDto));
        List<PropertyDto> returnedProperties = propertyService.findAll();
        assert (returnedProperties.size() == 1);
        assert (returnedProperties.get(0).getId().toString().equals(propertyId.toString()));
        assert (returnedProperties.get(0).getOwner().equals(property.getOwner()));
        assert (returnedProperties.get(0).getType().equals(property.getType()));
        assert (returnedProperties.get(0).getAddress().getStreet().equals(property.getAddress().getStreet()));
        assert (returnedProperties.get(0).getAddress().getCity().equals(property.getAddress().getCity()));
        assert (returnedProperties.get(0).getAddress().getState().equals(property.getAddress().getState()));
        assert (returnedProperties.get(0).getAddress().getZipCode().equals(property.getAddress().getZipCode()));
        assert (returnedProperties.get(0).getPricePerNight() == property.getPricePerNight());
        assert (returnedProperties.get(0).getDescription().equals(property.getDescription()));
    }

    @Test
    public void should_return_property_list_search() throws Exception {
        UUID propertyId = UUID.randomUUID();
        Address address = new Address("10 1st", "FairField", "Iowa", "52556");
        Property property = new Property(propertyId, "henry@gmail.com", PropertyType.HOUSE, true,
                100.0, "Very good house", address, null);
        PropertyDto propertyDto = new PropertyDto(propertyId, "henry@gmail.com", PropertyType.HOUSE, true,
                100.0, "Very good house", address, null);
        Mockito.when(propertyRepository.findByTypeAndAddressStreetEqualsIgnoreCaseAndAddressCityEqualsIgnoreCaseAndAddressStateEqualsIgnoreCaseAndAddressZipCodeEqualsIgnoreCase(
                any(), anyString(), anyString(), anyString(), anyString()
        )).thenReturn(List.of(property));
        Mockito.when(propertyMapper.toDtoList(any())).thenReturn(List.of(propertyDto));
        List<PropertyDto> returnedProperties = propertyService.search("HOUSE", 1, 1000, "10 1 st", "FairField", "Iowa",
                "52556", true);
        assert (returnedProperties.size() == 1);
        assert (returnedProperties.get(0).getId().toString().equals(propertyId.toString()));
        assert (returnedProperties.get(0).getOwner().equals(property.getOwner()));
        assert (returnedProperties.get(0).getType().equals(property.getType()));
        assert (returnedProperties.get(0).getAddress().getStreet().equals(property.getAddress().getStreet()));
        assert (returnedProperties.get(0).getAddress().getCity().equals(property.getAddress().getCity()));
        assert (returnedProperties.get(0).getAddress().getState().equals(property.getAddress().getState()));
        assert (returnedProperties.get(0).getAddress().getZipCode().equals(property.getAddress().getZipCode()));
        assert (returnedProperties.get(0).getPricePerNight() == property.getPricePerNight());
        assert (returnedProperties.get(0).getDescription().equals(property.getDescription()));
    }

    @Test
    public void should_save_property() throws Exception {
        UUID propertyId = UUID.randomUUID();
        Address address = new Address("10 1st", "FairField", "Iowa", "52556");
        Property property = new Property(propertyId, "henry@gmail.com", PropertyType.HOUSE, true,
                100.0, "Very good house", address, null);
        PropertyDto propertyDto = new PropertyDto(propertyId, "henry@gmail.com", PropertyType.HOUSE, true,
                100.0, "Very good house", address, null);
        Mockito.when(propertyRepository.save(property)).thenReturn(property);
        Mockito.when(propertyMapper.toEntity(any())).thenReturn(property);
        Mockito.when(propertyMapper.toDto(any())).thenReturn(propertyDto);
        PropertyDto returnedProperty = propertyService.save(propertyDto);
        assert (returnedProperty.getId().toString().equals(propertyId.toString()));
        assert (returnedProperty.getOwner().equals(property.getOwner()));
        assert (returnedProperty.getType().equals(property.getType()));
        assert (returnedProperty.getAddress().getStreet().equals(property.getAddress().getStreet()));
        assert (returnedProperty.getAddress().getCity().equals(property.getAddress().getCity()));
        assert (returnedProperty.getAddress().getState().equals(property.getAddress().getState()));
        assert (returnedProperty.getAddress().getZipCode().equals(property.getAddress().getZipCode()));
        assert (returnedProperty.getPricePerNight() == property.getPricePerNight());
        assert (returnedProperty.getDescription().equals(property.getDescription()));
    }

    @Test
    public void should_update_property() throws Exception {
        UUID propertyId = UUID.randomUUID();
        Address address = new Address("10 1st", "FairField", "Iowa", "52556");
        Property property = new Property(propertyId, "henry@gmail.com", PropertyType.HOUSE, true,
                100.0, "Very good house", address, null);
        PropertyDto propertyDto = new PropertyDto(propertyId, "henry@gmail.com", PropertyType.HOUSE, true,
                100.0, "Very good house", address, null);
        Mockito.when(propertyRepository.save(property)).thenReturn(property);
        Mockito.when(propertyMapper.toEntity(any())).thenReturn(property);
        Mockito.when(propertyMapper.toDto(any())).thenReturn(propertyDto);
        PropertyDto returnedProperty = propertyService.update(propertyId, propertyDto);
        assert (returnedProperty.getId().toString().equals(propertyId.toString()));
        assert (returnedProperty.getOwner().equals(property.getOwner()));
        assert (returnedProperty.getType().equals(property.getType()));
        assert (returnedProperty.getAddress().getStreet().equals(property.getAddress().getStreet()));
        assert (returnedProperty.getAddress().getCity().equals(property.getAddress().getCity()));
        assert (returnedProperty.getAddress().getState().equals(property.getAddress().getState()));
        assert (returnedProperty.getAddress().getZipCode().equals(property.getAddress().getZipCode()));
        assert (returnedProperty.getPricePerNight() == property.getPricePerNight());
        assert (returnedProperty.getDescription().equals(property.getDescription()));
    }

    @Test
    public void should_delete_property() throws Exception {
        UUID propertyId = UUID.randomUUID();
        Address address = new Address("10 1st", "FairField", "Iowa", "52556");
        Property property = new Property(propertyId, "henry@gmail.com", PropertyType.HOUSE, true,
                100.0, "Very good house", address, null);
        PropertyDto propertyDto = new PropertyDto(propertyId, "henry@gmail.com", PropertyType.HOUSE, true,
                100.0, "Very good house", address, null);
        Mockito.when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));
        Mockito.when(propertyMapper.toDto(any())).thenReturn(propertyDto);
        PropertyDto returnedProperty = propertyService.delete(propertyId);
        assert (returnedProperty.getId().toString().equals(propertyId.toString()));
        assert (returnedProperty.getOwner().equals(property.getOwner()));
        assert (returnedProperty.getType().equals(property.getType()));
        assert (returnedProperty.getAddress().getStreet().equals(property.getAddress().getStreet()));
        assert (returnedProperty.getAddress().getCity().equals(property.getAddress().getCity()));
        assert (returnedProperty.getAddress().getState().equals(property.getAddress().getState()));
        assert (returnedProperty.getAddress().getZipCode().equals(property.getAddress().getZipCode()));
        assert (returnedProperty.getPricePerNight() == property.getPricePerNight());
        assert (returnedProperty.getDescription().equals(property.getDescription()));
    }
}
