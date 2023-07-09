package thkoeln.st.st2praktikum.exercise.cleaningDevice;

import thkoeln.st.st2praktikum.exercise.inner.Identifying;
import thkoeln.st.st2praktikum.exercise.inner.Position;
import thkoeln.st.st2praktikum.exercise.obstacle.ObstaclePassable;

public interface Occupied extends Identifying, ObstaclePassable {
    Boolean occupiedSpace(Position position);
}
