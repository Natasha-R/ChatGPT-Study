package thkoeln.st.st2praktikum.exercise.repositories;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.Obstacle;
import thkoeln.st.st2praktikum.exercise.TidyUpRobot;

import java.util.UUID;

public interface TidyUpRobotRepository extends CrudRepository<TidyUpRobot, UUID> {
}
