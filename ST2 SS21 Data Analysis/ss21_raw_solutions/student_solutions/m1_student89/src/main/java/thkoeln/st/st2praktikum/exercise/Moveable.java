package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public interface Moveable {


     void moveNorth(int moves, Room room);
     void moveEast(int moves, Room room);
     void moveSouth(int moves, Room room);
     void moveWest(int moves, Room room);
}
