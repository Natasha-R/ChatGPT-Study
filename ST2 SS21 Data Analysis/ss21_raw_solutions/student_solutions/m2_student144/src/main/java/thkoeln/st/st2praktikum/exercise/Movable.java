package thkoeln.st.st2praktikum.exercise;

import java.util.List;
import java.util.UUID;

public interface Movable {
    public void travel(UUID destinationRoomId, String destinationPosition);
    public void moveRobot(Command command, List<Wall> relevantWalls, Integer maxHeight, Integer maxWith);
}
