package ch.hearc.jee2024.meteoservice.service;

import ch.hearc.jee2024.meteoservice.model.WeatherResponse;

public interface MeteoService {
    /**
     * Récupère la météo actuelle pour la ville passée en paramètre.
     * @param city le nom de la ville
     * @return la réponse météo pour la ville spécifiée ou retourne une exception si la ville n'est pas trouvée
     */
    WeatherResponse getWeather(String city);
}