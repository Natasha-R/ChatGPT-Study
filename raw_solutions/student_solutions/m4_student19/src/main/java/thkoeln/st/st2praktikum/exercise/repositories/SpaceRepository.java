package thkoeln.st.st2praktikum.exercise.repositories;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;

import java.util.Optional;
import java.util.UUID;

public interface SpaceRepository extends CrudRepository<Space, UUID> {
    @Override
    Optional<Space> findById(UUID uuid);
}
