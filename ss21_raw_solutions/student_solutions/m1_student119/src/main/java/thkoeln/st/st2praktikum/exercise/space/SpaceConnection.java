package thkoeln.st.st2praktikum.exercise.space;

import java.awt.*;
import java.util.UUID;

public class SpaceConnection {
    private final ISpace source;
    private final Point sourcePosition;
    private final ISpace destination;
    private final Point destinationPosition;

    private final UUID uuid;

    public SpaceConnection(UUID uuid, ISpace source, Point sourcePosition, ISpace destination, Point destinationPosition) {
        this.source = source;
        this.sourcePosition = sourcePosition;
        this.destination = destination;
        this.destinationPosition = destinationPosition;
        this.uuid = uuid;
    }

    public ISpace getSource() {
        return source;
    }

    public Point getSourcePosition() {
        return sourcePosition;
    }

    public ISpace getDestination() {
        return destination;
    }

    public Point getDestinationPosition() {
        return destinationPosition;
    }

    public UUID getUuid() {
        return uuid;
    }
}
