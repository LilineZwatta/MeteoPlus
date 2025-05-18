package ch.hearc.jee2024.meteoservice.controller;

import ch.hearc.jee2024.meteoservice.model.Alert;
import ch.hearc.jee2024.meteoservice.model.WeatherResponse;
import ch.hearc.jee2024.meteoservice.service.AlertClientService;
import ch.hearc.jee2024.meteoservice.service.MeteoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MeteoController.class)
@Import(MeteoControllerTest.TestConfig.class)
@ActiveProfiles("test")
@ComponentScan("ch.hearc.jee2024.meteoservice.controller")
public class MeteoControllerTest {

    @Configuration
    static class TestConfig {
        @Bean
        public MeteoService meteoService() {
            return Mockito.mock(MeteoService.class);
        }

        @Bean
        public AlertClientService alertClientService() {
            return Mockito.mock(AlertClientService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MeteoService meteoService;

    @Autowired
    private AlertClientService alertClientService;

    private WeatherResponse dummyWeather;

    @BeforeEach
    public void setup() {
        dummyWeather = new WeatherResponse();
        dummyWeather.setCity("Paris");

        WeatherResponse.TemperatureInfo main = new WeatherResponse.TemperatureInfo();
        main.setTemp(22.5);
        dummyWeather.setMain(main);
    }

    @Test
    public void getWeather_validCity_returns200() throws Exception {
        when(meteoService.getWeather("Paris")).thenReturn(dummyWeather);

        mockMvc.perform(get("/api/weather?city=Paris"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Paris"));
    }

    @Test
    public void getWeather_invalidCity_returns404() throws Exception {
        when(meteoService.getWeather("UnknownCity")).thenReturn(null);

        mockMvc.perform(get("/api/weather?city=UnknownCity"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void alerts_validCity_returns200() throws Exception {
        Alert alert = new Alert();
        alert.setCity("Paris");
        alert.setCondition("clear sky");
        alert.setTemperature(30.0);

        when(alertClientService.getAlertsForCity("Paris")).thenReturn(List.of(alert));

        mockMvc.perform(get("/api/weather/alerts/Paris"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].city").value("Paris"));
    }

    @Test
    public void alerts_noAlerts_returnsEmptyList() throws Exception {
        when(alertClientService.getAlertsForCity("EmptyCity")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/weather/alerts/EmptyCity"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
