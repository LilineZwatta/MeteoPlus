package ch.hearc.jee2024.meteoservice.controller;

import ch.hearc.jee2024.meteoservice.model.WeatherResponse;
import ch.hearc.jee2024.meteoservice.service.MeteoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class MeteoController {

    private final MeteoService meteoService;

    public MeteoController(MeteoService meteoService) {

        this.meteoService = meteoService;
    }

    @GetMapping
    public ResponseEntity<?> getWeather(@RequestParam String city) {
        WeatherResponse resp = meteoService.getWeather(city);
        if (resp == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", city +" => Ville introuvable" ));
        }
        return ResponseEntity.ok(resp);
    }
}