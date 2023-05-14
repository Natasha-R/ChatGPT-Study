package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.entities.Portal;
import thkoeln.st.st2praktikum.exercise.entities.Room;
import thkoeln.st.st2praktikum.exercise.entities.TransportSystem;
import thkoeln.st.st2praktikum.exercise.exception.*;
import thkoeln.st.st2praktikum.exercise.utility.*;
import thkoeln.st.st2praktikum.exercise.repositories.*;

import java.awt.*;
import java.util.UUID;

@Service
public class TidyUpRobotService
{
    @Autowired
    private TidyUpRobotRepository tidyUpRobotRepository;
    @Autowired
    private TransportSystemRepository transportSystemRepository;
    @Autowired
    private BarrierRepository barrierRepository;
    @Autowired
    private PortalRepository portalRepository;
    @Autowired
    private RoomRepository roomRepository;

    private final World world = World.getInstance();
    private final RobotManager robotManager = new RobotManager();

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width)
    {
        final UUID roomUUID = getWorld().addRoom(height, width);

        try
        {
            final Room room = getWorld().getRoom(roomUUID);

            roomRepository.save(room);
        }
        catch (RoomNotFound roomNotFound)
        {
            roomNotFound.printStackTrace();
        }

        return roomUUID;
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

            barrierRepository.save(barrier);
        }
        catch (RoomNotFound roomNotFound)
        {
            roomNotFound.printStackTrace();
        }
    }

    /**
     * This method adds a transport system
     * @param system the type of the transport system
     * @return the UUID of the created transport system
     */
    public UUID addTransportSystem(String system)
    {
        final UUID uuid = getWorld().addTransportSystem(system);

        final TransportSystem transportSystem = getWorld().getTransportSystem(uuid);

        transportSystemRepository.save(transportSystem);

        return uuid;
    }

    /**
     * This method adds a traversable connection between two rooms based on a transport system. Connections only work in one direction.
     * @param transportSystemId the transport system which is used by the connection
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourceVector2D the vector-2D of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationVector2D the vector-2D of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportSystemId, UUID sourceRoomId, Vector2D sourceVector2D, UUID destinationRoomId, Vector2D destinationVector2D)
    {
        try
        {
            final UUID uuid = getWorld().addPortal(transportSystemId, sourceRoomId, sourceVector2D, destinationRoomId, destinationVector2D);

            final Portal portal = getWorld().getPortal(uuid);

            portalRepository.save(portal);

            return uuid;
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
        final UUID uuid = getRobotManager().addRobot(name);
        final TidyUpRobot robot ;

        try
        {
            robot = getRobotManager().getRobot(uuid);
            tidyUpRobotRepository.save(robot);
        }
        catch (RobotNotFound robotNotFound)
        {
            robotNotFound.printStackTrace();
        }

        return uuid;
    }


    /**
     * This method lets the tidy-up robot execute a order.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on grid cells with a connection to another room
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
            final TidyUpRobot robot = getRobotManager().getRobot(tidyUpRobotId);

            result = robot.move(order);

            tidyUpRobotRepository.save(robot);
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
     * This method returns the vector-2D a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the vector-2D of the tidy-up robot
     */
    public Vector2D getTidyUpRobotVector2D(UUID tidyUpRobotId)
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
