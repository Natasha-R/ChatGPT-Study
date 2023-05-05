package thkoeln.st.st2praktikum.exercise;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.Wall;

import java.util.UUID;

public interface WallRepository extends CrudRepository<Wall, UUID> {
}
