package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Task;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskType;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;
import thkoeln.st.st2praktikum.exercise.room.domain.RoomRepository;

import java.util.HashMap;
import java.util.UUID;

public interface IRobot
{
    boolean executeCommand(Task task, HashMap<UUID, Room> rooms);
    boolean move(Room room, TaskType type, int steps);
    boolean transport(java.util.UUID destinationRoomId, HashMap<UUID, Room> rooms);
    boolean enter(Room room, java.util.UUID destinationRoomId);
}
