package thkoeln.st.st2praktikum.exercise;

import thkoeln.st.st2praktikum.exercise.BitPaw.*;

import java.util.UUID;

public class TidyUpRobotService
{
    final World world = World.getInstance();
    final RobotManager robotManager = new RobotManager();

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width)
    {
        return getWorld().addRoom(height, width);
    }

    /**
     * This method adds a barrier to a given room.
     * @param roomId the ID of the room the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID roomId, Barrier barrier)
    {
        try
        {
            getWorld().addWall(roomId, barrier);
        }
        catch (RoomNotFound roomNotFound)
        {
            roomNotFound.printStackTrace();
        }
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
        try
        {
            return getWorld().addPortal(sourceRoomId, sourceVector2D, destinationRoomId, destinationVector2D);
        }
        catch (RoomNotFound exception)
        {
            exception.printStackTrace();
            return null;
        }
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name)
    {
        return getRobotManager().addRobot(name);
    }

    /**
     * This method lets the tidy-up robot execute a order.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on gridcells with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a barrier or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Order order)
    {
        boolean result = false;

        try
        {
            final Robot robot = getRobotManager().getRobot(tidyUpRobotId);

            result = robot.move(order);
        }
        catch (RobotNotFound | RoomNotFound exception)
        {
            exception.printStackTrace();
        }

        return result; // ??? "Movement commands are always successful" ???
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId)
    {
        try
        {
            return getWorld().getRoomUUIDFromRoboter(tidyUpRobotId);
        }
        catch (RoomNotFound roomNotFound)
        {
            roomNotFound.printStackTrace();
            return null;
        }
    }

    /**
     * This method returns the vector2Ds a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the vector2Ds of the tidy-up robot
     */
    public Vector2D getVector2D(UUID tidyUpRobotId)
    {
        try
        {
            return getRobotManager().getRobotPosition(tidyUpRobotId);
        }
        catch (RobotNotFound robotNotFound)
        {
            robotNotFound.printStackTrace();
            return null;
        }
    }

    private World getWorld()
    {
        return world;
    }

    private RobotManager getRobotManager()
    {
        return robotManager;
    }
}
