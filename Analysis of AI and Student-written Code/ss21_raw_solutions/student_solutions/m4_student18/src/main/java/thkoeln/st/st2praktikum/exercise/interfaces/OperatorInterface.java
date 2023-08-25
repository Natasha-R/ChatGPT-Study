package thkoeln.st.st2praktikum.exercise.interfaces;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Vector2D;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.TidyUpRobot;

import java.util.List;
import java.util.UUID;

public interface OperatorInterface extends RoomInterface{
    boolean roomIsBlockedByRobot(UUID roomID, Vector2D vector2D, List<TidyUpRobot> tidyUpRobots);
}
