package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface ConnectionInterface {
    public UUID getTransportSystemId();
    public UUID getSourceRoomId();
    public UUID getDestinationRoomId();
    public Vector2D getSourceCoordinate();
    public Vector2D getDestinationCoordinate();
}
