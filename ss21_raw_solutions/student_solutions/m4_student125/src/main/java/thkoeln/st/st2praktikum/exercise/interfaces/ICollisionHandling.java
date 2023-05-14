package thkoeln.st.st2praktikum.exercise.interfaces;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;

import java.util.HashMap;
import java.util.UUID;

public interface ICollisionHandling
{
    boolean collidesAtPosition(Coordinate position, String moveDirection, HashMap<UUID, Room> rooms);
    boolean collidesWithRobotAtPosition(Coordinate position, HashMap<UUID, Room> rooms);
}
