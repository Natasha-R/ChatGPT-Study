package thkoeln.st.st2praktikum.exercise.room;

import thkoeln.st.st2praktikum.exercise.obstacle.Blocking;
import thkoeln.st.st2praktikum.exercise.connection.Connectable;
import thkoeln.st.st2praktikum.exercise.core.Identifiable;

import java.util.UUID;

public interface Buildable extends Identifiable {
    void addObstacle(Blocking barrier);
    UUID addConnection(Connectable connection);
}
