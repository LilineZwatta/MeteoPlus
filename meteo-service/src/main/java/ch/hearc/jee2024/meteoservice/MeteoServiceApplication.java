package ch.hearc.jee2024.meteoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MeteoServiceApplication {

    public static void main(String[] args) {
        System.out.println("Before Start");
        SpringApplication.run(MeteoServiceApplication.class, args);
        System.out.println("After Start");

    }
}
