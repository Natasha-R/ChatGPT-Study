package thkoeln.st.st2praktikum.exercise.Repository;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.Obstacle;

import java.util.UUID;

public interface ObstacleRepository extends CrudRepository<Obstacle, UUID> {
}
