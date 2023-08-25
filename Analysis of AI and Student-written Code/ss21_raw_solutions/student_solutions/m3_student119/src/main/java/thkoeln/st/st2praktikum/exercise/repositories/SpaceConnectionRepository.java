package thkoeln.st.st2praktikum.exercise.repositories;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.entities.SpaceConnection;

import java.util.UUID;

public interface SpaceConnectionRepository extends CrudRepository<SpaceConnection, UUID> {
}
