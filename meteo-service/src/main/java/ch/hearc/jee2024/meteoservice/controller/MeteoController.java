package ch.hearc.jee2024.meteoservice.controller;

import ch.hearc.jee2024.meteoservice.model.WeatherResponse;
import ch.hearc.jee2024.meteoservice.service.MeteoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
public class MeteoController {

    private final MeteoService meteoService;

    public MeteoController(MeteoService meteoService) {

        this.meteoService = meteoService;
    }

    @GetMapping
    public ResponseEntity<WeatherResponse> getWeather(@RequestParam String city) {
        WeatherResponse w = meteoService.getWeather(city);
        return (w == null)
                ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(w);
    }

}