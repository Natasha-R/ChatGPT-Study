package thkoeln.st.st2praktikum.exercise.room;

import thkoeln.st.st2praktikum.exercise.core.Identifiable;
import thkoeln.st.st2praktikum.exercise.barrier.Blockable;
import thkoeln.st.st2praktikum.exercise.connection.Connectable;

import java.util.UUID;

public interface Buildable extends Identifiable {
    void addBarrier(Blockable barrier);
    UUID addConnection(Connectable connection);
}
