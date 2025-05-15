package ch.hearc.jee2024.meteoservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class WeatherResponse {

    @JsonProperty("name")
    private String city;

    @JsonProperty("weather")
    private List<Weather> weather;

    @JsonProperty("main")
    private Main main;

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public List<Weather> getWeather() { return weather; }
    public void setWeather(List<Weather> weather) { this.weather = weather; }

    public Main getMain() { return main; }
    public void setMain(Main main) { this.main = main; }

    public static class Weather {
        @JsonProperty("description")
        private String description;

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }

    public static class Main {
        @JsonProperty("temp")
        private double temp;

        public double getTemp() { return temp; }
        public void setTemp(double temp) { this.temp = temp; }
    }
}
