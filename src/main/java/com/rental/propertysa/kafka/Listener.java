package com.rental.propertysa.kafka;

import com.rental.propertysa.entity.Property;
import com.rental.propertysa.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Listener {
    private final PropertyRepository propertyRepository;

    @KafkaListener(id = "reservation-listener", topics = "${kafka.reservationTopic}")
    public void listen(ReservationChange msg) {
        log.info("Consumer received msg " + msg);
        if (msg == null) return;

        try {
            Property property = propertyRepository.findById(msg.getPropertyId()).orElse(null);
            if (property != null) {
                log.info(property.toString());
                property.setAvailable(true);
                propertyRepository.save(property);
            } else {
                log.info("Kafka message processing: cannot find property id");
            }
        } catch (Exception ex) {
            log.info("Kafka message processing error: " + ex);
        }
    }
}
