package ch.hearc.jee2024.meteoservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class MeteoServiceApplication {
//meteo-service le gateway qui récupère la météo et déclenche les alertes
    public static void main(String[] args) {
        System.out.println("Before Start");
        SpringApplication.run(MeteoServiceApplication.class, args);
        System.out.println("After Start");

    }

    @Value("${openweather.api.url}")
    private String baseUrl;

//    @Bean
//    public WebClient webClient(WebClient.Builder builder) {
//        return builder.baseUrl(baseUrl).build();
//    }

    @Bean
    public WebClient webClient(@Value("${openweather.api.url}") String apiUrl) {
        return WebClient.builder()
                .baseUrl(apiUrl)
                .build();
    }
}
