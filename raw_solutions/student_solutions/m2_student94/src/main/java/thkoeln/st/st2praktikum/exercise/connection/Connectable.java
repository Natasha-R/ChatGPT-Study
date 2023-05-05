package thkoeln.st.st2praktikum.exercise.connection;

import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.innerCore.Identifying;

import java.util.UUID;

public interface Connectable extends Identifying {
    Coordinate getSourcePosition();
    Coordinate getDestinationPosition();
    UUID getSourceSpaceId();
    UUID getDestinationSpaceID();

}
