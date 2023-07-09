package thkoeln.st.st2praktikum.exercise.tidyuprobot;

import org.springframework.data.repository.CrudRepository;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;

import java.util.List;
import java.util.UUID;

public interface TidyUpRobotRepository extends CrudRepository<TidyUpRobot, UUID> {
    public List<String> findByName(String name);
    public TidyUpRobot findByRoomId(UUID roomId);
    public TidyUpRobot findByVector2D(Vector2D vector2D);

}
