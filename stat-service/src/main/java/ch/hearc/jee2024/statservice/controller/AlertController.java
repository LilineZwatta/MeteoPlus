package ch.hearc.jee2024.statservice.controller;

import ch.hearc.jee2024.statservice.model.Alert;
import ch.hearc.jee2024.statservice.service.AlertService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@Tag(name = "Alert API", description = "Endpoints pour consulter les alertes météo")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping
    @Operation(
            summary = "Liste de toutes les alertes",
            description = "Récupère toutes les alertes météo enregistrées",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Succès")
            }
    )
    public List<Alert> getAllAlerts() {
        return alertService.getAllAlerts();
    }

    @GetMapping("/{city}")
    @Operation(
            summary = "Alertes par ville",
            description = "Récupère les alertes météo pour une ville donnée (nom insensible à la casse)",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Succès"),
                    @ApiResponse(responseCode = "404", description = "Aucune alerte trouvée pour la ville")
            }
    )
    public List<Alert> getAlertsByCity(@PathVariable String city) {
        return alertService.getAlertsByCity(city);
    }
}
