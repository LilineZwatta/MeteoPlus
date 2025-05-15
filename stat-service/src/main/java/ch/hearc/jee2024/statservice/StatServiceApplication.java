package ch.hearc.jee2024.statservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StatServiceApplication {

    public static void main(String[] args) {
        System.out.println("Before Start");
        SpringApplication.run(StatServiceApplication.class, args);
        System.out.println("After Start");

    }
}

