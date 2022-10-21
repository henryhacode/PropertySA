package com.rental.propertysa.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationChange {
    private String status; // DONE, FAIL
    private UUID propertyId;
}
