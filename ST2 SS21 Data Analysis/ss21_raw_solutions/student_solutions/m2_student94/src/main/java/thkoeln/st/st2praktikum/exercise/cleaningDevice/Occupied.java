package thkoeln.st.st2praktikum.exercise.cleaningDevice;

import thkoeln.st.st2praktikum.exercise.Coordinate;
import thkoeln.st.st2praktikum.exercise.innerCore.Identifying;
import thkoeln.st.st2praktikum.exercise.ObstaclePassable;

public interface Occupied extends Identifying, ObstaclePassable {
    Boolean occupiedSpace(Coordinate coordinate);
}
