package thkoeln.st.st2praktikum.exercise.room;

import thkoeln.st.st2praktikum.exercise.core.Identifiable;
import thkoeln.st.st2praktikum.exercise.core.Coordinate;
import thkoeln.st.st2praktikum.exercise.robot.Occupier;

public interface Walkable extends Identifiable {
    Boolean isFieldOccupied (Coordinate coordinate);
    Boolean isSpawnCoordinateAvailable();
    Coordinate getSpawnCoordinate();
    Boolean isValidTransportPosition(Coordinate transportCoordinate, Walkable targetRoom);
    Coordinate getTransportDestinationPosition(Coordinate transportCoordinate, Walkable targetRoom);
    Coordinate getBorderBlockPosition(Coordinate targetCoordinate);
    Coordinate getBarrierBlockPosition(Coordinate currentCoordinate, Coordinate targetCoordinate);
    Coordinate getOccupierBlockPosition(Coordinate currentCoordinate, Coordinate targetCoordinate);
    void removeOccupier(Occupier occupier);
    void addOccupier(Occupier occupier);
}
