package thkoeln.st.st2praktikum.exercise.tidyuprobot.domain;

import thkoeln.st.st2praktikum.exercise.domainprimitives.Coordinate;
import thkoeln.st.st2praktikum.exercise.room.domain.RoomRepository;
import thkoeln.st.st2praktikum.exercise.transportcategory.domain.TransportCategoryRepository;


import java.util.UUID;

public interface move {
    public Coordinate moveNorth(Integer steps, TidyUpRoboterList tidyUpRobotList, TidyUpRobotRepository tidyUpRobotRepository, UUID roomWhereRobotIs, RoomRepository roomRepository);

    public Coordinate moveSouth(Integer steps, TidyUpRoboterList tidyUpRobotList,  TidyUpRobotRepository tidyUpRobotRepository, UUID roomWhereRobotIs, RoomRepository roomRepository);

    public Coordinate moveEast(Integer steps, TidyUpRoboterList tidyUpRobotList, TidyUpRobotRepository tidyUpRobotRepository, UUID roomWhereRobotIs, RoomRepository roomRepository);

    public Coordinate moveWest(Integer steps, TidyUpRoboterList tidyUpRobotList, TidyUpRobotRepository tidyUpRobotRepository, UUID roomWhereRobotIs, RoomRepository roomRepository);

    public Coordinate moveConnection(UUID tidyRobotUUID, TidyUpRoboterList tidyUpRobotList, TidyUpRobotRepository tidyUpRobotRepository, UUID roomWhereRobotIs, TransportCategoryRepository transportCategoryRepository);


}
