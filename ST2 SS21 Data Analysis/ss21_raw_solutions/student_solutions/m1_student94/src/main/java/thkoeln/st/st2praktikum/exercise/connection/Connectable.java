package thkoeln.st.st2praktikum.exercise.connection;

import thkoeln.st.st2praktikum.exercise.inner.Identifying;
import thkoeln.st.st2praktikum.exercise.inner.Position;

public interface Connectable extends Identifying {
    Position getSourcePosition();
    Position getDestinationPosition();

}
