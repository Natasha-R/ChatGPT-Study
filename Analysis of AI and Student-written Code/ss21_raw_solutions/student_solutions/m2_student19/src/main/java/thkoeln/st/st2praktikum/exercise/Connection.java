package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public class Connection {

    private final UUID connectionId;

    private final UUID sourceSpaceId;
    private final Vector2D sourceVector2D;
    private final UUID destinationSpaceId;
    private final Vector2D destinationVector2D;

    public Connection(UUID sourceSpaceId, Vector2D sourceVector2D, UUID destinationSpaceId, Vector2D destinationVector2S) {
        this.connectionId = UUID.randomUUID();
        this.sourceSpaceId = sourceSpaceId;
        this.sourceVector2D = sourceVector2D;
        this.destinationSpaceId = destinationSpaceId;
        this.destinationVector2D = destinationVector2S;
    }

    public UUID getConnectionId() { return this.connectionId; }
    public UUID getSourceSpaceId() { return this.sourceSpaceId; }
    public Vector2D getSourceVector2D() { return this.sourceVector2D; }
    public UUID getDestinationSpaceId() { return this.destinationSpaceId; }
    public Vector2D getDestinationVector2D() { return this.destinationVector2D; }
}
