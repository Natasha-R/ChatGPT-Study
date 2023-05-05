package thkoeln.st.st2praktikum.exercise.tidyuprobot;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;

public interface Movable {
    public Boolean moveNorth(Room room, Command command);
    public Boolean moveEast(Room room, Command command);
    public Boolean moveSouth(Room room, Command command);
    public Boolean moveWest(Room room, Command command);
}
