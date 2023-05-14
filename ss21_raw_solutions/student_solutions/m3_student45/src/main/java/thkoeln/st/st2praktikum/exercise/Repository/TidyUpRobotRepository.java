package thkoeln.st.st2praktikum.exercise.Repository;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.TidyUpRobot;

import java.util.Optional;
import java.util.UUID;

public interface TidyUpRobotRepository extends CrudRepository<TidyUpRobot, UUID> {
}
