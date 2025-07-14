package com.stupor.mainequipmentcontroller.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stupor.mainequipmentcontroller.dto.EquipmentDto;
import com.stupor.mainequipmentcontroller.kafka.KafkaProducer;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EquipmentService {
    private final KafkaProducer kafkaProducer;
    private final ObjectMapper mapper;
    private final Logger log = LogManager.getLogger(EquipmentService.class);

    public void sendMsg(String message) throws JsonProcessingException {
        mapper.readValue(message, EquipmentDto.class);
        log.info("Отправляю сообщение в кафку");
        kafkaProducer.sendMessage("airsoft-created", message);
    }
}
