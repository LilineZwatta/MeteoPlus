package ch.hearc.jee2024.meteoservice.controller;

import ch.hearc.jee2024.meteoservice.model.FavoriteCity;
import ch.hearc.jee2024.meteoservice.service.FavoriteCityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/favorites", produces = "application/json")
public class FavoriteCityController {

    private final FavoriteCityService service;

    public FavoriteCityController(FavoriteCityService service) {
        this.service = service;
    }

    @Operation(summary = "Récupère toutes les villes favorites")
    @ApiResponse(responseCode = "200", description = "Liste des favoris retournée")
    @GetMapping
    public List<FavoriteCity> all() {
        return service.getAllFavorites();
    }

    @Operation(summary = "Récupère une ville favorite par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ville favorite trouvée"),
            @ApiResponse(responseCode = "404", description = "Ville favorite introuvable")
    })
    @GetMapping("/{id}")
    public ResponseEntity<FavoriteCity> one(@PathVariable Long id) {
        return service.getFavoriteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Ajoute une nouvelle ville favorite")
    @ApiResponse(responseCode = "201", description = "Ville favorite ajoutée avec succès")
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<FavoriteCity> create(@RequestBody FavoriteCity fav) {
        FavoriteCity saved = service.addFavorite(fav);
        return ResponseEntity
                .created(URI.create("/api/favorites/" + saved.getId()))
                .body(saved);
    }

    @Operation(summary = "Supprime une ville favorite par son ID")
    @ApiResponse(responseCode = "204", description = "Suppression réussie")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.removeFavorite(id);
        return ResponseEntity.noContent().build();
    }
}
