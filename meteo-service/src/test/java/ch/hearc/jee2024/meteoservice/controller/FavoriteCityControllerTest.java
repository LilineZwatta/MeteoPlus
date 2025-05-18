package ch.hearc.jee2024.meteoservice.controller;

import ch.hearc.jee2024.meteoservice.model.FavoriteCity;
import ch.hearc.jee2024.meteoservice.service.FavoriteCityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FavoriteCityController.class)
@Import(FavoriteCityControllerTest.TestConfig.class)
public class FavoriteCityControllerTest {

    @Configuration
    static class TestConfig {
        @Bean
        public FavoriteCityService favoriteCityService() {
            return Mockito.mock(FavoriteCityService.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FavoriteCityService favoriteCityService;

    private FavoriteCity city;

    @BeforeEach
    public void setup() {
        city = new FavoriteCity();
        city.setId(1L);
        city.setCityName("Paris");
        city.setUserName("julien");
    }

    @Test
    public void testGetAllFavorites_returns200() throws Exception {
        when(favoriteCityService.getAllFavorites()).thenReturn(List.of(city));

        mockMvc.perform(get("/api/favorites"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cityName").value("Paris"));
    }

    @Test
    public void testGetFavoriteById_found() throws Exception {
        when(favoriteCityService.getFavoriteById(1L)).thenReturn(Optional.of(city));

        mockMvc.perform(get("/api/favorites/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cityName").value("Paris"));
    }

    @Test
    public void testGetFavoriteById_notFound() throws Exception {
        when(favoriteCityService.getFavoriteById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/favorites/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteFavorite_returns204() throws Exception {
        mockMvc.perform(delete("/api/favorites/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testCreateFavorite_returns201() throws Exception {
        when(favoriteCityService.addFavorite(Mockito.any())).thenReturn(city);

        String json = """
            {
              "cityName": "Paris",
              "userName": "julien"
            }
        """;

        mockMvc.perform(post("/api/favorites")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/favorites/1"));
    }
}
