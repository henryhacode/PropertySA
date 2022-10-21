package com.rental.propertysa.controller;

import com.rental.propertysa.dto.PropertyDto;
import com.rental.propertysa.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/properties")
@CrossOrigin
@RequiredArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    @GetMapping
    public List<PropertyDto> findAll() {
        return propertyService.findAll();
    }

    @GetMapping("/{id}")
    public PropertyDto findById(@PathVariable UUID id) {
        return propertyService.findById(id);
    }

    @PostMapping
    public PropertyDto save(@RequestBody PropertyDto propertyDto) {
        return propertyService.save(propertyDto);
    }

    @GetMapping("/search")
    public List<PropertyDto> search(@RequestParam(defaultValue = "HOUSE") String type,
                                    @RequestParam(defaultValue = "-1") int minPrice,
                                    @RequestParam(defaultValue = Integer.MAX_VALUE + "") int maxPrice,
                                    @RequestParam(defaultValue = "%") String street,
                                    @RequestParam(defaultValue = "%") String city,
                                    @RequestParam(defaultValue = "%") String state,
                                    @RequestParam(defaultValue = "%") String zipCode,
                                    @RequestParam(defaultValue = "false") Boolean onlyLocation) {
        return propertyService.search(type, minPrice, maxPrice, street, city, state, zipCode, onlyLocation);
    }

    @PutMapping
    public PropertyDto update(PropertyDto propertyDto) {
        return propertyService.update(propertyDto);
    }

    @DeleteMapping("/{id}")
    public PropertyDto delete(@PathVariable UUID id) {
        return propertyService.delete(id);
    }
}
