package thkoeln.st.st2praktikum.exercise.cleaningdevice.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface TransportTechnologyRepository extends CrudRepository<TransportTechnology, UUID> {
    boolean existsByTechnology(String technology);
    Optional<TransportTechnology> findByTechnology(String technology);
}
