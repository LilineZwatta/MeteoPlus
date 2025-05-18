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
    private final String alertQueue;

    public JmsSender(JmsTemplate jmsTemplate,
                     ObjectMapper objectMapper,
                     @Value("${app.jms.alert-queue}") String alertQueue) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
        this.alertQueue = alertQueue;
    }


    public void sendAlert(WeatherResponse weather) {
        try {
            String json = objectMapper.writeValueAsString(weather);
            jmsTemplate.convertAndSend(alertQueue, json);
        } catch (JsonProcessingException e) {
            System.err.println("Erreur de sérialisation : " + e.getMessage());
        }
    }
}
