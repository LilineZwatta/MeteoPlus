package ch.hearc.jee2024.meteoservice.service;

import ch.hearc.jee2024.meteoservice.model.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class MeteoServiceImpl implements MeteoService {

    private final String apiUrl;
    private final String apiKey;
    private final RestTemplate rest = new RestTemplate();
    private final JmsSender jmsSender;

    public MeteoServiceImpl(
            @Value("${openweather.api.url}") String apiUrl,
            @Value("${openweather.api.key}") String apiKey,
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
                + "&units=metric";

        try {
            WeatherResponse weather = rest.getForObject(url, WeatherResponse.class);
            if (weather != null) {
                double temp = Math.round(weather.getMain().getTemp() * 10.0) / 10.0;
                weather.getMain().setTemp(temp);

                if (temp > 20 || weather.getWeather().stream()
                        .anyMatch(w -> w.getDescription().toLowerCase().contains("rain"))) {
                    System.out.println("🔔 Envoi alerte pour " + weather.getCity());
                    jmsSender.sendAlert(weather);
                }
            }
            return weather;

        } catch (HttpClientErrorException.NotFound ex) {
            return null;
        }
    }
}

