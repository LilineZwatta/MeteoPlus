package ch.hearc.jee2024.meteoservice.controller;

import ch.hearc.jee2024.meteoservice.model.FavoriteCity;
import ch.hearc.jee2024.meteoservice.service.FavoriteCityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteCityController {

    private final FavoriteCityService service;

    public FavoriteCityController(FavoriteCityService service) {
        this.service = service;
    }

    @GetMapping
    public List<FavoriteCity> all() {
        return service.getAllFavorites();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FavoriteCity> one(@PathVariable Long id) {
        return service.getFavoriteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<FavoriteCity> create(@RequestBody FavoriteCity fav) {
        FavoriteCity saved = service.addFavorite(fav);
        return ResponseEntity
                .created(URI.create("/api/favorites/" + saved.getId()))
                .body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.removeFavorite(id);
        return ResponseEntity.noContent().build();
    }
}
