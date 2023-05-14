package thkoeln.st.st2praktikum.exercise.interfaces;

import java.util.UUID;

public interface Buildable extends Identifiable {
    void addObstacle(Blocking barrier);
    UUID addConnection(Connectable connection);
}
