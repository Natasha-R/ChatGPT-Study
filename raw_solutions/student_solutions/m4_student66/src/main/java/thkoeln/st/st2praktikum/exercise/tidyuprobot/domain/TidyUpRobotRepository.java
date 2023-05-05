package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TidyUpRobotRepository extends CrudRepository<TidyUpRobot, UUID> {
    Optional<TidyUpRobot> findByUuid(UUID uuid);
}
