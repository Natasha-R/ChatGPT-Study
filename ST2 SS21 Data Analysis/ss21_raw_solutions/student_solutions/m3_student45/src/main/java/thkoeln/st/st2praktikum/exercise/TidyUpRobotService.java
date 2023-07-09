package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.Repository.*;

import java.util.HashMap;
import java.util.UUID;


@Service
public class TidyUpRobotService {

    private final HashMap<UUID, TidyUpRobot> tidyUpRobots;
    private final HashMap<UUID, Room> rooms;
    private final HashMap<UUID, TransportTechnology> transportTechnologies;

    private final ConnectionRepository connectionRepository;
    private final RoomRepository roomRepository;
    private final TidyUpRobotRepository tidyUpRobotRepository;
    private final TransportTechnologyRepository transportTechnologyRepository;

    @Autowired
    public TidyUpRobotService(ConnectionRepository connectionRepository, RoomRepository roomRepository, TidyUpRobotRepository tidyUpRobotRepository, TransportTechnologyRepository transportTechnologyRepository){
        tidyUpRobots = new HashMap<>();
        rooms = new HashMap<>();
        transportTechnologies = new HashMap<>();

        this.connectionRepository = connectionRepository;
        this.roomRepository = roomRepository;
        this.tidyUpRobotRepository = tidyUpRobotRepository;
        this.transportTechnologyRepository = transportTechnologyRepository;
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
        roomRepository.save(room);

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
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        TransportTechnology transportTechnology = new TransportTechnology(technology);
        UUID id = transportTechnology.getId();

        transportTechnologies.put(id, transportTechnology);
        transportTechnologyRepository.save(transportTechnology);

        return transportTechnology.getId();
    }

    /**
     * This method adds a traversable connection between two rooms based on a transport technology. Connections only work in one direction.
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourceCoordinate the coordinate of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationCoordinate the coordinate of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId, UUID sourceRoomId, Coordinate sourceCoordinate, UUID destinationRoomId, Coordinate destinationCoordinate) {
        TransportTechnology transportTechnology = transportTechnologies.get(transportTechnologyId);
        Room sourceRoom = rooms.get(sourceRoomId);
        Room destinationRoom =  rooms.get(destinationRoomId);

        Connection connection = transportTechnology.addConnection(sourceRoom, sourceCoordinate, destinationRoom, destinationCoordinate);
        connectionRepository.save(connection);

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
        tidyUpRobotRepository.save(tidyUpRobot);

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

        Boolean status = tidyUpRobot.execute(order);
        tidyUpRobotRepository.save(tidyUpRobot);

        return status;
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        TidyUpRobot tidyUpRobot = tidyUpRobots.get(tidyUpRobotId);
        return tidyUpRobot.getRoomId();
    }

    /**
     * This method returns the coordinate a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordinate of the tidy-up robot
     */
    public Coordinate getTidyUpRobotCoordinate(UUID tidyUpRobotId){
        TidyUpRobot tidyUpRobot = tidyUpRobots.get(tidyUpRobotId);
        return tidyUpRobot.getCoordinate();
    }
}
