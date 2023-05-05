package thkoeln.st.st2praktikum.exercise.interfaces;

import thkoeln.st.st2praktikum.exercise.TidyUpRobot;
import thkoeln.st.st2praktikum.exercise.Vector2D;

import java.util.HashMap;
import java.util.UUID;

public interface RoomInterface {
    boolean roomIsBlockedByRobot(UUID roomID, Vector2D vector2D, HashMap<UUID, TidyUpRobot> tidyUpRobots);
}
