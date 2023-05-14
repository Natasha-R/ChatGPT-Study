package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public interface RobotActions {
    public boolean move(Space currentSpace, CommandType direction, int fields);
    public boolean transport(Space currentSpace, Space destinationSpace, HashMap<UUID, Connection> connectionHashMap);
    public boolean enterSpace(Space destinationSpace);
}
