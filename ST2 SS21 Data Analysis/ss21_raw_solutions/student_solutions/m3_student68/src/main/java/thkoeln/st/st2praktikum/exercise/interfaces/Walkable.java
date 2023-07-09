package thkoeln.st.st2praktikum.exercise.interfaces;

import thkoeln.st.st2praktikum.exercise.Coordinate;

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
