package com.stupor.mainequipmentcontroller.kafka;

import com.stupor.mainequipmentcontroller.dto.EquipmentDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {
    private final Logger log = LogManager.getLogger(KafkaConsumer.class);
    @KafkaListener(topics = "equipment-enabled")
    public void consumerEquipment(EquipmentDto message) {
        log.info("Получено сообщение из топика equipment-enabled: {}", message);
    }
}