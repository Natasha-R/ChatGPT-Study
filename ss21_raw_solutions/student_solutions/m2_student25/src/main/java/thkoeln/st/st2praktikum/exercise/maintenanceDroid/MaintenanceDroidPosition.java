package thkoeln.st.st2praktikum.exercise.maintenanceDroid;

import java.util.UUID;

public class MaintenanceDroidPosition {
    private UUID spaceShipDeckID;
    private UUID squareID;

    public MaintenanceDroidPosition(UUID spaceShipDeckID, UUID squareID) {
        this.spaceShipDeckID = spaceShipDeckID;
        this.squareID = squareID;
    }

    public UUID getSpaceShipDeckID() {
        return spaceShipDeckID;
    }

    public void setSpaceShipDeckID(UUID spaceShipDeckID) {
        this.spaceShipDeckID = spaceShipDeckID;
    }

    public UUID getSquareID() {
        return squareID;
    }

    public void setSquareID(UUID squareID) {
        this.squareID = squareID;
    }
}
