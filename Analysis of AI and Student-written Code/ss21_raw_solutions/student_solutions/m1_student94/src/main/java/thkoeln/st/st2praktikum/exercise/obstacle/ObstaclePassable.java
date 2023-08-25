package thkoeln.st.st2praktikum.exercise.obstacle;

import thkoeln.st.st2praktikum.exercise.inner.Position;

public interface ObstaclePassable {
    Position getUnaccessPosition(Position actualPosition, Position objectivePosition );
}
