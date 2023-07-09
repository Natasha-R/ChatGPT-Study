package thkoeln.st.st2praktikum.exercise.space.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ObstacleRepository extends CrudRepository<Obstacle, UUID> {
}
