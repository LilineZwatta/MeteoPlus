package ch.hearc.jee2024.meteoservice;

import jakarta.jms.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication(scanBasePackages = "ch.hearc.jee2024.meteoservice")
public class MeteoServiceApplication {
    public static void main(String[] args) {
        System.out.println("Before Start");
        SpringApplication.run(MeteoServiceApplication.class, args);
        System.out.println("After Start");
    }

    @Value("${openweather.api.url}")
    private String baseUrl;

    @Bean
    public JmsTemplate jmsTopicTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setPubSubDomain(true);
        return jmsTemplate;
    }

    @Bean
    public WebClient webClient(@Value("${openweather.api.url}") String apiUrl) {
        return WebClient.builder()
                .baseUrl(apiUrl)
                .build();
    }
}
