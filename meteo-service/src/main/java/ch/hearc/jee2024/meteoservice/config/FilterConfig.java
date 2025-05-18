package ch.hearc.jee2024.meteoservice.config;

import ch.hearc.jee2024.meteoservice.security.JwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter() {
        FilterRegistrationBean<JwtFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new JwtFilter());
        registration.addUrlPatterns("/api/*"); // Protéger uniquement les endpoints REST
        return registration;
    }
}
