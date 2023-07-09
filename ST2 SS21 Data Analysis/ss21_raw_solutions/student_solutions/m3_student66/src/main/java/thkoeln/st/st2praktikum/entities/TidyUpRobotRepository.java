package thkoeln.st.st2praktikum.entities;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.TidyUpRobot;

import java.util.UUID;

public interface TidyUpRobotRepository extends CrudRepository<TidyUpRobot, UUID> {
    TidyUpRobot findByUuid(UUID uuid);
}
