package com.rental.propertysa.controller;

import com.rental.propertysa.dto.PropertyDto;
import com.rental.propertysa.entity.Address;
import com.rental.propertysa.entity.PropertyType;
import com.rental.propertysa.service.PropertyService;
import com.rental.propertysa.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PropertyController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("dev")
public class PropertyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PropertyService propertyService;

    @Test
    public void should_return_property() throws Exception {
        UUID propertyId = UUID.randomUUID();
        Address address = new Address("10 1st", "FairField", "Iowa", "52556");
        PropertyDto propertyDto = new PropertyDto(propertyId, "henry@gmail.com", PropertyType.HOUSE, true,
                100.0, "Very good house", address, null);
        Mockito.when(propertyService.findById(propertyId)).thenReturn(propertyDto);
        mockMvc.perform(get("/properties/" + propertyId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(propertyDto.getId().toString())))
                .andExpect(jsonPath("$.address.street", is(propertyDto.getAddress().getStreet())))
                .andExpect(jsonPath("$.address.city", is(propertyDto.getAddress().getCity())))
                .andExpect(jsonPath("$.address.state", is(propertyDto.getAddress().getState())))
                .andExpect(jsonPath("$.address.zipCode", is(propertyDto.getAddress().getZipCode())))
                .andExpect(jsonPath("$.owner", is(propertyDto.getOwner())))
                .andExpect(jsonPath("$.available", is(propertyDto.isAvailable())))
                .andExpect(jsonPath("$.pricePerNight", is(propertyDto.getPricePerNight())))
                .andExpect(jsonPath("$.description", is(propertyDto.getDescription())));
    }

    @Test
    public void should_return_property_list() throws Exception {
        UUID propertyId = UUID.randomUUID();
        Address address = new Address("10 1st", "FairField", "Iowa", "52556");
        PropertyDto propertyDto = new PropertyDto(propertyId, "henry@gmail.com", PropertyType.HOUSE, true,
                100.0, "Very good house", address, null);
        Mockito.when(propertyService.findAll()).thenReturn(List.of(propertyDto));
        mockMvc.perform(get("/properties")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(propertyDto.getId().toString())))
                .andExpect(jsonPath("$[0].address.street", is(propertyDto.getAddress().getStreet())))
                .andExpect(jsonPath("$[0].address.city", is(propertyDto.getAddress().getCity())))
                .andExpect(jsonPath("$[0].address.state", is(propertyDto.getAddress().getState())))
                .andExpect(jsonPath("$[0].address.zipCode", is(propertyDto.getAddress().getZipCode())))
                .andExpect(jsonPath("$[0].owner", is(propertyDto.getOwner())))
                .andExpect(jsonPath("$[0].available", is(propertyDto.isAvailable())))
                .andExpect(jsonPath("$[0].pricePerNight", is(propertyDto.getPricePerNight())))
                .andExpect(jsonPath("$[0].description", is(propertyDto.getDescription())));
    }

    @Test
    public void should_return_property_list_search() throws Exception {
        UUID propertyId = UUID.randomUUID();
        Address address = new Address("10 1st", "FairField", "Iowa", "52556");
        PropertyDto propertyDto = new PropertyDto(propertyId, "henry@gmail.com", PropertyType.HOUSE, true,
                100.0, "Very good house", address, null);
        Mockito.when(propertyService.search(anyString(), anyInt(), anyInt(), anyString(), anyString(), anyString(), anyString(), anyBoolean())).thenReturn(List.of(propertyDto));
        mockMvc.perform(get("/properties/search?type=HOUSE&minPrice=1&maxPrice=1000")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(propertyDto.getId().toString())))
                .andExpect(jsonPath("$[0].address.street", is(propertyDto.getAddress().getStreet())))
                .andExpect(jsonPath("$[0].address.city", is(propertyDto.getAddress().getCity())))
                .andExpect(jsonPath("$[0].address.state", is(propertyDto.getAddress().getState())))
                .andExpect(jsonPath("$[0].address.zipCode", is(propertyDto.getAddress().getZipCode())))
                .andExpect(jsonPath("$[0].owner", is(propertyDto.getOwner())))
                .andExpect(jsonPath("$[0].available", is(propertyDto.isAvailable())))
                .andExpect(jsonPath("$[0].pricePerNight", is(propertyDto.getPricePerNight())))
                .andExpect(jsonPath("$[0].description", is(propertyDto.getDescription())));
    }

    @Test
    public void should_create_property() throws Exception {
        UUID propertyId = UUID.randomUUID();
        Address address = new Address("10 1st", "FairField", "Iowa", "52556");
        PropertyDto propertyDto = new PropertyDto(propertyId, "henry@gmail.com", PropertyType.HOUSE, true,
                100.0, "Very good house", address, null);
        Mockito.when(propertyService.save(any())).thenReturn(propertyDto);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer jwt");

        mockMvc.perform(post("/properties")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.stringify(propertyDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(propertyDto.getId().toString())))
                .andExpect(jsonPath("$.address.street", is(propertyDto.getAddress().getStreet())))
                .andExpect(jsonPath("$.address.city", is(propertyDto.getAddress().getCity())))
                .andExpect(jsonPath("$.address.state", is(propertyDto.getAddress().getState())))
                .andExpect(jsonPath("$.address.zipCode", is(propertyDto.getAddress().getZipCode())))
                .andExpect(jsonPath("$.owner", is(propertyDto.getOwner())))
                .andExpect(jsonPath("$.available", is(propertyDto.isAvailable())))
                .andExpect(jsonPath("$.pricePerNight", is(propertyDto.getPricePerNight())))
                .andExpect(jsonPath("$.description", is(propertyDto.getDescription())));
    }

    @Test
    public void should_update_property() throws Exception {
        UUID propertyId = UUID.randomUUID();
        Address address = new Address("10 1st", "FairField", "Iowa", "52556");
        PropertyDto propertyDto = new PropertyDto(propertyId, "henry@gmail.com", PropertyType.HOUSE, true,
                100.0, "Very good house", address, null);
        Mockito.when(propertyService.update(any(), any())).thenReturn(propertyDto);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer jwt");

        mockMvc.perform(put("/properties/" + propertyId)
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.stringify(propertyDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(propertyDto.getId().toString())))
                .andExpect(jsonPath("$.address.street", is(propertyDto.getAddress().getStreet())))
                .andExpect(jsonPath("$.address.city", is(propertyDto.getAddress().getCity())))
                .andExpect(jsonPath("$.address.state", is(propertyDto.getAddress().getState())))
                .andExpect(jsonPath("$.address.zipCode", is(propertyDto.getAddress().getZipCode())))
                .andExpect(jsonPath("$.owner", is(propertyDto.getOwner())))
                .andExpect(jsonPath("$.available", is(propertyDto.isAvailable())))
                .andExpect(jsonPath("$.pricePerNight", is(propertyDto.getPricePerNight())))
                .andExpect(jsonPath("$.description", is(propertyDto.getDescription())));
    }

    @Test
    public void should_delete_property() throws Exception {
        UUID propertyId = UUID.randomUUID();
        Address address = new Address("10 1st", "FairField", "Iowa", "52556");
        PropertyDto propertyDto = new PropertyDto(propertyId, "henry@gmail.com", PropertyType.HOUSE, true,
                100.0, "Very good house", address, null);
        Mockito.when(propertyService.delete(any())).thenReturn(propertyDto);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer jwt");

        mockMvc.perform(delete("/properties/" + propertyId)
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(propertyDto.getId().toString())))
                .andExpect(jsonPath("$.address.street", is(propertyDto.getAddress().getStreet())))
                .andExpect(jsonPath("$.address.city", is(propertyDto.getAddress().getCity())))
                .andExpect(jsonPath("$.address.state", is(propertyDto.getAddress().getState())))
                .andExpect(jsonPath("$.address.zipCode", is(propertyDto.getAddress().getZipCode())))
                .andExpect(jsonPath("$.owner", is(propertyDto.getOwner())))
                .andExpect(jsonPath("$.available", is(propertyDto.isAvailable())))
                .andExpect(jsonPath("$.pricePerNight", is(propertyDto.getPricePerNight())))
                .andExpect(jsonPath("$.description", is(propertyDto.getDescription())));
    }
}
