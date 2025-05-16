
package ch.hearc.jee2024.meteoservice.repository;
import ch.hearc.jee2024.meteoservice.model.FavoriteCity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FavoriteCityRepository extends CrudRepository<FavoriteCity, Long> {
}
