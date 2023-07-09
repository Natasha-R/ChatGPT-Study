package thkoeln.st.st2praktikum.exercise.universe;

import thkoeln.st.st2praktikum.exercise.command.Direction;
import thkoeln.st.st2praktikum.exercise.exception.SquareNotFoundException;
import thkoeln.st.st2praktikum.exercise.maintenanceDroid.MaintenanceDroid;
import thkoeln.st.st2praktikum.exercise.spaceShipDeck.SpaceShipDeck;

import java.util.HashMap;
import java.util.UUID;

public interface UniverseInterface {
    HashMap<UUID, SpaceShipDeck> getSpaceShipDeckHashMap();
    HashMap<UUID, MaintenanceDroid> getMaintenanceDroidHashMap();
    String getCoordinates(UUID maintenanceDroidId);
    UUID getMaintenanceDroidSpaceShipDeckId(UUID maintenanceDroidId);
    Boolean isSquareOccupied(Direction direction, UUID squareID, UUID maintenancesDroidID, SpaceShipDeck spaceShipDeck) throws SquareNotFoundException;
    Boolean isTransportOccupied(UUID squareID, UUID maintenancesDroidID);
}
