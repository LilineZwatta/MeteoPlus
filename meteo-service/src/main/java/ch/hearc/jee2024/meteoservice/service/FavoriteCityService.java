package ch.hearc.jee2024.meteoservice.service;

import ch.hearc.jee2024.meteoservice.model.FavoriteCity;

import java.util.List;
import java.util.Optional;

public interface FavoriteCityService {
    Optional<FavoriteCity> getFavoriteById(Long id);
    List<FavoriteCity> getAllFavorites();
    FavoriteCity addFavorite(FavoriteCity favoriteCity);
    void removeFavorite(Long id);}
