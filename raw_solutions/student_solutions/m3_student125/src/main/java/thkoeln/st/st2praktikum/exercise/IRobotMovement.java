package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public interface IRobotMovement
{
    boolean executeCommand(Order order, HashMap<UUID, Room> rooms, HashMap<UUID, Connection> connections);
    boolean move(Order order, HashMap<UUID, Room> rooms);
    boolean spawnAtPosition(Coordinate position, UUID targetRoom, HashMap<UUID, Room> rooms);
    boolean collidesAtPosition(Coordinate position, String moveDirection, HashMap<UUID, Room> rooms);
    boolean collidesWithRobotAtPosition(Coordinate position, HashMap<UUID, Room> rooms);
    boolean transport(HashMap<UUID, Room> rooms, UUID targetRoom);
}
