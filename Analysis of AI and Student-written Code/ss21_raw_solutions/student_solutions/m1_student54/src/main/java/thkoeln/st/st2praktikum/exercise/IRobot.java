package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public interface IRobot
{
    boolean executeCommand(String commandString, HashMap<UUID, Room> rooms, HashMap<UUID, Connection> connections);
    boolean move(Room room, String command, String parameter);
    boolean transport(String parameter, HashMap<UUID, Room> rooms, HashMap<UUID, Connection> connections);
    boolean enlist(Room room, String destinationRoomId);
}
