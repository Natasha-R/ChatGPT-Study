package thkoeln.st.st2praktikum.exercise.space;

import thkoeln.st.st2praktikum.exercise.ObstaclePassable;
import thkoeln.st.st2praktikum.exercise.cleaningDevice.Occupied;
import thkoeln.st.st2praktikum.exercise.connection.Connectable;
import thkoeln.st.st2praktikum.exercise.innerCore.Identifying;

import java.util.UUID;

public interface SpaceManageable extends Identifying {
    void addObstacle(ObstaclePassable obstacle);
    UUID addConnection(Connectable connection);
}
