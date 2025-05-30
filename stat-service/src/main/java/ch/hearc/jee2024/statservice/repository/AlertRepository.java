package ch.hearc.jee2024.statservice.repository;

import ch.hearc.jee2024.statservice.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {

    List<Alert> findByCityIgnoreCase(String city);

}
