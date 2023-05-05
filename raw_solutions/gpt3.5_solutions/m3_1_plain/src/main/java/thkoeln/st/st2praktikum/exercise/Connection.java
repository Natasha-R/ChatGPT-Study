package thkoeln.st.st2praktikum.exercise;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Connection {
    @Id
    private UUID id;
    private UUID sourceSpaceShipDeckId;
    private String sourceCoordinate;
    private UUID destinationSpaceShipDeckId;
    private String destinationCoordinate;
    private String transportCategory;

    public Connection(UUID id, UUID sourceSpaceShipDeckId, String sourceCoordinate, UUID destinationSpaceShipDeckId, String destinationCoordinate, String transportCategory) {
        this.id = id;
        this.sourceSpaceShipDeckId = sourceSpaceShipDeckId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationSpaceShipDeckId = destinationSpaceShipDeckId;
        this.destinationCoordinate = destinationCoordinate;
        this.transportCategory = transportCategory;
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
    
    public String getTransportCategory() {
        return transportCategory;
    }

    public static Connection parseConnectionString(String connectionString) {
        String[] parts = connectionString.split(",");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Invalid connection string: " + connectionString);
        }
        UUID id = UUID.fromString(parts[0]);
        UUID sourceSpaceShipDeckId = UUID.fromString(parts[1]);
        String sourceCoordinate = parts[2];
        UUID destinationSpaceShipDeckId = UUID.fromString(parts[3]);
        String destinationCoordinate = parts[4];
        String transportCategory = parts[5];
        return new Connection(id, sourceSpaceShipDeckId, sourceCoordinate, destinationSpaceShipDeckId, destinationCoordinate, transportCategory);
    }
}

