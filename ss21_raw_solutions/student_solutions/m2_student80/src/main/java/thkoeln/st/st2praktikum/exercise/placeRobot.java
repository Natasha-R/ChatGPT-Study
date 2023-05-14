package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface placeRobot {

    public RoomRobotorHashMap placeRobot(UUID roomUUID, TidyUpRoboterList tidyUpRoboterList, RoomRobotorHashMap roomRobotorHashMap, UUID tidyRobot, CoordinatesForRobots coordinatesForRobots);
}
