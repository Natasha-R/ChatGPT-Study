package thkoeln.st.st2praktikum.exercise.interfaces;

import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.core.RobotCommand;

import java.util.UUID;

public interface Instructable extends Identifiable {
    Boolean readCommand (RobotCommand robotCommand);
    Coordinate getCoordinate();
    Walkable getRoom();
    UUID getRoomId();
}
