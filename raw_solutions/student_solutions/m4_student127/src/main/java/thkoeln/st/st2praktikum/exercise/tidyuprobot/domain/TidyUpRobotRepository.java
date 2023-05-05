package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TidyUpRobotRepository extends CrudRepository<TidyUpRobot, UUID> {
    List<TidyUpRobot> findByRoomIdAndRoomIdIsNotNull(UUID uuid);
}
