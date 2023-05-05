package thkoeln.st.st2praktikum.exercise.space.application;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.space.domain.Space;

import java.util.UUID;

public interface SpaceRepository extends CrudRepository<Space, UUID> {
}
