package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class TidyUpRobotService {

    @Autowired
    private RobotRepository robotRepository;
    @Autowired
    private RoomRepository roomRepository;

    private HashMap<UUID, Room> rooms;
    private HashMap<UUID, Connection> connections;
    private HashMap<UUID, TidyUpRobot> robots;
    private HashMap<UUID, TransportCategory> transportCategories;

    public TidyUpRobotService()
    {
        rooms = new HashMap<UUID, Room>();
        connections = new HashMap<UUID, Connection>();
        robots = new HashMap<UUID, TidyUpRobot>();
        transportCategories = new HashMap<UUID, TransportCategory>();
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
        Room room = new Room(uuid, width, height);
        rooms.put(uuid, room);
        roomRepository.save(room);
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
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category)
    {
        if(category == null)
        {
            throw new IllegalArgumentException();
        }

        UUID uuid = UUID.randomUUID();

        transportCategories.put(uuid, new TransportCategory(category));

        return uuid;
    }


    /**
     * This method adds a traversable connection between two rooms based on a transport category. Connections only work in one direction.
     * @param transportCategoryId the transport category which is used by the connection
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourceVector2D the vector-2D of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationVector2D the vector-2D of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportCategoryId, UUID sourceRoomId, Vector2D sourceVector2D, UUID destinationRoomId, Vector2D destinationVector2D)
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

        connections.put(uuid, new Connection(transportCategories.get(transportCategoryId), rooms.get(sourceRoomId), sourceVector2D, rooms.get(destinationRoomId), destinationVector2D));
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
        TidyUpRobot tidyUpRobot = new TidyUpRobot(uuid, name);
        robots.put(uuid, tidyUpRobot);
        robotRepository.save(tidyUpRobot);
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

        boolean terminated = robots.get(tidyUpRobotId).executeCommand(task, rooms, connections);
        robotRepository.deleteById(tidyUpRobotId);
        robotRepository.save(robots.get(tidyUpRobotId));
        return terminated;
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

        return robots.get(tidyUpRobotId).getRoomId();
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
