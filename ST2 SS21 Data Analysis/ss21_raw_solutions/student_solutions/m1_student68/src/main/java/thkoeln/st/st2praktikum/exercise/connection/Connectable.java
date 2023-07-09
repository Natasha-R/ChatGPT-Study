package thkoeln.st.st2praktikum.exercise.connection;

import thkoeln.st.st2praktikum.exercise.core.Identifiable;
import thkoeln.st.st2praktikum.exercise.core.Coordinate;
import thkoeln.st.st2praktikum.exercise.room.Walkable;

public interface Connectable extends Identifiable {
    Coordinate getSourcePosition();
    Coordinate getDestinationPosition();
    Walkable getSourceRoom();
    Walkable getDestinationRoom();
}
