package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.domainprimitives.CoordinatesForRobots;
import thkoeln.st.st2praktikum.exercise.domainprimitives.TaskType;
import thkoeln.st.st2praktikum.exercise.room.domain.RoomRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;

import java.util.UUID;

public interface executeCommand {
    public Coordinate executeCommand(TaskType moveCommand, Integer steps, UUID UUID, TidyUpRoboterList tidyUpRobotList, TidyUpRobotRepository tidyUpRobotRepository, UUID roomWhereRobotIs, UUID tidyRobot, CoordinatesForRobots coordinatesForRobots, RoomRepository roomRepository, TransportCategoryRepository transportCategoryRepository);
}
