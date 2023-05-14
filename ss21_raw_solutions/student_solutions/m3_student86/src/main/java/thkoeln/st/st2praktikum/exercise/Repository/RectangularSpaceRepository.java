package thkoeln.st.st2praktikum.exercise.Repository;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.RectangularSpace;

import java.util.UUID;

public interface RectangularSpaceRepository extends CrudRepository<RectangularSpace, UUID> {
}
