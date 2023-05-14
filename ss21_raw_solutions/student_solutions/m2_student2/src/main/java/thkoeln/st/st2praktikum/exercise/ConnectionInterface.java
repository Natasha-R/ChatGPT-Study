package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.Vector2D;

import java.util.UUID;

public interface ConnectionInterface {
    public UUID getConnectionId();
    public UUID getSourceRoomId();
    public UUID getDestinationRoomId();
    public Vector2D getSourceCoordinate();
    public Vector2D getDestinationCoordinate();
}
