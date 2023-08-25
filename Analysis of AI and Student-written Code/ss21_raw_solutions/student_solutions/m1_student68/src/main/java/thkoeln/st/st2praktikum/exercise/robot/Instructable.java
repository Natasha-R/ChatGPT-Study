package thkoeln.st.st2praktikum.exercise.robot;

import thkoeln.st.st2praktikum.exercise.core.Identifiable;
import thkoeln.st.st2praktikum.exercise.core.Coordinate;
import thkoeln.st.st2praktikum.exercise.core.RobotCommand;
import thkoeln.st.st2praktikum.exercise.room.Walkable;

public interface Instructable extends Identifiable {
    Boolean readCommand (RobotCommand robotCommand);
    Coordinate getPosition();
    Walkable getRoom();
}
