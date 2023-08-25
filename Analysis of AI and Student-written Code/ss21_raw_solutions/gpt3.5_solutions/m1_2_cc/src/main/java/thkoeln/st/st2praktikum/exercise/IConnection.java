package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface IConnection {
    UUID getId();
    UUID getSourceSpaceShipDeckId();
    String getSourceCoordinate();
    UUID getDestinationSpaceShipDeckId();
    String getDestinationCoordinate();
}
