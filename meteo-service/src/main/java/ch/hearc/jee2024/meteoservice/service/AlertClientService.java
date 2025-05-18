package ch.hearc.jee2024.meteoservice.service;

import ch.hearc.jee2024.meteoservice.model.Alert;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class AlertClientService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String statServiceUrl = "http://localhost:8081/api/alerts/city/";

    public List<Alert> getAlertsForCity(String city) {
        Alert[] alerts = restTemplate.getForObject(statServiceUrl + city, Alert[].class);
        return Arrays.asList(alerts);
    }
}
