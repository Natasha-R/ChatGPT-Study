package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class TidyUpRobotService
{
    @Autowired
    private TidyUpRobotRepository robotRepository;

    @Autowired
    private RoomRepository roomRepository;

    public HashMap<UUID, Room> rooms = new HashMap<>();
    public HashMap<UUID, TidyUpRobot> robots = new HashMap<>();
    public HashMap<UUID, Connection> connections = new HashMap<>();
    public HashMap<UUID, TransportTechnology> transportTechnologies = new HashMap<>();

    public UUID addTransportTechnology(String name)
    {
        UUID transportTechnologyId = UUID.randomUUID();
        TransportTechnology transportTechnology = new TransportTechnology(name);
        transportTechnologies.put(transportTechnologyId, transportTechnology);
        return transportTechnologyId;
    }

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width)
    {
        Room room = new Room(width, height);
        rooms.put(room.getRoomId(), room);
        roomRepository.save(room);
        return room.getRoomId();
    }

    /**
     * This method adds a barrier to a given room.
     * @param roomId the ID of the room the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID roomId, Barrier barrier)
    {
        rooms.get(roomId).addBarrier(barrier);
    }

    /**
     * This method adds a traversable connection between two rooms. Connections only work in one direction.
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourceCoordinate the coordinates of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationCoordinate the coordinates of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId, UUID sourceRoomId, Coordinate sourceCoordinate, UUID destinationRoomId, Coordinate destinationCoordinate)
    {
        Connection connection = new Connection(transportTechnologyId, sourceRoomId, sourceCoordinate, destinationRoomId, destinationCoordinate);
        connections.put(connection.connectionId, connection);
        rooms.get(sourceRoomId).setupConnectionStartpoint(connection);
        return connection.connectionId;
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name)
    {
        TidyUpRobot robot = new TidyUpRobot(name);
        robots.put(robot.getRobotId(), robot);
        robotRepository.save(robot);
        return robot.getRobotId();
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
        boolean executed = robots.get(tidyUpRobotId).executeCommand(order, rooms, connections);
        //robotRepository.deleteById(tidyUpRobotId);
        robotRepository.save(robots.get(tidyUpRobotId));
        return executed;
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId)
    {
        return robots.get(tidyUpRobotId).getCurrentRoom();
    }

    /**
     * This method returns the coordinates a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordinates of the tidy-up robot
     */
    public Coordinate getCoordinate(UUID tidyUpRobotId)
    {
        return robots.get(tidyUpRobotId).getCoordinate();
    }
}
