package com.rental.propertysa.repository;

import com.rental.propertysa.entity.Property;
import com.rental.propertysa.entity.PropertyType;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Component
@Repository
public interface PropertyRepository extends CassandraRepository<Property, UUID> {
    List<Property> findByTypeAndPricePerNightGreaterThanEqualAndPricePerNightLessThanEqualAndAddressStreetEqualsIgnoreCaseAndAddressCityEqualsIgnoreCaseAndAddressZipCodeEqualsIgnoreCase(
            PropertyType type, double pricePerNight, double pricePerNight2, String address_street, String address_city, String address_zipCode
    );

    List<Property> findByTypeAndAddressStreetEqualsIgnoreCaseAndAddressCityEqualsIgnoreCaseAndAddressStateEqualsIgnoreCaseAndAddressZipCodeEqualsIgnoreCase(
            PropertyType type, String address_street, String address_city, String address_state, String address_zipCode
    );

    List<Property> findByType(PropertyType type);
    List<Property> findByTypeAndAddressStreetEqualsIgnoreCase(PropertyType type, String street);
    List<Property> findByTypeAndAddressCityEqualsIgnoreCase(PropertyType type, String city);
    List<Property> findByTypeAndAddressStateEqualsIgnoreCase(PropertyType type, String state);
    List<Property> findByTypeAndAddressStreetEqualsIgnoreCaseAndAddressCityEqualsIgnoreCaseAndAddressStateEqualsIgnoreCase(
            PropertyType type, String address_street, String address_city, String address_state
    );
}
