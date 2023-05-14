package thkoeln.st.st2praktikum.exercise.connection;

import thkoeln.st.st2praktikum.exercise.coordinate.Coordinate;

import java.util.UUID;

public interface Jumpable {
     boolean connectable(UUID sourceFieldId, Coordinate sourceCoordinate, UUID destinationFieldId);
}
