package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TidyUpRobotService {

    private RoomRepository roomRepository;
    private CommandRepository commandRepository;
    private TidyUpRobotRepository tidyUpRobotRepository;
    private TraversableConnectionRepository traversableConnectionRepository;
    private WallRepository wallRepository;

    @Autowired
    public TidyUpRobotService(RoomRepository roomRepository,
                              CommandRepository commandRepository,
                              TidyUpRobotRepository tidyUpRobotRepository,
                              TraversableConnectionRepository traversableConnectionRepository,
                              WallRepository wallRepository)
    {
        this.roomRepository = roomRepository;
        this.commandRepository = commandRepository;
        this.tidyUpRobotRepository = tidyUpRobotRepository;
        this.traversableConnectionRepository = traversableConnectionRepository;
        this. wallRepository = wallRepository;
    }
    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        Room room = new Room(height,width);
        roomRepository.save(room);
        RoomMap.addToMap(room,room.getRoomId());
        return room.getRoomId();
    }

    /**
     * This method adds a wall to a given room.
     * @param roomId the ID of the room the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID roomId, Wall wall) {
        wall.placeWallInRoom(roomId);
        wallRepository.save(wall);
        WallMap.addToMap(wall, wall.getWallId());
    }

    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        TransportTechnology transportTechnology = new TransportTechnology(technology);

        return transportTechnology.getTransportTechnologyId();
    }

    /**
     * This method adds a traversable connection between two rooms based on a transport technology. Connections only work in one direction.
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourcePoint the point of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationPoint the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId, UUID sourceRoomId, Point sourcePoint, UUID destinationRoomId, Point destinationPoint) {
        TraversableConnection traversableConnection = new TraversableConnection(transportTechnologyId,sourceRoomId,sourcePoint,destinationRoomId,destinationPoint);
        TraversableConnectionMap.addToMap(traversableConnection, traversableConnection.getSourceRoomId() );
        traversableConnectionRepository.save(traversableConnection);
        return traversableConnection.getConnectionId();
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot tidyUpRobot = new TidyUpRobot(name);
        TidyUpRobotMap.addToMap(tidyUpRobot,tidyUpRobot.getTidyUpRobotId());
        tidyUpRobotRepository.save(tidyUpRobot);
        return tidyUpRobot.getTidyUpRobotId();

    }

    /**
     * This method lets the tidy-up robot execute a command.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param command the given command
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on grid cells with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a wall or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Command command) {
        Boolean worked = TidyUpRobotMap.getByUUID(tidyUpRobotId).understandCommandString(command);
        tidyUpRobotRepository.save(TidyUpRobotMap.getByUUID(tidyUpRobotId));

        return worked;
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return TidyUpRobotMap.getByUUID(tidyUpRobotId).getRoomId();
    }

    /**
     * This method returns the point a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the point of the tidy-up robot
     */
    public Point getTidyUpRobotPoint(UUID tidyUpRobotId){
        return TidyUpRobotMap.getByUUID(tidyUpRobotId).getPoint();
    }
}
