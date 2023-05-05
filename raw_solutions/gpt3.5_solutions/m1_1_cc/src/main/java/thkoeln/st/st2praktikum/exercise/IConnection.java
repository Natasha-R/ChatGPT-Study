package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface IConnection {
    UUID getId();
    ISpaceShipDeck getSourceSpaceShipDeck();
    String getSourceCoordinate();
    ISpaceShipDeck getDestinationSpaceShipDeck();
    String getDestinationCoordinate();
}