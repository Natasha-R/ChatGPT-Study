package thkoeln.st.st2praktikum.exercise.interfaces;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;

import java.util.HashMap;
import java.util.UUID;

public interface ISpawning
{
    boolean spawnAtPosition(Coordinate position, UUID targetRoom, HashMap<UUID, Room> rooms);
    boolean transport(HashMap<UUID, Room> rooms, UUID targetRoom);
}
