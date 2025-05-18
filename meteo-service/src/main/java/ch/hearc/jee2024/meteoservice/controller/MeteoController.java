package ch.hearc.jee2024.meteoservice.controller;

import ch.hearc.jee2024.meteoservice.model.Alert;
import ch.hearc.jee2024.meteoservice.model.WeatherResponse;
import ch.hearc.jee2024.meteoservice.service.MeteoService;
import ch.hearc.jee2024.meteoservice.service.AlertClientService; // 🔹 N'oublie pas cet import
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class MeteoController {

    private final MeteoService meteoService;
    private final AlertClientService alertClientService; // 🔹 Déclare bien ce champ ici

    // 🔹 Injecte le service via le constructeur
    public MeteoController(MeteoService meteoService, AlertClientService alertClientService) {
        this.meteoService = meteoService;
        this.alertClientService = alertClientService;
    }

    @GetMapping
    public ResponseEntity<?> getWeather(@RequestParam String city) {
        WeatherResponse resp = meteoService.getWeather(city);
        if (resp == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", city + " => Ville introuvable"));
        }
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/alerts/{city}")
    public List<Alert> alerts(@PathVariable String city) {
        return alertClientService.getAlertsForCity(city);
    }
}
