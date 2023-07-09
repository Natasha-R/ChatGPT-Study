package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class WorldBuilder {
    private RoomFactory roomBuilder = new RoomFactory();
    private RobotFactory robotBuilder = new RobotFactory();
    private HashMap<UUID, TidyUpRobot> robotFinder = new HashMap<UUID, TidyUpRobot>();
    private HashMap<UUID, Room> roomFinder = new HashMap<UUID, Room>();

    public UUID createRoom(int width, int height) {
        Room room = roomBuilder.getRoom(height, width);
        roomFinder.put(room.getUID(), room);
        return room.getUID();
    }

    public UUID createRobot(String name) {
        TidyUpRobot robot = robotBuilder.getRobot(name);
        robotFinder.put(robot.getUID(), robot);
        return robot.getUID();
    }
    
    public HashMap<UUID, TidyUpRobot> getRobotFinder() {
        return robotFinder;
    }

    public HashMap<UUID, Room> getRoomFinder() {
        return roomFinder;
    }
    
    public UUID getIdFromString(String commandString) {
        return UUID.fromString(commandString.substring(commandString.indexOf(',')+1, commandString.indexOf(']')));
    }
}
