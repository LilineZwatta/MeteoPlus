package ch.hearc.jee2024.meteoservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

    @JsonProperty("name")
    private String city;

    @JsonProperty("main")
    private TemperatureInfo main;

    @JsonProperty("weather")
    private List<WeatherInfo> weather;

    public WeatherResponse() {}

    public String getCity() {
        return city;
    }

    public TemperatureInfo getMain() {
        return main;
    }

    public List<WeatherInfo> getWeather() {
        return weather;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TemperatureInfo {
        @JsonProperty("temp")
        private double temp;

        public TemperatureInfo() {}

        public double getTemp() {
            return temp;
        }

        public void setTemp(double temp) {
            this.temp = temp;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeatherInfo {
        @JsonProperty("description")
        private String description;

        public WeatherInfo() {}

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
