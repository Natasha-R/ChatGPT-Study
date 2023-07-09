package thkoeln.st.st2praktikum.exercise.repository;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.space.domain.ObstacleEntity;

import java.util.UUID;

public interface ObstacleRepository extends CrudRepository<ObstacleEntity, UUID> {
}
