package thkoeln.st.st2praktikum.exercise;

import java.util.UUID;

public interface placeRobot {

    public Coordinate placeRobot(UUID roomUUID, TidyUpRoboterList tidyUpRoboterList, TidyUpRobotRepository tidyUpRobotRepository, UUID tidyRobot, CoordinatesForRobots coordinatesForRobots);
}
