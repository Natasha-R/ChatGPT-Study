package thkoeln.st.st2praktikum.exercise.space;

import thkoeln.st.st2praktikum.exercise.connection.Connectable;
import thkoeln.st.st2praktikum.exercise.inner.Identifying;
import thkoeln.st.st2praktikum.exercise.obstacle.Obstacle;
import thkoeln.st.st2praktikum.exercise.obstacle.ObstaclePassable;

import java.util.UUID;

public interface SpaceManageable extends Identifying {
    void addObstacle(ObstaclePassable obstacle);
    UUID addConnection(Connectable connection);
}
