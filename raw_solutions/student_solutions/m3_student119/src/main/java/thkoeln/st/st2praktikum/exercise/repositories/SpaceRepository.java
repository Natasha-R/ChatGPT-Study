package thkoeln.st.st2praktikum.exercise.repositories;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.entities.Space;

import java.util.Optional;
import java.util.UUID;

public interface SpaceRepository extends CrudRepository<Space, UUID> {
    Optional<Space> getById(UUID uuid);
}
