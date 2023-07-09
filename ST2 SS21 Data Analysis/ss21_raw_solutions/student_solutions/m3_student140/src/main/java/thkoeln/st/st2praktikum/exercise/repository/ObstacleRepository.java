package thkoeln.st.st2praktikum.exercise.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import thkoeln.st.st2praktikum.exercise.entity.ObstacleEntity;

import java.util.Set;
import java.util.UUID;

public interface ObstacleRepository extends CrudRepository<ObstacleEntity, UUID> {
}
