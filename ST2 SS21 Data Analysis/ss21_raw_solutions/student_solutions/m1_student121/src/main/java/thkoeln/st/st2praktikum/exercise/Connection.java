package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Connection {
    private static List<Connection> connectionList = new ArrayList<>();
    UUID connectionID;
    UUID sourceSpaceShipDeckId;
    String sourceCoordinate;
    UUID destinationSpaceShipDeckId;
    String destinationCoordinate;

    public Connection(UUID connectionID, UUID sourceSpaceShipDeckId, String sourceCoordinate, UUID destinationSpaceShipDeckId, String destinationCoordinate) {
        this.sourceSpaceShipDeckId = sourceSpaceShipDeckId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationSpaceShipDeckId = destinationSpaceShipDeckId;
        this.destinationCoordinate = destinationCoordinate;
        connectionList.add(this);
    }

    public static Connection getConnection(UUID spaceShipDeckId) {
        Connection connection = null;
        for (Connection cnnctn : connectionList) {
            if (cnnctn.getSourceSpaceShipDeckId().equals(spaceShipDeckId)) {
                connection = cnnctn;
            }
        }
        return connection;
    }

    public UUID getSourceSpaceShipDeckId() {
        return sourceSpaceShipDeckId;
    }

    public void setSourceSpaceShipDeckId(UUID sourceSpaceShipDeckId) {
        this.sourceSpaceShipDeckId = sourceSpaceShipDeckId;
    }

    public String getSourceCoordinate() {
        return sourceCoordinate;
    }

    public void setSourceCoordinate(String sourceCoordinate) {
        this.sourceCoordinate = sourceCoordinate;
    }

    public UUID getDestinationSpaceShipDeckId() {
        return destinationSpaceShipDeckId;
    }

    public void setDestinationSpaceShipDeckId(UUID destinationSpaceShipDeckId) {
        this.destinationSpaceShipDeckId = destinationSpaceShipDeckId;
    }

    public String getDestinationCoordinate() {
        return destinationCoordinate;
    }

    public void setDestinationCoordinate(String destinationCoordinate) {
        this.destinationCoordinate = destinationCoordinate;
    }

    public UUID getConnectionID() {
        return connectionID;
    }

    public void setConnectionID(UUID connectionID) {
        this.connectionID = connectionID;
    }
}
