package thkoeln.st.st2praktikum.exercise.cleaningDevice;

import thkoeln.st.st2praktikum.exercise.inner.CleaningDeviceMovement;
import thkoeln.st.st2praktikum.exercise.inner.Identifying;
import thkoeln.st.st2praktikum.exercise.inner.Position;
import thkoeln.st.st2praktikum.exercise.space.Walkable;

public interface Instructable extends Identifying {
    Boolean analysedCommand (CleaningDeviceMovement deviceCommand);
    Position getPosition();
    Walkable getSpace();
}
