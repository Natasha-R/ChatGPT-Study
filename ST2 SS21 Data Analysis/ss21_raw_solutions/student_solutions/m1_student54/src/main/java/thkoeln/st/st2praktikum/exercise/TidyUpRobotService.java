package thkoeln.st.st2praktikum.exercise;

import java.util.HashMap;
import java.util.UUID;

public class TidyUpRobotService {


    private HashMap<UUID, Room> rooms;
    private HashMap<UUID, Connection> connections;
    private HashMap<UUID, Robot> robots;

    public TidyUpRobotService()
    {
        rooms = new HashMap<UUID, Room>();
        connections = new HashMap<UUID, Connection>();
        robots = new HashMap<UUID, Robot>();
    }

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width)
    {
        if(height <= 0 || width <= 0)
        {
            throw new IllegalArgumentException();
        }

        UUID uuid = UUID.randomUUID();
        rooms.put(uuid, new Room(width, height));

        return uuid;
    }

    /**
     * This method adds a wall to a given room.
     * @param roomId the ID of the room the wall shall be placed on
     * @param wallString the end points of the wall, encoded as a String, eg. "(2,3)-(10,3)" for a wall that spans from (2,3) to (10,3)
     */
    public void addWall(UUID roomId, String wallString)
    {
        if(roomId == null || wallString == null || rooms.get(roomId) == null)
        {
            throw new IllegalArgumentException();
        }

        rooms.get(roomId).addWall(wallString);
    }

    private Position extractPositionFromString(String coordinateString)
    {
        int x = Integer.parseInt(coordinateString.substring(1, coordinateString.indexOf(',')));
        int y = Integer.parseInt(coordinateString.substring(coordinateString.indexOf(',') + 1, coordinateString.length() - 1));

        return new Position(x, y);
    }

    /**
     * This method adds a traversable connection between two rooms. Connections only work in one direction.
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceRoomId, String sourceCoordinate, UUID destinationRoomId, String destinationCoordinate)
    {
        if(sourceRoomId == null || sourceCoordinate == null || destinationRoomId == null || destinationCoordinate == null
            || rooms.get(sourceRoomId) == null || rooms.get(destinationRoomId) == null)
        {
            throw new IllegalArgumentException();
        }

        UUID uuid = UUID.randomUUID();
        Position sourcePosition = extractPositionFromString(sourceCoordinate);
        Position destinationPosition = extractPositionFromString(destinationCoordinate);

        connections.put(uuid, new Connection(sourceRoomId, sourcePosition, destinationRoomId, destinationPosition));
        rooms.get(sourceRoomId).setGridCellToConnection(sourcePosition.getX(), sourcePosition.getY(), uuid);

        return uuid;
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name)
    {
        if(name == null)
        {
            throw new IllegalArgumentException();
        }

        UUID uuid = UUID.randomUUID();
        robots.put(uuid, new Robot(name));

        return uuid;
    }

    /**
     * This method lets the tidy-up robot execute a command.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param commandString the given command, encoded as a String:
     * "[direction, steps]" for movement, e.g. "[we,2]"
     * "[tr,room-id]" for transport, e.g. "[tr,4de4d85c-612c-4fa0-8e0f-7212f0711b40]" - only works on tiles with a connection to another room
     * "[en,<room-id>]" for setting the initial room where a tidy-up robot is placed, e.g. "[en,4de4d85c-612c-4fa0-8e0f-7212f0711b40]". The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a wall or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, String commandString)
    {
        if(tidyUpRobotId == null || commandString == null || robots.get(tidyUpRobotId) == null)
        {
            throw new IllegalArgumentException();
        }

        return robots.get(tidyUpRobotId).executeCommand(commandString, rooms, connections);
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId)
    {
        if(tidyUpRobotId == null || robots.get(tidyUpRobotId) == null)
        {
            throw new IllegalArgumentException();
        }
        return robots.get(tidyUpRobotId).getCurrentRoom();
    }

    /**
     * This method returns the coordinates a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordiantes of the tidy-up robot encoded as a String, e.g. "(2,4)"
     */
    public String getCoordinates(UUID tidyUpRobotId)
    {
        if(tidyUpRobotId == null || robots.get(tidyUpRobotId) == null)
        {
            throw new IllegalArgumentException();
        }
        return "(" + robots.get(tidyUpRobotId).getPosition().getX() + "," + robots.get(tidyUpRobotId).getPosition().getY() + ")";
    }
}
