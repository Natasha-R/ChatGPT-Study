package thkoeln.st.st2praktikum.exercise.repository;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.entity.Connection;
import thkoeln.st.st2praktikum.exercise.entity.ObstacleEntity;
import thkoeln.st.st2praktikum.exercise.entity.TransportCategory;

import java.util.List;
import java.util.UUID;

public interface TransportCategoryRepository extends CrudRepository<TransportCategory, UUID>{
}
