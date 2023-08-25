package thkoeln.st.st2praktikum.exercise.Repository;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.Space;

import java.util.UUID;

public interface SpaceRepository extends CrudRepository<Space, UUID> {
}
