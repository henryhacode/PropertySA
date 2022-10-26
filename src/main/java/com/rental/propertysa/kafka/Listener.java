package com.rental.propertysa.kafka;

import com.rental.propertysa.entity.Property;
import com.rental.propertysa.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@RequiredArgsConstructor
public class Listener {
    private final PropertyRepository propertyRepository;

    @KafkaListener(id = "reservation-listener", topics = "${kafka.reservationTopic}")
    public void listen(ReservationChange msg) {
        log.info("Consumer received msg " + msg);
        if (msg == null) return;

        Property property = propertyRepository.findById(msg.getPropertyId()).orElse(null);
        if (property != null) {
            property.setAvailable(true);
            propertyRepository.save(property);
        }
    }
}
