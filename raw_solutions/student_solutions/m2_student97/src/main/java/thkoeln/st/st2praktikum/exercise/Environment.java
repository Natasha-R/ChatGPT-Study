package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class Environment {
    
    private HashMap<UUID, Robot> robots = new HashMap<>();
    private HashMap<UUID, Room> rooms = new HashMap<>();


    public UUID addRobot (String name) {
        Robot newRobot = new Robot(name);
        this.robots.put(newRobot.getId(), newRobot);
        return newRobot.getId();
    }


    public Robot getRobot (UUID robotId) {
        return robots.get(robotId);
    }


    public HashMap<UUID, Robot> getRobots () {
        return this.robots;
    }


    public UUID addRoom (Integer height, Integer width) {
        Room newRoom = new Room(height, width);
        this.rooms.put(newRoom.getId(), newRoom);
        return newRoom.getId();
    }


    public Room getRoom (UUID roomId) {
        return rooms.get(roomId);
    }


    public HashMap<UUID, Room> getRooms() {
        return this.rooms;
    }
}
