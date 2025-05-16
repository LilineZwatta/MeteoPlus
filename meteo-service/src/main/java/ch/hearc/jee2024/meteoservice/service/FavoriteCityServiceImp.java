package ch.hearc.jee2024.meteoservice.service;



import ch.hearc.jee2024.meteoservice.model.FavoriteCity;
import ch.hearc.jee2024.meteoservice.repository.FavoriteCityRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class FavoriteCityServiceImp implements FavoriteCityService {
    private final FavoriteCityRepository repo;

    public FavoriteCityServiceImp(FavoriteCityRepository repo) {
        this.repo = repo;
    }
    /**
     * Récupère une ville favorite par son id.
     */
    @Override
    public Optional<FavoriteCity> getFavoriteById(Long id) {
        return repo.findById(id);
    }

    /**
     * Récupère toutes les villes favorites.
     */
    @Override
    public List<FavoriteCity> getAllFavorites() {
        Iterable<FavoriteCity> all = repo.findAll();
        List<FavoriteCity> list = new ArrayList<>();
        all.forEach(list::add);
        return list;
    }

    /**
     * Ajoute une nouvelle ville favorite.
     * @throws IllegalArgumentException si favoriteCity est null
     */
    @Override
    public FavoriteCity addFavorite(FavoriteCity favoriteCity) {
        if (favoriteCity == null) {
            throw new IllegalArgumentException("Favorite city cannot be null");
        }
        return repo.save(favoriteCity);
    }

    /**
     * Supprime une ville favorite par son id.
     * @throws IllegalStateException si l'id n'existe pas
     */
    @Override
    public void removeFavorite(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalStateException("Favorite city not found (id=" + id + ")");
        }
        repo.deleteById(id);
    }
}