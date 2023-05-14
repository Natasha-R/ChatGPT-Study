package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class TidyUpRobotService
{

    private TidyUpRobotRepository tidyUpRobotRepository;

    private RoomRepository roomRepository;

    private TransportTechnologyRepository technologyRepository;

    private ConnectionRepository connectionRepository;

    @Autowired
    public TidyUpRobotService(TidyUpRobotRepository tidyUpRobotRepository, RoomRepository roomRepository,
                              TransportTechnologyRepository technologyRepository,ConnectionRepository connectionRepository)
    {
        this.roomRepository = roomRepository;
        this.tidyUpRobotRepository = tidyUpRobotRepository;
        this.technologyRepository = technologyRepository;
        this.connectionRepository = connectionRepository;
    }

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width)
    {
       Room room = new Room(width,height);
       this.roomRepository.save(room);
       return room.identify();
    }

    /**
     * This method adds a wall to a given room.
     * @param roomId the ID of the room the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID roomId, Wall wall)
    {
        this.roomRepository.findById(roomId).get().addWall(wall);
    }

    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology)
    {
        TransportTechnology transportTechnology = new TransportTechnology(technology);
        this.technologyRepository.save(transportTechnology);
        return transportTechnology.getTechnologyID();
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
    public UUID addConnection(UUID transportTechnologyId, UUID sourceRoomId, Coordinate sourceCoordinate, UUID destinationRoomId, Coordinate destinationCoordinate)
    {
        TransportTechnology transportTechnology = technologyRepository.findById(transportTechnologyId).get();
        Room sourceRoom = roomRepository.findById(sourceRoomId).get();
        Room destinationRoom = roomRepository.findById(destinationRoomId).get();
        Connection connection = new Connection(sourceRoom,sourceCoordinate,destinationRoom,destinationCoordinate,transportTechnology);
        this.connectionRepository.save(connection);
        this.roomRepository.findById(sourceRoomId).get().addRoomConnection(connection);
       return connection.getConnectionID();
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name)
    {
        TidyUpRobot robot = new TidyUpRobot(name);
        this.tidyUpRobotRepository.save(robot);
        return robot.identify();
    }

    /**
     * This method lets the tidy-up robot execute a task.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on grid cells with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a wall or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Task task)
    {
        Transportable robot = tidyUpRobotRepository.findById(tidyUpRobotId).get();
        UUID roomID = task.getGridId();
        Roomable sourceRoom = robot.showRoom();
        Connection connection = null;
        Roomable room = null;

        if (sourceRoom != null)
            if(roomID!=null) {
                connection = connectionRepository.findById(sourceRoom.hasConnectionToRoom(roomID)).get();
                connection = roomRepository.findById(sourceRoom.identify()).get().getConnectionByID(connection.identify());
            }

        if (roomID != null) {
            this.roomRepository.findById(roomID).get().addRobotToRoom(robot);
            room = roomRepository.findById(roomID).get();
        }

        return this.tidyUpRobotRepository.findById(tidyUpRobotId).get().moveInDirection(task.getTaskType(),task.getNumberOfSteps(),connection,room);
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId)
    {
        return tidyUpRobotRepository.findById(tidyUpRobotId).get().showTransportableRoomID();
    }

    /**
     * This method returns the coordinate a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordinate of the tidy-up robot
     */
    public Coordinate getTidyUpRobotCoordinate(UUID tidyUpRobotId)
    {
       return tidyUpRobotRepository.findById(tidyUpRobotId).get().getActualPosition().getPosition();
    }
}
