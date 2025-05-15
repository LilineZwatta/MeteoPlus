package ch.hearc.jee2024.meteoservice.service;

import ch.hearc.jee2024.meteoservice.model.WeatherResponse;
import reactor.core.publisher.Mono;

public interface MeteoService {
    /**
     * Récupère la météo actuelle pour la ville passée en paramètre.
     */
    Mono<WeatherResponse> fetchWeather(String city);
}