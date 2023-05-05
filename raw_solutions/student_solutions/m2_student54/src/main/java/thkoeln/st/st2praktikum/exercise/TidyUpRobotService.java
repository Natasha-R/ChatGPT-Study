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
     * @param wall the end points of the wall
     */
    public void addWall(UUID roomId, Wall wall)
    {
        if(roomId == null || wall == null || rooms.get(roomId) == null)
        {
            throw new IllegalArgumentException();
        }

        rooms.get(roomId).addWall(wall);
    }

    private boolean isVectorInBounds(UUID roomId, Vector2D vector2D)
    {
        if(vector2D.getX() >= 0 && vector2D.getX() < rooms.get(roomId).getWidth()
            && vector2D.getY() >= 0 && vector2D.getY() < rooms.get(roomId).getHeight())
        {
            return true;
        }

        return false;
    }

    /**
     * This method adds a traversable connection between two rooms. Connections only work in one direction.
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourceVector2D the vector2Ds of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationVector2D the vector2Ds of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID sourceRoomId, Vector2D sourceVector2D, UUID destinationRoomId, Vector2D destinationVector2D)
    {
        if(sourceRoomId == null || sourceVector2D == null || destinationRoomId == null || destinationVector2D == null
                || rooms.get(sourceRoomId) == null || rooms.get(destinationRoomId) == null)
        {
            throw new IllegalArgumentException("One or more arguments are null!");
        }

        if(!(isVectorInBounds(sourceRoomId, sourceVector2D) && isVectorInBounds(destinationRoomId, destinationVector2D)))
        {
            throw new IllegalArgumentException("One or more vectors are out of bounds!");
        }

        UUID uuid = UUID.randomUUID();

        connections.put(uuid, new Connection(sourceRoomId, sourceVector2D, destinationRoomId, destinationVector2D));
        rooms.get(sourceRoomId).setGridCellToConnection(sourceVector2D.getX(), sourceVector2D.getY(), uuid);

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
    public Boolean executeCommand(UUID tidyUpRobotId, Task task)
    {
        if(tidyUpRobotId == null || task == null || robots.get(tidyUpRobotId) == null)
        {
            throw new IllegalArgumentException();
        }

        return robots.get(tidyUpRobotId).executeCommand(task, rooms, connections);
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
     * This method returns the vector2Ds a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the vector2Ds of the tidy-up robot
     */
    public Vector2D getVector2D(UUID tidyUpRobotId)
    {
        if(tidyUpRobotId == null || robots.get(tidyUpRobotId) == null)
        {
            throw new IllegalArgumentException();
        }

        return robots.get(tidyUpRobotId).getPosition();
    }
}
