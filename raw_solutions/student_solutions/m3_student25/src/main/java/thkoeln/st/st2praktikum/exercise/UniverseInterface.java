package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.Direction;
import thkoeln.st.st2praktikum.exercise.SquareNotFoundException;
import thkoeln.st.st2praktikum.exercise.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.SpaceShipDeck;

import java.util.HashMap;
import java.util.UUID;

public interface UniverseInterface {
    SpaceShipDeckRepository getSpaceShipDecks();

    MaintenanceDroidRepository getMaintenanceDroids();

    Coordinate getCoordinates(UUID maintenanceDroidId);

    UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId);

    Boolean isSquareOccupied(Direction direction, UUID squareID, UUID maintenancesDroidID, SpaceShipDeck spaceShipDeck) throws SquareNotFoundException;

    Boolean isTransportOccupied(UUID squareID, UUID maintenancesDroidID);
}
