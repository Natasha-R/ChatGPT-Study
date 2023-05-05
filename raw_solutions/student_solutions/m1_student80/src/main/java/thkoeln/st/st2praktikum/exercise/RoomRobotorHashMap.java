package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class RoomRobotorHashMap {
    private HashMap<UUID, UUID> roomtidyUpRobotHashMap = new HashMap<UUID, UUID>();

    public RoomRobotorHashMap() {
        this.roomtidyUpRobotHashMap = roomtidyUpRobotHashMap;
    }


    public void add(UUID UUIDTidyUpRobot, UUID UUIDRoom) {
        roomtidyUpRobotHashMap.put(UUIDTidyUpRobot, UUIDRoom);
    }

    public HashMap<UUID, UUID> getRoomtidyUpRobotHashMap() {
        return roomtidyUpRobotHashMap;
    }
}
