package thkoeln.st.st2praktikum.exercise.space;

import thkoeln.st.st2praktikum.exercise.cleaningDevice.Occupied;
import thkoeln.st.st2praktikum.exercise.inner.Identifying;
import thkoeln.st.st2praktikum.exercise.inner.Position;

public interface Walkable extends Identifying {
    Boolean isSpaceOccupied (Position position);
    Boolean isInitialPositionFree();
    Position getInitialPosition();
    Boolean isTransportPositionValid(Position transportPosition, Walkable targetRoom);
    Position getTransportDestinationPosition(Position transportPosition, Walkable targetRoom);
    Position getLastPosition(Position targetPosition);
    Position getObstaclePosition(Position currentPosition, Position targetPosition);
    Position getOccupiedPosition(Position currentPosition, Position targetPosition);
    void removeOccupied(Occupied occupied);
    void addOccupied(Occupied occupied);
}