package ch.hearc.jee2024.meteoservice.controller;

import ch.hearc.jee2024.meteoservice.model.WeatherResponse;
import ch.hearc.jee2024.meteoservice.service.MeteoService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/weather")
public class MeteoController {

    private final MeteoService meteoService;

    public MeteoController(MeteoService meteoService) {
        this.meteoService = meteoService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<WeatherResponse> getWeather(@RequestParam("city") String city) {
        return meteoService.fetchWeather(city);
    }
}