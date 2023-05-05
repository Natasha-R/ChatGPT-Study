package thkoeln.st.st2praktikum.exercise.cleaningDevice;

import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.Order;
import thkoeln.st.st2praktikum.exercise.innerCore.Identifying;
import thkoeln.st.st2praktikum.exercise.space.Walkable;

public interface Instructable extends Identifying {
    Boolean analysedCommand (Order deviceCommand);
    Coordinate getPosition();
    Walkable getSpace();
}
