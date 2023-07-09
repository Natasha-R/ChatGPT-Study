package thkoeln.st.st2praktikum.exercise.tidyuprobot.repository;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;

import java.util.UUID;

public interface TidyUpRobotRepository extends CrudRepository<TidyUpRobot, UUID> {

    Iterable<TidyUpRobot> findTidyUpRobotsByRoom(Room room);
}
