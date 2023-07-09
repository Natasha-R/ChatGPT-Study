package thkoeln.st.st2praktikum.exercise.space;

import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.cleaningDevice.Occupied;
import thkoeln.st.st2praktikum.exercise.innerCore.Identifying;

public interface Walkable extends Identifying {
    Boolean isSpaceOccupied (Coordinate position);
    Boolean isInitialPositionFree();
    Coordinate getInitialPosition();
    Boolean isTransportPositionValid(Coordinate transportPosition, Walkable targetRoom);
    Coordinate getTransportDestinationPosition(Coordinate transportPosition, Walkable targetRoom);
    Coordinate getLastPosition(Coordinate targetPosition);
    Coordinate getObstaclePosition(Coordinate currentPosition, Coordinate targetPosition);
    Coordinate getOccupiedPosition(Coordinate currentPosition, Coordinate targetPosition);
    void removeOccupied(Occupied occupied);
    void addOccupied(Occupied occupied);
    int getHeight();
    int getWidth();

}