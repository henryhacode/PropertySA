package com.rental.propertysa.kafka;

import com.rental.propertysa.entity.Property;
import com.rental.propertysa.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Slf4j
@Service
@RequiredArgsConstructor
public class Listener implements CommandLineRunner {
    private final PropertyRepository propertyRepository;

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private String redisPort;

    @Value("${spring.redis.password}")
    private String redisPwd;

    @Value("${jwt.secret}")
    private String secret;

    private Jedis jedis;

    @KafkaListener(id = "reservation-listener", topics = "${kafka.reservationTopic}")
    public void listen(ReservationChange msg) {
        log.info("Consumer received msg " + msg);
        log.info(redisHost + redisPort + redisPwd);
        log.info(secret);
        if (msg == null) return;

        try {
            Property property = propertyRepository.findById(msg.getPropertyId()).orElse(null);
            if (property != null) {
                log.info(property.toString());
                property.setAvailable(false);
                propertyRepository.save(property);
                if (jedis != null) {
                    long ret = jedis.del("properties::" + msg.getPropertyId().toString());
                    log.info("Result of redis delete {}", ret);
                }
            } else {
                log.info("Kafka message processing: cannot find property id");
            }
        } catch (Exception ex) {
            log.info("Kafka message processing error: " + ex);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            jedis = new Jedis(redisHost, Integer.parseInt(redisPort));
            if (redisPwd != null && redisPwd.length() > 0) {
                jedis.auth(redisPwd);
                log.info("auth with redis");
            }
        } catch (Exception ex) {
            log.info("Cannot connect to redis {}", ex.toString());
        }
    }
}
