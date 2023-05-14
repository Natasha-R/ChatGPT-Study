package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface Moveable {
    Boolean moveTo(Task task, Room room);
    Boolean teleport(Task task, Room currentRoom, Room travelToRoom);
    Boolean placeInRoom(Room room);
}
