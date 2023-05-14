package thkoeln.st.st2praktikum.exercise.Interfaces;

import thkoeln.st.st2praktikum.exercise.Entities.Connection;
import thkoeln.st.st2praktikum.exercise.Entities.SpaceShipDeck;

public interface ImplementConnectionable {
    Connection createConnection(SpaceShipDeck sourceSpaceShipDeck, String sourceCoordinate, SpaceShipDeck destinationSpaceShipDeck, String destinationCoordinate);



    void addConnectionOnSpaceShipDeck(SpaceShipDeck sourceSpaceShipDeck, Connection connection);
}
