package ch.hearc.jee2024.statservice.service;

import ch.hearc.jee2024.statservice.model.Alert;
import ch.hearc.jee2024.statservice.repository.AlertRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.ComponentScan;

import static org.mockito.Mockito.*;

@ComponentScan("ch.hearc.jee2024.statservice.controller")
public class JmsAlertListenerTest {

    private AlertRepository alertRepository;
    private ObjectMapper objectMapper;
    private JmsAlertListener listener;

    @BeforeEach
    public void setup() {
        alertRepository = mock(AlertRepository.class);
        objectMapper = new ObjectMapper();
        listener = new JmsAlertListener(objectMapper, alertRepository);
    }

    @Test
    public void receiveAlert_validJson_savesAlert() {
        // language=JSON
        String json = """
        {
          "name": "TestCity",
          "main": { "temp": 28.5 },
          "weather": [{ "description": "clear sky" }]
        }
        """;

        listener.receiveAlert(json);

        verify(alertRepository, times(1)).save(argThat(alert ->
                alert.getCity().equals("TestCity") &&
                        alert.getTemperature() == 28.5 &&
                        alert.getCondition().equals("clear sky")
        ));
    }
}
