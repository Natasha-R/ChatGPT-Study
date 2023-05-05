package thkoeln.st.st2praktikum.exercise.room.domain;


import thkoeln.st.st2praktikum.exercise.Identifiable;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.tidyuprobot.domain.Occupier;

public interface Walkable extends Identifiable {
    Boolean isFieldOccupied (Coordinate coordinate);
    Boolean isSpawnCoordinateAvailable();
    Coordinate getSpawnCoordinate();
    Coordinate getBorderBlockPosition(Coordinate targetCoordinate);
    Coordinate getBarrierBlockPosition(Coordinate currentCoordinate, Coordinate targetCoordinate);
    Coordinate getOccupierBlockPosition(Coordinate currentCoordinate, Coordinate targetCoordinate);
    void removeOccupier(Occupier occupier);
    void addOccupier(Occupier occupier);
}
