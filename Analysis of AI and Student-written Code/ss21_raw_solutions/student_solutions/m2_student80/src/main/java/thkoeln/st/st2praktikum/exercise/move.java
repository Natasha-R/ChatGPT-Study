package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public interface move {
    public RoomRobotorHashMap moveNorth(Integer steps, TidyUpRoboterList tidyUpRobotList, RoomList roomList, RoomRobotorHashMap roomtidyUpRobotHashMap, UUID roomWhereRobotIs);

    public RoomRobotorHashMap moveSouth(Integer steps, TidyUpRoboterList tidyUpRobotList, RoomList roomList, RoomRobotorHashMap roomtidyUpRobotHashMap, UUID roomWhereRobotIs);

    public RoomRobotorHashMap moveEast(Integer steps, TidyUpRoboterList tidyUpRobotList, RoomList roomList, RoomRobotorHashMap roomtidyUpRobotHashMap, UUID roomWhereRobotIs);

    public RoomRobotorHashMap moveWest(Integer steps, TidyUpRoboterList tidyUpRobotList, RoomList roomList, RoomRobotorHashMap roomtidyUpRobotHashMap, UUID roomWhereRobotIs);

    public RoomRobotorHashMap moveConnection(UUID tidyRobotUUID, TidyUpRoboterList tidyUpRobotList, ConnectionList connectionList, RoomRobotorHashMap roomtidyUpRobotHashMap, UUID roomWhereRobotIs);


}
