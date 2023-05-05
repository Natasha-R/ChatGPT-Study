package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class ConnectionParser implements IConnectionParser {
    public IConnection parseConnectionString(String connectionString) {
        String[] parts = connectionString.split(",");
        if (parts.length != 5) {
            throw new IllegalArgumentException("Invalid connection string: " + connectionString);
        }
        UUID id = UUID.fromString(parts[0]);
        UUID sourceSpaceShipDeckId = UUID.fromString(parts[1]);
        String sourceCoordinate = parts[2];
        UUID destinationSpaceShipDeckId = UUID.fromString(parts[3]);
        String destinationCoordinate = parts[4];
        return new Connection(id, sourceSpaceShipDeckId, sourceCoordinate, destinationSpaceShipDeckId, destinationCoordinate);
    }
}
