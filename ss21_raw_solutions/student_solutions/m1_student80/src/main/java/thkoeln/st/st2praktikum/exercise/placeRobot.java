package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface placeRobot {

    public RoomRobotorHashMap placeRobot(String roomUUID, TidyUpRoboterList tidyUpRoboterList, RoomRobotorHashMap roomRobotorHashMap, UUID tidyRobot, CoordinatesForRobots coordinatesForRobots);
}
