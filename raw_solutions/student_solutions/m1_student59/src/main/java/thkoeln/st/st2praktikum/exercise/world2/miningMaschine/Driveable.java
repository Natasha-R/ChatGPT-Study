package thkoeln.st.st2praktikum.exercise.world2.miningMaschine;

import thkoeln.st.st2praktikum.exercise.world2.Cloud;
import thkoeln.st.st2praktikum.exercise.world2.types.Direction;

public interface Driveable {
    boolean drive(Direction direction, Cloud world);
}
