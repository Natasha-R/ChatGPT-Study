package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection {
    private UUID uuid;
    private UUID sourceSpaceId;
    private Vector2D sourceVector;
    private UUID destinationSpaceId;
    private Vector2D destinationVector;
    public UUID getUuid() {
        return uuid;
    }

    public UUID getSourceSpaceId() {
        return sourceSpaceId;
    }

    public Vector2D getSourceVector() {
        return sourceVector;
    }

    public Vector2D getDestinationVector() {
        return destinationVector;
    }

    public UUID getDestinationSpaceId() {
        return destinationSpaceId;
    }

    public Connection(UUID sourceSpaceId, int sourceCoordinateX, int sourceCoordinateY, UUID destinationSpaceId, int destinationCoordinateX, int destinationCoordinateY)
    {
        uuid = UUID.randomUUID();
        this.sourceSpaceId = sourceSpaceId;
        this.destinationSpaceId = destinationSpaceId;

        this.sourceVector =  new Vector2D(sourceCoordinateX, sourceCoordinateY);

        this.destinationVector =  new Vector2D(destinationCoordinateX, destinationCoordinateY);
    }

    public Connection(UUID sourceSpaceId, Vector2D source, UUID destinationSpaceId, Vector2D destination)
    {
        uuid = UUID.randomUUID();
        this.sourceSpaceId = sourceSpaceId;
        this.destinationSpaceId = destinationSpaceId;

        this.sourceVector =  source;

        this.destinationVector =  destination;
    }
}
