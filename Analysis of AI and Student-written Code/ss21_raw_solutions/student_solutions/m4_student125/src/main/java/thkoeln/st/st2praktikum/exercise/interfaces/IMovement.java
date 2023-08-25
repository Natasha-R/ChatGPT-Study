package thkoeln.st.st2praktikum.exercise.interfaces;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Order;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;

import java.util.HashMap;
import java.util.UUID;

public interface IMovement
{
    boolean move(Order order, HashMap<UUID, Room> rooms);
    boolean moveUp(HashMap<UUID, Room> rooms);
    boolean moveDown(HashMap<UUID, Room> rooms);
    boolean moveRight(HashMap<UUID, Room> rooms);
    boolean moveLeft(HashMap<UUID, Room> rooms);
}
