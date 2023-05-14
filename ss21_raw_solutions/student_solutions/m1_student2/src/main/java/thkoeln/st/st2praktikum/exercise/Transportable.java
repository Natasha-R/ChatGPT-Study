package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface Transportable {
    public Boolean transportTo(Room currentRoom, Room destinationRoom, String command);
}
