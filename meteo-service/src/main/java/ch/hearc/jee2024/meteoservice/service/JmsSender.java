package ch.hearc.jee2024.meteoservice.service;

import ch.hearc.jee2024.meteoservice.model.WeatherResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsSender {
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;
    @Value("${app.jms.alert-queue}")
    private String alertQueue;

    public JmsSender(JmsTemplate jmsTemplate, ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendAlert(WeatherResponse weather) {
        try {
            String json = objectMapper.writeValueAsString(weather);
            jmsTemplate.convertAndSend(alertQueue, json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erreur sérialisation alert", e);
        }
    }
}
