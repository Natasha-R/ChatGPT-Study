package thkoeln.st.st2praktikum.exercise.cleaningdevice.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpaceRepository extends CrudRepository<Space, UUID> {
    Optional<Space> getById(UUID uuid);
}
