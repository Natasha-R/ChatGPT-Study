package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.entities.*;
import thkoeln.st.st2praktikum.exceptions.OutOfBoundsException;
import thkoeln.st.st2praktikum.exceptions.TaskException;

import java.util.HashMap;
import java.util.UUID;

public class TidyUpRobotService {

    private HashMap<UUID, Room> rooms = new HashMap<>();
    private HashMap<UUID, TidyUpRobot> tidyUpRobots = new HashMap<>();
    private HashMap<UUID, Connection> connections = new HashMap<>();

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        Room newRoom = new Room(height, width);
        rooms.put(newRoom.getUuid(), newRoom);
        return newRoom.getUuid();
    }

    /**
     * This method adds a wall to a given room.
     * @param roomId the ID of the room the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID roomId, Wall wall) {
        Room room = rooms.get(roomId);
        if(room.isInsideLimit(wall.getStart()) && room.isInsideLimit(wall.getEnd())) {
            room.addWall(wall);
        }else{
            throw new OutOfBoundsException("Wall is placed out of bounds.");
        }
    }

    /**
     * This method adds a traversable connection between two rooms. Connections only work in one direction.
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourcePoint the points of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationPoint the points of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceRoomId, Point sourcePoint, UUID destinationRoomId, Point destinationPoint) {
        Room sourceRoom = rooms.get(sourceRoomId);
        Room destinationRoom = rooms.get(destinationRoomId);
        if(sourceRoom.isInsideLimit(sourcePoint) && destinationRoom.isInsideLimit(destinationPoint)) {
            Connection newConnection = new Connection(sourceRoom, sourcePoint, destinationRoom, destinationPoint);
            sourceRoom.getUseAbles().add(newConnection);
            connections.put(newConnection.getUuid(), newConnection);
            return newConnection.getUuid();
        }else{
            throw new OutOfBoundsException("Connection is placed out of bounds.");
        }
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot newRobot = new TidyUpRobot(name);
        tidyUpRobots.put(newRobot.getUuid(), newRobot);
        return newRobot.getUuid();
    }

    /**
     * This method lets the tidy-up robot execute a task.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on tiles with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a wall or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Task task) {
        TidyUpRobot tidyUpRobot = tidyUpRobots.get(tidyUpRobotId);
        switch (task.getTaskType()){
            case NORTH:
            case SOUTH:
            case WEST:
            case EAST:
                tidyUpRobot.go(task);
                return true;
            case ENTER:
                Room initialRoom = rooms.get(task.getGridId());
                if(initialRoom == null)
                    throw new TaskException("Doesnt know a room with UUID: " + task.getGridId());
                return tidyUpRobot.init(initialRoom);
            case TRANSPORT:
                Room destinationRoom = rooms.get(task.getGridId());
                return tidyUpRobot.transport(destinationRoom);
            default:
                throw new TaskException("Cant get in here.");
        }
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return tidyUpRobots.get(tidyUpRobotId).getRoom().getUuid();
    }

    /**
     * This method returns the points a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the points of the tidy-up robot
     */
    public Point getPoint(UUID tidyUpRobotId){
        return tidyUpRobots.get(tidyUpRobotId).getPosition();
    }
}
