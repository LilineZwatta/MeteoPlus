package ch.hearc.jee2024.statservice.controller;

import ch.hearc.jee2024.statservice.model.Alert;
import ch.hearc.jee2024.statservice.repository.AlertRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertRepository alertRepository;

    public AlertController(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @GetMapping
    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }
}
