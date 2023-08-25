package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class TidyUpRobotService {

    private final HashMap<UUID, TidyUpRobot> tidyUpRobots;
    private final HashMap<UUID, Room> rooms;

    public TidyUpRobotService(){
        tidyUpRobots = new HashMap<>();
        rooms = new HashMap<>();
    }

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        Room room = new Room(height, width);
        UUID id = room.getId();

        rooms.put(id, room);

        return id;
    }

    /**
     * This method adds a wall to a given room.
     * @param roomId the ID of the room the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID roomId, Wall wall) {
        Room room = rooms.get(roomId);
        room.addWall(wall);
    }

    /**
     * This method adds a traversable connection between two rooms. Connections only work in one direction.
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceRoomId, Coordinate sourceCoordinate, UUID destinationRoomId, Coordinate destinationCoordinate) {
        Connection connection = new Connection(sourceRoomId, sourceCoordinate, destinationRoomId, destinationCoordinate);
        Room sourceRoom = rooms.get(sourceRoomId);
        Room destinationRoom = rooms.get(destinationRoomId);

        sourceRoom.addConnection(connection);
        destinationRoom.addConnection(connection);

        return connection.getId();
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot tidyUpRobot = new TidyUpRobot(name, rooms);
        UUID id = tidyUpRobot.getId();

        tidyUpRobots.put(id, tidyUpRobot);

        return id;
    }

    /**
     * This method lets the tidy-up robot execute a order.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on squares with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a wall or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Order order) {
        TidyUpRobot tidyUpRobot = tidyUpRobots.get(tidyUpRobotId);
        return tidyUpRobot.execute(order);
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        TidyUpRobot tidyUpRobot = tidyUpRobots.get(tidyUpRobotId);
        return tidyUpRobot.getTidyUpRobotRoomId();
    }

    /**
     * This method returns the coordinates a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordinates of the tidy-up robot
     */
    public Coordinate getCoordinate(UUID tidyUpRobotId){
        TidyUpRobot tidyUpRobot = tidyUpRobots.get(tidyUpRobotId);
        return tidyUpRobot.getCoordinates();
    }
}
