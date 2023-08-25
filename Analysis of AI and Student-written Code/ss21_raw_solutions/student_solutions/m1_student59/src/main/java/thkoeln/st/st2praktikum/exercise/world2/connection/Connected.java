package thkoeln.st.st2praktikum.exercise.world2.connection;

import thkoeln.st.st2praktikum.exercise.world2.coordinate.Coordinate;

import java.util.UUID;

public interface Connected {

    Connection getConnection();
    boolean connectable(UUID sourceFieldId, Coordinate sourceCoordinate, UUID destinationFieldId);
}
