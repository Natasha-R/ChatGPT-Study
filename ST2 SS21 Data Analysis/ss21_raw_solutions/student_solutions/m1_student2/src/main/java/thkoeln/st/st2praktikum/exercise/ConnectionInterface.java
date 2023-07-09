package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.UUID;

public interface ConnectionInterface {
    public UUID getConnectionId();
    public UUID getSourceRoomId();
    public UUID getDestinationRoomId();
    public String getSourceCoordinate();
    public String getDestinationCoordinate();
}
