package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TidyUpRobotRepository extends CrudRepository<TidyUpRobot, UUID> {
}
