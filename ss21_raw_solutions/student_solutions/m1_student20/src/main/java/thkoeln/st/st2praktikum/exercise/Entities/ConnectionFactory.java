package thkoeln.st.st2praktikum.exercise.Entities;

import thkoeln.st.st2praktikum.exercise.Interfaces.ImplementConnectionable;

public class ConnectionFactory implements ImplementConnectionable {
     private Connection connection;
    @Override
    public Connection createConnection(SpaceShipDeck sourceSpaceShipDeck, String sourceCoordinate, SpaceShipDeck destinationSpaceShipDeck, String destinationCoordinate){
        CoordinateStringReader reader = new CoordinateStringReader();
        connection = new Connection();
        connection.setSourceSpaceShipDeck((SpaceShipDeck) sourceSpaceShipDeck);
        connection.setSourceCoordinates(reader.readCoordinatesOfString(sourceCoordinate));
        connection.setDestinationSpaceShipDeck((SpaceShipDeck) destinationSpaceShipDeck);
        connection.setDestinationCoordinates(reader.readCoordinatesOfString(destinationCoordinate));
        return connection;
    }
    @Override
    public void addConnectionOnSpaceShipDeck(SpaceShipDeck sourceSpaceShipDeck, Connection connection){
        sourceSpaceShipDeck.addConnectionOnSpaceShipDeck(connection);
    }
}
