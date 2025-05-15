package ch.hearc.jee2024.meteoservice.service;

import ch.hearc.jee2024.meteoservice.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MeteoServiceImpl implements MeteoService {

    private final WebClient webClient;
    private final String apiKey;
    private final JmsTemplate jmsTemplate;
    private final String alertQueue;

    public MeteoServiceImpl(
            @Value("${openweather.api.url}") String apiUrl,
            @Value("${openweather.api.key}") String apiKey,
            JmsTemplate jmsTemplate,
            @Value("${app.jms.alert-queue}") String alertQueue) {

        // on crée un WebClient direct avec l'URL
        this.webClient = WebClient.create(apiUrl);
        this.apiKey = apiKey;
        this.jmsTemplate = jmsTemplate;
        this.alertQueue = alertQueue;
    }

    @Override
    public Mono<WeatherResponse> fetchWeather(String city) {
        return webClient.get()
                .uri(uri -> uri
                        .queryParam("q", city)
                        .queryParam("appid", apiKey)
                        .queryParam("units", "metric")      // <— ici !
                        .build())
                .retrieve()
                .bodyToMono(WeatherResponse.class)
                .doOnNext(resp -> {
                    // maintenant resp.getMain().getTemp() est déjà en °C
                    double tempC = resp.getMain().getTemp();
                    boolean severe = tempC > 35 ||
                            resp.getWeather().stream()
                                    .anyMatch(w -> w.getDescription().toLowerCase().contains("rain"));
                    if (severe) {
                        jmsTemplate.convertAndSend(alertQueue, resp);
                    }
                })
                .map(resp -> {
                    // on arrondit à 1 décimale pour l’affichage
                    double rounded = Math.round(resp.getMain().getTemp() * 10.0) / 10.0;
                    resp.getMain().setTemp(rounded);
                    return resp;
                });
    }
}
