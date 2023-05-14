package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public interface IRobotActions
{
    String getCurrentPositionAsString();
    boolean executeCommand(String commandString, HashMap<UUID, Room> rooms, HashMap<UUID, Connection> connections);
    boolean move(String moveCommand, HashMap<UUID, Room> rooms);
    boolean spawnAtPosition(Position position, UUID targetRoom, HashMap<UUID, Room> rooms);
    boolean collidesAtPosition(Position position, String moveDirection, HashMap<UUID, Room> rooms);
    boolean collidesWithRobotAtPosition(Position position, HashMap<UUID, Room> rooms);
    boolean transport(HashMap<UUID, Room> rooms, UUID targetRoom);
}
