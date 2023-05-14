package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class TidyUpRobotService {
    private TidyUpRobotRepository tidyUpRobotRepository;
    private RoomRepository roomRepository;
    private ConnectionRepository connectionRepository;
    private TransportTechnologyRepository transportTechnologyRepository;

    @Autowired
    public TidyUpRobotService(TidyUpRobotRepository tidyUpRobotRepository, RoomRepository roomRepository,
                              ConnectionRepository connectionRepository, TransportTechnologyRepository transportTechnologyRepository) {
        this.tidyUpRobotRepository = tidyUpRobotRepository;
        this.roomRepository = roomRepository;
        this.connectionRepository = connectionRepository;
        this.transportTechnologyRepository = transportTechnologyRepository;
    }

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        Room newRoom = new Room(width, height);
        roomRepository.save(newRoom);
        return newRoom.getId();
    }

    /**
     * This method adds a barrier to a given room.
     * @param roomId the ID of the room the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID roomId, Barrier barrier) {
        roomRepository.findById(roomId).get().addBarrier(barrier);
    }

    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */
    public UUID addTransportTechnology(String technology) {
        TransportTechnology newTransportTechnology = new TransportTechnology(technology);
        transportTechnologyRepository.save(newTransportTechnology);
        return newTransportTechnology.getId();
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
        Connection newConnection = new Connection(
                sourceRoomId,
                sourcePoint,
                destinationRoomId,
                destinationPoint
        );
        connectionRepository.save(newConnection);
        return transportTechnologyRepository
                .findById(transportTechnologyId)
                .stream()
                .findFirst()
                .orElse(null)
                .addConnection(newConnection);
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot newTidyUpRobot = new TidyUpRobot(name);
        tidyUpRobotRepository.save(newTidyUpRobot);
        return newTidyUpRobot.getId();
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
        TidyUpRobot tidyUpRobot = tidyUpRobotRepository.findById(tidyUpRobotId).get();

        switch (task.getTaskType()) {
            case ENTER:
                List<TidyUpRobot> robotsInTargetRoom = tidyUpRobotRepository.findByRoomIdAndRoomIdIsNotNull(task.getGridId());
                System.out.println(tidyUpRobot.getId());
                return tidyUpRobot.placeInInitialRoom(task, robotsInTargetRoom);
            case TRANSPORT:
                TransportTechnology transportTechnology =
                        transportTechnologyRepository
                                .findByConnectionsSourceRoomIdAndConnectionsSourcePoint(tidyUpRobot.getRoomId(), tidyUpRobot.getPoint()).stream()
                                .findFirst()
                                .orElse(null);
                List<TidyUpRobot> robotsInDestinationRoom = tidyUpRobotRepository.findByRoomIdAndRoomIdIsNotNull(task.getGridId());
                return tidyUpRobot.transport(task, transportTechnology, robotsInDestinationRoom);
            case NORTH:
            case WEST:
            case SOUTH:
            case EAST:
                Room currentRoom = roomRepository.findById(tidyUpRobot.getRoomId()).get();
                List<TidyUpRobot> robotsInCurrentRoom = tidyUpRobotRepository.findByRoomIdAndRoomIdIsNotNull(currentRoom.getId());
                tidyUpRobot.walk(task, currentRoom, robotsInCurrentRoom);
                return true;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return tidyUpRobotRepository
                .findById(tidyUpRobotId)
                .get()
                .getRoomId();
    }

    /**
     * This method returns the point a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the point of the tidy-up robot
     */
    public Point getTidyUpRobotPoint(UUID tidyUpRobotId){
        return tidyUpRobotRepository
                .findById(tidyUpRobotId)
                .get()
                .getPoint();
    }
}
