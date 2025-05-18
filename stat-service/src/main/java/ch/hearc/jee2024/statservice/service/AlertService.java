package ch.hearc.jee2024.statservice.service;

import ch.hearc.jee2024.statservice.model.Alert;
import ch.hearc.jee2024.statservice.repository.AlertRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {

    private final AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

    public List<Alert> getAlertsByCity(String city) {
        return alertRepository.findByCityIgnoreCase(city); // ✅ Appel correct
    }

}
