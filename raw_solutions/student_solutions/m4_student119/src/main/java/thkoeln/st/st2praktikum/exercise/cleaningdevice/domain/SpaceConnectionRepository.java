package thkoeln.st.st2praktikum.exercise.cleaningdevice.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SpaceConnectionRepository extends CrudRepository<SpaceConnection, UUID> {
}
