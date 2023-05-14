package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection {
    private UUID id;
    private UUID sourceSpaceShipDeckId;
    private String sourceCoordinate;
    private UUID destinationSpaceShipDeckId;
    private String destinationCoordinate;

    public Connection(UUID id, UUID sourceSpaceShipDeckId, String sourceCoordinate, UUID destinationSpaceShipDeckId, String destinationCoordinate) {
        this.id = id;
        this.sourceSpaceShipDeckId = sourceSpaceShipDeckId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationSpaceShipDeckId = destinationSpaceShipDeckId;
        this.destinationCoordinate = destinationCoordinate;
    }

    public UUID getId() {
        return id;
    }

    public UUID getSourceSpaceShipDeckId() {
        return sourceSpaceShipDeckId;
    }

    public String getSourceCoordinate() {
        return sourceCoordinate;
    }

    public UUID getDestinationSpaceShipDeckId() {
        return destinationSpaceShipDeckId;
    }

    public String getDestinationCoordinate() {
        return destinationCoordinate;
    }

    public static Connection parseConnectionString(String connectionString) {
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
