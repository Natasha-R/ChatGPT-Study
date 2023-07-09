package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.room.Room;

import java.util.List;

public interface Walkable {
    String walk(String walkCommandString, Room room, List<Walkable> otherWalkablesInRoom);
    Coordinate getCoordinate();
}
