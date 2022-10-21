package com.rental.propertysa.entity;

import lombok.*;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@PrimaryKeyClass
public class Address {
    @PrimaryKeyColumn(ordinal = 3, type = PrimaryKeyType.PARTITIONED)
    private String street;
    @PrimaryKeyColumn(ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String city;
    @PrimaryKeyColumn(ordinal = 2, type = PrimaryKeyType.PARTITIONED)
    private String state;
    @PrimaryKeyColumn(ordinal = 4, type = PrimaryKeyType.PARTITIONED)
    private String zipCode;
}
