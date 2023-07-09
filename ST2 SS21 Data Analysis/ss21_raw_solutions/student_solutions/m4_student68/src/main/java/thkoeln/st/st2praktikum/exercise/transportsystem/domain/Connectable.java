package thkoeln.st.st2praktikum.exercise.transportsystem.domain;



import thkoeln.st.st2praktikum.exercise.Identifiable;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;

import java.util.UUID;

public interface Connectable extends Identifiable {
    Coordinate getSourcePosition();
    Coordinate getDestinationPosition();
    UUID getSourceRoomId();
    UUID getDestinationRoomId();
}
