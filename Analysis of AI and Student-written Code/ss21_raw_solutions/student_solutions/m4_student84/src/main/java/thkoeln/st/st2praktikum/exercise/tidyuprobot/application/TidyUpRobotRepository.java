package thkoeln.st.st2praktikum.exercise.tidyuprobot.application;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Point;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;

import java.util.List;
import java.util.UUID;

public interface TidyUpRobotRepository extends CrudRepository<TidyUpRobot, UUID> {
    public List<TidyUpRobot> findByRoomIdAndCurrentPosition(UUID roomId, Point currentPosition);
}
