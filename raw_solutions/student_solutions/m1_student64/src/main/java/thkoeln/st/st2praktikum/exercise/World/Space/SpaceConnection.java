package thkoeln.st.st2praktikum.exercise.World.Space;

import thkoeln.st.st2praktikum.exercise.World.IHasUUID;
import thkoeln.st.st2praktikum.exercise.World.Point;

import java.util.UUID;

public class SpaceConnection implements IHasUUID {
    private UUID uuid;
    private Space source;
    private Point sourcePosition;
    private Space destination;
    private Point targetPosition;

    public SpaceConnection(Space source, Point sourcePosition, Space destination, Point targetPosition) {
        this.source = source;
        this.sourcePosition = sourcePosition;
        this.destination = destination;
        this.targetPosition = targetPosition;
    }

    public Space getSource() {
        return source;
    }

    public void setSource(Space source) {
        this.source = source;
    }

    public Point getSourcePosition() {
        return sourcePosition;
    }

    public void setSourcePosition(Point sourcePosition) {
        this.sourcePosition = sourcePosition;
    }

    public Space getDestination() {
        return destination;
    }

    public void setDestination(Space destination) {
        this.destination = destination;
    }

    public Point getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(Point targetPosition) {
        this.targetPosition = targetPosition;
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }
}
