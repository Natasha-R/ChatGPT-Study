package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection {

    private final UUID connectionId;

    private final UUID sourceSpaceId;
    private final String sourceCoordinate;
    private final UUID destinationSpaceId;
    private final String destinationCoordinate;

    public Connection(UUID sourceSpaceId, String sourceCoordinate, UUID destinationSpaceId, String destinationCoordinate) {
        this.connectionId = UUID.randomUUID();
        this.sourceSpaceId = sourceSpaceId;
        this.sourceCoordinate = sourceCoordinate;
        this.destinationSpaceId = destinationSpaceId;
        this.destinationCoordinate = destinationCoordinate;
    }

    public UUID getConnectionId() { return this.connectionId; }
    public UUID getSourceSpaceId() { return this.sourceSpaceId; }
    public String getSourceCoordinate() { return this.sourceCoordinate; }
    public UUID getDestinationSpaceId() { return this.destinationSpaceId; }
    public String getDestinationCoordinate() { return this.destinationCoordinate; }
}
