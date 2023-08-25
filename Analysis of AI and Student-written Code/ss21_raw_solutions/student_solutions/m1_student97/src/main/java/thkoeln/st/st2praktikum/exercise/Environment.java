package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class Environment {
    
    protected HashMap<UUID, Robot> robots = new HashMap<UUID, Robot>();
    protected HashMap<UUID, Room> rooms = new HashMap<UUID, Room>();


    public UUID addRobot (String name) {
        Robot newRobot = new Robot(name);
        this.robots.put(newRobot.id, newRobot);
        return newRobot.id;
    }


    public UUID addRoom (Integer height, Integer width) {
        Room newRoom = new Room(height, width);
        this.rooms.put(newRoom.id, newRoom);
        return newRoom.id;
    }


    public Robot getRobot (UUID robotId) {
        return robots.get(robotId);
    }


    public Room getRoom (UUID roomId) {
        return rooms.get(roomId);
    }
}
