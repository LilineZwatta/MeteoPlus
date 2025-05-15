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
            WebClient webClient,
            @Value("${openweather.api.key}") String apiKey,
            JmsTemplate jmsTemplate,
            @Value("${app.jms.alert-queue}") String alertQueue) {
        this.webClient   = webClient;
        this.apiKey      = apiKey;
        this.jmsTemplate = jmsTemplate;
        this.alertQueue  = alertQueue;
    }

    @Override
    public Mono<WeatherResponse> fetchWeather(String city) {
        return webClient.get()
                .uri(uri -> uri
                        .queryParam("q", city)
                        .queryParam("appid", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(WeatherResponse.class)
                .doOnNext(resp -> {
                    // détection d’alerte
                    double tempC = resp.getMain().getTemp() - 273.15;
                    boolean severe = tempC > 35 ||
                            resp.getWeather().stream()
                                    .anyMatch(w -> w.getDescription().toLowerCase().contains("rain"));
                    if (severe) {
                        jmsTemplate.convertAndSend(alertQueue, resp);
                    }
                })
                .map(resp -> {
                    // conversion Kelvin -> Celsius
                    double tempC = resp.getMain().getTemp() - 273.15;
                    resp.getMain().setTemp(Math.round(tempC * 100.0) / 100.0);
                    return resp;
                });
    }
}
