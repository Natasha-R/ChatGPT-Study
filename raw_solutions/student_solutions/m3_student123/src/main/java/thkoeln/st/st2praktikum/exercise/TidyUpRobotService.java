package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.repositories.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.repositories.RoomRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TidyUpRobotRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TransportTechnologyRepository;

import java.util.UUID;


@Service
public class TidyUpRobotService {

    @Autowired
    private TidyUpRobotRepository tidyUpRobotRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private ConnectionRepository connectionRepository;
    @Autowired
    private TransportTechnologyRepository transportTechnologyRepository;


    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        Room room = new Room(width,height);
        roomRepository.save(room);
        return room.getRoomId();
    }

    /**
     * This method adds a barrier to a given room.
     * @param roomId the ID of the room the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID roomId, Barrier barrier) {
        this.roomRepository.findById(roomId).orElseThrow(() -> new IllegalArgumentException("RoomId does not exist!")).setBorder(barrier);
    }

    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        TransportTechnology transportTechnology = new TransportTechnology(technology);
        transportTechnologyRepository.save(transportTechnology);
        return transportTechnology.getTransportTechnologyID();
    }

    /**
     * This method adds a traversable connection between two rooms based on a transport technology. Connections only work in one direction.
     * @param transportTechnologyId the transport technology which is used by the connection
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourceVector2D the vector-2D of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationVector2D the vector-2D of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportTechnologyId, UUID sourceRoomId, Vector2D sourceVector2D, UUID destinationRoomId, Vector2D destinationVector2D) {
        Room source = roomRepository.findById(sourceRoomId).orElseThrow(() -> new IllegalArgumentException("SourceroomId does not exist!"));
        Room destination =  roomRepository.findById(destinationRoomId).orElseThrow(() -> new IllegalArgumentException("DestinationroomId does not exist!"));
        if(source.checkIfVectorOutOfBounds(sourceVector2D))
            throw new IllegalArgumentException("Coordinates are Out of Bounds: Source " + sourceVector2D.toString());
        if(destination.checkIfVectorOutOfBounds(destinationVector2D))
            throw new IllegalArgumentException("Coordinates are Out of Bounds: Destination " + destinationVector2D.toString());
        Connection connection = new Connection( transportTechnologyId, sourceRoomId, sourceVector2D, destinationRoomId, destinationVector2D);
        this.connectionRepository.save(connection);
        return connection.getConnectionId();
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot robot = new TidyUpRobot(name);
        this.tidyUpRobotRepository.save(robot);
        return robot.getTidyUpRobotID();
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
     *      (Movement commands are always successful, even if the tidy-up robot hits a barrier or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Task task) {

        TidyUpRobot robot = this.tidyUpRobotRepository.findById(tidyUpRobotId).orElseThrow(()->new IllegalArgumentException("This TidyUpRobot does not Exist."));
        Room room = null;
        if(robot.getRoomId() != null) room = this.roomRepository.findById(robot.getRoomId()).get();
        //Room room = roomRepository.findById(robot.getRoomID()).orElseThrow(() -> new IllegalArgumentException("RoomID does not exist!"));
        var success = false;
        switch (task.getTaskType()){
            case NORTH:
                success = robot.moveNorth(room, task.getNumberOfSteps());
                break;
            case SOUTH:
                success = robot.moveSouth(room,task.getNumberOfSteps());
                break;
            case EAST:
                success = robot.moveEast(room,task.getNumberOfSteps());
                break;
            case WEST:
                success = robot.moveWest(room,task.getNumberOfSteps());
                break;
            case TRANSPORT:
                success = robot.traverseRobot(this.connectionRepository,task.getGridId());
                break;
            case ENTER:
                success = robot.placeRobot(this.roomRepository,this.tidyUpRobotRepository,task.getGridId());
                break;
            default:
                throw new UnsupportedOperationException("Fail");
        }
        return success;
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return this.tidyUpRobotRepository.findById(tidyUpRobotId).orElseThrow(()->new IllegalArgumentException("This TidyUpRobot does not Exist.")).getRoomId();
    }

    /**
     * This method returns the vector-2D a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the vector-2D of the tidy-up robot
     */
    public Vector2D getTidyUpRobotVector2D(UUID tidyUpRobotId){
        return this.tidyUpRobotRepository.findById(tidyUpRobotId).orElseThrow(()->new IllegalArgumentException("This TidyUpRobot does not Exist.")).getVector2D();
    }
}
