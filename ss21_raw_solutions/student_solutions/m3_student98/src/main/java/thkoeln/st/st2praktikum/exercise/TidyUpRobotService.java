package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;


@Service
public class TidyUpRobotService {

    private MapInstance mapInstance;
    private RoomRepository roomRepository;
    private TidyUpRobotRepository tidyUpRobotRepository;
    private TransportCategoryRepository transportCategoryRepository;

    @Autowired
    public TidyUpRobotService(MapInstance mapInstance, RoomRepository roomRepository, TidyUpRobotRepository tidyUpRobotRepository,
                              TransportCategoryRepository transportCategoryRepository
    ) {
        this.mapInstance = mapInstance;
        this.roomRepository = roomRepository;
        this.tidyUpRobotRepository = tidyUpRobotRepository;
        this.transportCategoryRepository = transportCategoryRepository;
    }

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        Room newRoom = new Room(height, width);
        return roomRepository.save(newRoom).getId();
    }

    /**
     * This method adds a obstacle to a given room.
     * @param roomId the ID of the room the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID roomId, Obstacle obstacle) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new EntityNotFoundException(roomId.toString()));
        room.addObstacle(obstacle);
        roomRepository.save(room);
    }

    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        TransportCategory newTransportCategory = new TransportCategory(category);
        transportCategoryRepository.save(newTransportCategory);
        return newTransportCategory.getId();
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
    public UUID addConnection(UUID transportCategoryId, UUID sourceRoomId, Vector2D sourceVector2D, UUID destinationRoomId, Vector2D destinationVector2D) {
        TransportCategory transportCategory = transportCategoryRepository.findById(transportCategoryId)
                .orElseThrow(() -> new EntityNotFoundException(transportCategoryId.toString()));
        Room sourceRoom = roomRepository.findById(sourceRoomId)
                .orElseThrow(() -> new EntityNotFoundException(sourceRoomId.toString()));
        Room destinationRoom = roomRepository.findById(destinationRoomId)
                .orElseThrow(() -> new EntityNotFoundException(destinationRoomId.toString()));
        sourceVector2D.setRoom(sourceRoom);
        destinationVector2D.setRoom(destinationRoom);
        Connection newConnection = new Connection(sourceVector2D, destinationVector2D, transportCategory);
        sourceRoom.addConnection(newConnection);
        roomRepository.save(sourceRoom);
        return newConnection.getId();
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot newRobot = new TidyUpRobot(name);
        tidyUpRobotRepository.save(newRobot);
        return newRobot.getId();
    }

    /**
     * This method lets the tidy-up robot execute a task.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on squares with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a obstacle or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Task task) {
        TidyUpRobot robot = tidyUpRobotRepository.findById(tidyUpRobotId).orElseThrow(() -> new EntityNotFoundException(tidyUpRobotId.toString()));
        return robot.processTask(task, mapInstance);
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        TidyUpRobot robot = tidyUpRobotRepository.findById(tidyUpRobotId).orElseThrow(() -> new EntityNotFoundException(tidyUpRobotId.toString()));
        return robot.position.getRoomID();
    }

    /**
     * This method returns the vector-2D a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the vector-2D of the tidy-up robot
     */
    public Vector2D getTidyUpRobotVector2D(UUID tidyUpRobotId){
        TidyUpRobot robot = tidyUpRobotRepository.findById(tidyUpRobotId).orElseThrow(() -> new EntityNotFoundException(tidyUpRobotId.toString()));
        return robot.position;
    }
}
