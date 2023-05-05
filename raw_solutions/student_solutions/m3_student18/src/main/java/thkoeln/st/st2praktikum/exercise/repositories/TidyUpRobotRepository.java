package thkoeln.st.st2praktikum.exercise.repositories;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.TidyUpRobot;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TidyUpRobotRepository extends CrudRepository<TidyUpRobot, UUID> {

    @Override
    Optional<TidyUpRobot> findById(UUID uuid);

    List<TidyUpRobot> findAll();

}