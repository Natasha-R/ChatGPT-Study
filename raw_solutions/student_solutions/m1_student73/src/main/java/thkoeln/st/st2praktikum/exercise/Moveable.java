package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface Moveable {
    Boolean moveTo(String moveCommandString, Room room);
    Boolean teleport(String teleportCommandString,Room currentRoom, Room travelToRoom);
    Boolean placeInRoom(Room room);
}
