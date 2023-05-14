package thkoeln.st.st2praktikum.exercise.interfaces;

import thkoeln.st.st2praktikum.exercise.entities.Coordinates;
import thkoeln.st.st2praktikum.exercise.entities.TidyUpRobot;

import java.util.HashMap;
import java.util.UUID;

public interface OperatorInterface extends RoomInterface{
    boolean roomIsBlockedByRobot(UUID roomID, Coordinates coordinates, HashMap<UUID, TidyUpRobot> tidyUpRobots);
}
