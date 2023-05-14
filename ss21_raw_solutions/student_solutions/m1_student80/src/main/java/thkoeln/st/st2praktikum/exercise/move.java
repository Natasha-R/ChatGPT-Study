package thkoeln.st.st2praktikum.exercise;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public interface move {
    public RoomRobotorHashMap moveNorth(String steps, TidyUpRoboterList tidyUpRobotList, RoomList roomList, RoomRobotorHashMap roomtidyUpRobotHashMap, UUID roomWhereRobotIs);

    public RoomRobotorHashMap moveSouth(String steps, TidyUpRoboterList tidyUpRobotList, RoomList roomList, RoomRobotorHashMap roomtidyUpRobotHashMap, UUID roomWhereRobotIs);

    public RoomRobotorHashMap moveEast(String steps, TidyUpRoboterList tidyUpRobotList, RoomList roomList, RoomRobotorHashMap roomtidyUpRobotHashMap, UUID roomWhereRobotIs);

    public RoomRobotorHashMap moveWest(String steps, TidyUpRoboterList tidyUpRobotList, RoomList roomList, RoomRobotorHashMap roomtidyUpRobotHashMap, UUID roomWhereRobotIs);

    public RoomRobotorHashMap moveConnection(UUID tidyRobotUUID, TidyUpRoboterList tidyUpRobotList, ConnectionList connectionList, RoomRobotorHashMap roomtidyUpRobotHashMap, UUID roomWhereRobotIs);


}
