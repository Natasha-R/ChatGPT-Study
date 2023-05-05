package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TidyUpRobotRepository extends CrudRepository<TidyUpRobot, UUID> {

    @Override
    Optional<TidyUpRobot> findById(UUID uuid);

    Optional<TidyUpRobot> findByName(String name);

    List<TidyUpRobot> findAll();

}