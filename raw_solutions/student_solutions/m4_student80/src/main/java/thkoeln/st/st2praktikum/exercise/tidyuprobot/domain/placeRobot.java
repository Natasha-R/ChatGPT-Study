package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.CoordinatesForRobots;

import java.util.UUID;

public interface placeRobot {

    public Coordinate placeRobot(UUID roomUUID, TidyUpRoboterList tidyUpRoboterList, TidyUpRobotRepository tidyUpRobotRepository, UUID tidyRobot, CoordinatesForRobots coordinatesForRobots);
}
