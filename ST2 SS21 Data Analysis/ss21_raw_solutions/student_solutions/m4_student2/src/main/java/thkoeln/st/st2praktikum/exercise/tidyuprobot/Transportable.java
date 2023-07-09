package thkoeln.st.st2praktikum.exercise.tidyuprobot;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Command;
import thkoeln.st.st2praktikum.exercise.room.domain.Room;

public interface Transportable {
    public Boolean transportTo(Room currentRoom, Room destinationRoom, Command command);
}
