package thkoeln.st.st2praktikum.exercise.interfaces;

import thkoeln.st.st2praktikum.exercise.Coordinate;

public interface Connectable extends Identifiable {
    Coordinate getSourcePosition();
    Coordinate getDestinationPosition();
    Walkable getSourceRoom();
    Walkable getDestinationRoom();
}
