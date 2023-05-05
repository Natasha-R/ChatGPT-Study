package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;

import java.util.UUID;

public interface Moveable {
    Boolean moveTo(Task task, Room room);
    Boolean teleport(Task task, Room currentRoom, Room travelToRoom);
    Boolean placeInRoom(Room room);
}
