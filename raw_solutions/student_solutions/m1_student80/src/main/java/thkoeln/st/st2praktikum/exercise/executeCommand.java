package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public interface executeCommand {
    public RoomRobotorHashMap executeCommand(String moveCommand, String steps, String UUID, TidyUpRoboterList tidyUpRobotList, RoomList roomList, ConnectionList connectionList, RoomRobotorHashMap roomRobotorHashMap, UUID roomWhereRobotIs, UUID tidyRobot,CoordinatesForRobots coordinatesForRobots);
}
