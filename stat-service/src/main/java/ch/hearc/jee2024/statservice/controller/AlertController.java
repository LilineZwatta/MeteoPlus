package ch.hearc.jee2024.statservice.controller;

import ch.hearc.jee2024.statservice.model.Alert;
import ch.hearc.jee2024.statservice.repository.AlertRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertRepository alertRepository;

    public AlertController(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @Operation(summary = "Retourne toutes les alertes météo enregistrées")
    @ApiResponse(responseCode = "200", description = "Liste des alertes retournée avec succès")
    @GetMapping
    public List<Alert> getAll() {
        return alertRepository.findAll();
    }

    @Operation(summary = "Retourne les alertes pour une ville donnée")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alertes trouvées pour la ville"),
            @ApiResponse(responseCode = "404", description = "Aucune alerte pour cette ville")
    })
    @GetMapping("/city/{cityName}")
    public List<Alert> getAlertsByCity(@PathVariable String cityName) {
        return alertRepository.findByCityIgnoreCase(cityName);
    }
}
