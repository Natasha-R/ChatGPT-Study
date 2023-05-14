package thkoeln.st.st2praktikum.exercise.repositories;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.entities.TransportTechnology;

import java.util.Optional;
import java.util.UUID;

public interface TransportTechnologyRepository extends CrudRepository<TransportTechnology, UUID> {
    boolean existsByTechnology(String technology);
    Optional<TransportTechnology> findByTechnology(String technology);
}
