package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public interface RobotActions {
    public boolean move(String direction, Integer fields);
    public boolean transport(Space destinationSpace, HashMap<UUID, Connection> connectionHashMap);
    public boolean enterSpace(Space destinationSpace);
}
