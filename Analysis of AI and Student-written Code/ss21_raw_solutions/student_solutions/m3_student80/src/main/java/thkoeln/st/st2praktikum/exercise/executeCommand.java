package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public interface executeCommand {
    public Coordinate executeCommand(TaskType moveCommand, Integer steps, UUID UUID, TidyUpRoboterList tidyUpRobotList, RoomList roomList, ConnectionList connectionList, TidyUpRobotRepository tidyUpRobotRepository, UUID roomWhereRobotIs, UUID tidyRobot,CoordinatesForRobots coordinatesForRobots);
}
