package thkoeln.st.st2praktikum.exercise.room.domain;

import thkoeln.st.st2praktikum.exercise.Blocking;
import thkoeln.st.st2praktikum.exercise.Identifiable;

public interface Buildable extends Identifiable {
    void addObstacle(Blocking barrier);
}
