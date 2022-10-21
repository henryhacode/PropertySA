package com.rental.propertysa.entity;

import lombok.*;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table
public class Property {
    //    @PrimaryKey
//    @PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private UUID id;

    private String owner; // email

    //    @Indexed
    @PrimaryKeyColumn(ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private PropertyType type;

    private boolean available;
    private double pricePerNight;
    private String description;

    @Embedded(onEmpty = Embedded.OnEmpty.USE_EMPTY)
//    @Indexed
//    @SASI(indexMode = SASI.IndexMode.CONTAINS)
//    @SASI.StandardAnalyzed
    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    private Address address;

    @Column
    private Set<String> pictures;
}
