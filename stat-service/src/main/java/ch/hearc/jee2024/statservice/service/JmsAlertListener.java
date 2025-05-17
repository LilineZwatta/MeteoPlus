package ch.hearc.jee2024.statservice.service;

import ch.hearc.jee2024.statservice.model.Alert;
import ch.hearc.jee2024.statservice.model.WeatherResponse;
import ch.hearc.jee2024.statservice.repository.AlertRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class JmsAlertListener {

    private final ObjectMapper objectMapper;
    private final AlertRepository alertRepository;

    public JmsAlertListener(ObjectMapper objectMapper, AlertRepository alertRepository) {
        this.objectMapper = objectMapper;
        this.alertRepository = alertRepository;
    }

    @JmsListener(destination = "weather.alert.queue")
    public void receiveAlert(String messageJson) {
        System.out.println("Message JMS brut reçu : " + messageJson);
        try {
            WeatherResponse weather = objectMapper.readValue(messageJson, WeatherResponse.class);
            Alert alert = new Alert();
            alert.setCity(weather.getName());
            alert.setTemperature(weather.getMain().getTemp());
            alert.setCondition(weather.getWeather().get(0).getDescription());
            alertRepository.save(alert);
            System.out.println("Alerte sauvegardée pour " + alert.getCity());
        } catch (Exception e) {
            System.err.println("Erreur réception alerte : " + e.getMessage());
        }
    }
}
