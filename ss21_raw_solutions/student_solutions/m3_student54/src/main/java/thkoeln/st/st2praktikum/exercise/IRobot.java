package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public interface IRobot
{
    boolean executeCommand(Task task, HashMap<UUID, Room> rooms, HashMap<UUID, Connection> connections);
    boolean move(Room room, TaskType type, int steps);
    boolean transport(UUID destinationRoomId, HashMap<UUID, Room> rooms, HashMap<UUID, Connection> connections);
    boolean enter(Room room, UUID destinationRoomId);
}
