package thkoeln.st.st2praktikum.exercise.repository;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.entity.ObstacleEntity;

import java.util.UUID;

public interface ObstacleRepository extends CrudRepository<ObstacleEntity, UUID> {
}
