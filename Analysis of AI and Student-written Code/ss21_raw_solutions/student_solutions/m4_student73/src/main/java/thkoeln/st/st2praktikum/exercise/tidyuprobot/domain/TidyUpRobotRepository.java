package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;

import java.util.Optional;
import java.util.UUID;

public interface TidyUpRobotRepository extends CrudRepository<TidyUpRobot, UUID> {
    Optional<TidyUpRobot> findById (UUID uuid);
}
