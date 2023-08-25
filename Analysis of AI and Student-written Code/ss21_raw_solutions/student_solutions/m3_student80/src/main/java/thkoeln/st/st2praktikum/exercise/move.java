package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public interface move {
    public Coordinate moveNorth(Integer steps, TidyUpRoboterList tidyUpRobotList, RoomList roomList, TidyUpRobotRepository tidyUpRobotRepository, UUID roomWhereRobotIs);

    public Coordinate moveSouth(Integer steps, TidyUpRoboterList tidyUpRobotList, RoomList roomList, TidyUpRobotRepository tidyUpRobotRepository, UUID roomWhereRobotIs);

    public Coordinate moveEast(Integer steps, TidyUpRoboterList tidyUpRobotList, RoomList roomList, TidyUpRobotRepository tidyUpRobotRepository, UUID roomWhereRobotIs);

    public Coordinate moveWest(Integer steps, TidyUpRoboterList tidyUpRobotList, RoomList roomList, TidyUpRobotRepository tidyUpRobotRepository, UUID roomWhereRobotIs);

    public Coordinate moveConnection(UUID tidyRobotUUID, TidyUpRoboterList tidyUpRobotList, ConnectionList connectionList, TidyUpRobotRepository tidyUpRobotRepository, UUID roomWhereRobotIs);


}
