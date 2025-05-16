package ch.hearc.jee2024.meteoservice.service;

import ch.hearc.jee2024.meteoservice.model.WeatherResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MeteoServiceImpl implements MeteoService {

    @Value("${API_URL}")
    private String apiUrl;

    @Value("${API_KEY}")
    private String apiKey;

    private final RestTemplate rest = new RestTemplate();
    private final JmsTemplate jmsTemplate;
    private final String alertQueue;

    public MeteoServiceImpl(
            @Value("${API_URL}") String apiUrl,
            @Value("${API_KEY}") String apiKey,
            JmsTemplate jmsTemplate,
            @Value("${app.jms.alert-queue}") String alertQueue) {

        // on crée un WebClient direct avec l'URL
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
        this.jmsTemplate = jmsTemplate;
        this.alertQueue = alertQueue;
    }

    @Override
    public WeatherResponse getWeather(String city) {
        String url = apiUrl
                + "?q=" + city
                + "&appid=" + apiKey
                + "&units=metric";  // °C

        try {
            ResponseEntity<WeatherResponse> resp =
                    rest.getForEntity(url, WeatherResponse.class);

            WeatherResponse weather = resp.getBody();
            if (weather != null) {
                // arrondi à 1 décimale
                double temp = Math.round(weather.getMain().getTemp() * 10.0) / 10.0;
                weather.getMain().setTemp(temp);

                // envoi d’alerte si extrême
                if (temp > 35 ||
                        weather.getWeather().stream()
                                .anyMatch(w -> w.getDescription().toLowerCase().contains("rain"))) {
                    jmsTemplate.convertAndSend(alertQueue, weather);
                }
            }
            return weather;

        } catch (HttpClientErrorException.NotFound ex) {
            // ville introuvable → renvoie null, le controller renverra 404
            return null;
        }
    }
}