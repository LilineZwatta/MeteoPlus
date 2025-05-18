package ch.hearc.jee2024.statservice.controller;

import ch.hearc.jee2024.statservice.model.Alert;
import ch.hearc.jee2024.statservice.service.AlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AlertController.class)
@Import(AlertControllerTest.TestConfig.class)
@ComponentScan("ch.hearc.jee2024.statservice.controller")
public class AlertControllerTest {

    @Configuration
    static class TestConfig {
        @Bean
        public AlertService alertService() {
            return Mockito.mock(AlertService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AlertService alertService;

    private Alert alert;

    @BeforeEach
    public void setup() {
        alert = new Alert();
        alert.setId(1L);
        alert.setCity("Paris");
        alert.setCondition("clear sky");
        alert.setTemperature(30.0);
    }

    @Test
    public void getAllAlerts_returns200() throws Exception {
        when(alertService.getAllAlerts()).thenReturn(List.of(alert));

        mockMvc.perform(get("/api/alerts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].city").value("Paris"));
    }

    @Test
    public void getAlertsByCity_returns200() throws Exception {
        when(alertService.getAlertsByCity("Paris")).thenReturn(List.of(alert));

        mockMvc.perform(get("/api/alerts/Paris"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].condition").value("clear sky"));
    }
}
