package thkoeln.st.st2praktikum.exercise.Repositories;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.Entities.Obstacles;

import java.util.UUID;

public interface ObstaclesRepository extends CrudRepository<Obstacles, UUID> {
}
