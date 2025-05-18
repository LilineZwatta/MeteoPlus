package ch.hearc.jee2024.meteoservice.controller;

import ch.hearc.jee2024.meteoservice.model.Alert;
import ch.hearc.jee2024.meteoservice.model.WeatherResponse;
import ch.hearc.jee2024.meteoservice.service.AlertClientService;
import ch.hearc.jee2024.meteoservice.service.MeteoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class MeteoController {

    private final MeteoService meteoService;
    private final AlertClientService alertClientService;

    public MeteoController(MeteoService meteoService, AlertClientService alertClientService) {
        this.meteoService = meteoService;
        this.alertClientService = alertClientService;
    }

    @Operation(summary = "Retourne la météo actuelle d'une ville")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Météo trouvée"),
            @ApiResponse(responseCode = "404", description = "Ville introuvable")
    })
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

    @Operation(summary = "Retourne la liste des alertes météo pour une ville")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alertes récupérées"),
            @ApiResponse(responseCode = "401", description = "Non autorisé (token manquant ou invalide)")
    })
    @GetMapping("/alerts/{city}")
    public List<Alert> alerts(@PathVariable String city) {
        return alertClientService.getAlertsForCity(city);
    }
}
