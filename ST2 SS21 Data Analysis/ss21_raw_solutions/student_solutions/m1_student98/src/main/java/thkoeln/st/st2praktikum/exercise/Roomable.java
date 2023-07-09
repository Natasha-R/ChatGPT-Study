package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface Roomable extends Identifiable {

    void addObstacle(Passable obstacle);

    void removeObstacle(UUID obstacleID);

    void addOccupiedPosition(Occupied occupiedElement);

    void removeOccupiedPosition(UUID OccupiedFieldID);

    Boolean roomPositionIsEmpty(XYPositionable position);

    XYMovable validateMovement(XYMovable movement);

    void addConnection(Connectable connection);

    Boolean isTransportable(XYPositionable currentPosition, Identifiable targetRoom);

    XYPositionable getNewTransportedPosition(XYPositionable currentPosition, Identifiable targetRoom)
            throws NoDataFoundException, PositionBlockedException;

    void debugPrintRoomStatus();
}
