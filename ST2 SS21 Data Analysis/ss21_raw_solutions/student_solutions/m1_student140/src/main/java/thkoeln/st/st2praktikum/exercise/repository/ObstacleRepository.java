package thkoeln.st.st2praktikum.exercise.repository;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.entity.Obstacle;
import java.util.UUID;

public interface ObstacleRepository extends CrudRepository<Obstacle, UUID> {
}
