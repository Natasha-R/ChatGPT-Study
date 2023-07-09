package thkoeln.st.st2praktikum.exercise.interfaces;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;

import java.util.HashMap;
import java.util.UUID;

public interface IFieldOccupation
{
    void occupyFieldAtPosition(Coordinate position, HashMap<UUID, Room> rooms);
    void releaseFieldAtPosition(Coordinate position, HashMap<UUID, Room> rooms);
}
