package ch.hearc.jee2024.meteoservice.service;

import ch.hearc.jee2024.meteoservice.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class MeteoServiceImpl implements MeteoService {

    @Value("${API_URL}")
    private String apiUrl;

    @Value("${API_KEY}")
    private String apiKey;

    private final RestTemplate rest = new RestTemplate();
    private final JmsSender jmsSender;

    public MeteoServiceImpl(
            @Value("${API_URL}") String apiUrl,
            @Value("${API_KEY}") String apiKey,
            JmsSender jmsSender) {

        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
        this.jmsSender = jmsSender;
    }

    @Override
    public WeatherResponse getWeather(String city) {
        String url = apiUrl
                + "?q=" + city
                + "&appid=" + apiKey
                + "&units=metric";  // °C

        try {
            WeatherResponse weather = rest.getForObject(url, WeatherResponse.class);
            if (weather != null) {
                // arrondi à 1 décimale
                double temp = Math.round(weather.getMain().getTemp() * 10.0) / 10.0;
                weather.getMain().setTemp(temp);

                // envoi d’alerte si extrême
                if (temp > 20    ||
                        weather.getWeather().stream()
                                .anyMatch(w -> w.getDescription().toLowerCase().contains("rain"))) {
                    jmsSender.sendAlert(weather);
                }
            }
            return weather;

        } catch (HttpClientErrorException.NotFound ex) {
            // ville introuvable → renvoie null, le controller renverra 404
            return null;
        }
    }
}
