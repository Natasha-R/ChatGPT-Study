package thkoeln.st.st2praktikum.exercise.room.repository;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.room.domain.Square;

import java.util.UUID;

public interface SquareRepository extends CrudRepository<Square, UUID> {
}
