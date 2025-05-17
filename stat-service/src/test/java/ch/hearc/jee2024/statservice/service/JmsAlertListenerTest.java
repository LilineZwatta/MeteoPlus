package ch.hearc.jee2024.statservice.service;

import ch.hearc.jee2024.statservice.model.Alert;
import ch.hearc.jee2024.statservice.repository.AlertRepository;
import ch.hearc.jee2024.statservice.model.WeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

public class JmsAlertListenerTest {

    @Test
    public void testReceiveAlert() throws Exception {
        String json = """
            {
              "name": "TestCity",
              "main": { "temp": 28.5 },
              "weather": [{ "description": "clear sky" }]
            }
        """;

        ObjectMapper objectMapper = new ObjectMapper();
        AlertRepository alertRepo = Mockito.mock(AlertRepository.class);

        JmsAlertListener listener = new JmsAlertListener(objectMapper, alertRepo);
        listener.receiveAlert(json);

        Mockito.verify(alertRepo, Mockito.times(1)).save(Mockito.argThat(alert ->
                alert.getCity().equals("TestCity") &&
                        alert.getTemperature() == 28.5 &&
                        alert.getCondition().equals("clear sky")
        ));
    }
}
