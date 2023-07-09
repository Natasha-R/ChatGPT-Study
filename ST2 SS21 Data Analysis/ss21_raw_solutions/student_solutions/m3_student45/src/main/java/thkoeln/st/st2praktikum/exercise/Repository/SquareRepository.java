package thkoeln.st.st2praktikum.exercise.Repository;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.Square;

import java.util.UUID;

public interface SquareRepository extends CrudRepository<Square, UUID> {
}
