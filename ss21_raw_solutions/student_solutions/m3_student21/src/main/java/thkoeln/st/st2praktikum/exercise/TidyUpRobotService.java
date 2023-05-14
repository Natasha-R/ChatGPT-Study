package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.Connection.Connection;
import thkoeln.st.st2praktikum.exercise.Connection.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.Connection.TransportCategory;
import thkoeln.st.st2praktikum.exercise.Connection.TransportCategoryRepository;
import thkoeln.st.st2praktikum.exercise.Room.Room;
import thkoeln.st.st2praktikum.exercise.Room.RoomRepository;

import java.util.UUID;

@Service
public class TidyUpRobotService {

    GameWorld gameWorld = new GameWorld(this);

    @Autowired
    public TidyUpRobotRepository tidyUpRobotRepository;

    @Autowired
    public RoomRepository roomRepository;

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private TransportCategoryRepository transportCategoryRepository;

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        Room room = new Room(height, width);
        return roomRepository.save(room).getId();
    }

    /**
     * This method adds a obstacle to a given room.
     * @param roomId the ID of the room the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID roomId, Obstacle obstacle) {
        roomRepository.findById(roomId).get().addObstacle(obstacle);
    }

    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        TransportCategory transportCategory = new TransportCategory(category);
        return transportCategoryRepository.save(transportCategory).getId();
    }

    /**
     * This method adds a traversable connection between two rooms based on a transport category. Connections only work in one direction.
     * @param transportCategoryId the transport category which is used by the connection
     * @param sourceRoomId the ID of the room where the entry point of the connection is located
     * @param sourceCoordinate the coordinate of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationCoordinate the coordinate of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportCategoryId, UUID sourceRoomId, Coordinate sourceCoordinate, UUID destinationRoomId, Coordinate destinationCoordinate) {

        Connection connection = new Connection(transportCategoryRepository.findById(transportCategoryId).get(),
                                                roomRepository.findById(sourceRoomId).get(),
                                                sourceCoordinate,
                                                roomRepository.findById(destinationRoomId).get(),
                                                destinationCoordinate);

        roomRepository.findById(sourceRoomId).get().addTransportable(connection);
        return connectionRepository.save(connection).getId();
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot tidyUpRobot = new TidyUpRobot(name);
        return tidyUpRobotRepository.save(tidyUpRobot).getId();
    }

    /**
     * This method lets the tidy-up robot execute a order.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param order the given order
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on cells with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a obstacle or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Order order) {
        if(order.getOrderType() == OrderType.ENTER || order.getOrderType() == OrderType.TRANSPORT){
            Room room = roomRepository.findById(order.getGridId()).get();
            return tidyUpRobotRepository.findById(tidyUpRobotId).get().executeRoomCommand(room, order);
        }
        tidyUpRobotRepository.findById(tidyUpRobotId).get().move(order);
        return true;
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return tidyUpRobotRepository.findById(tidyUpRobotId).get().getRoomId();
    }

    /**
     * This method returns the coordinate a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordinate of the tidy-up robot
     */
    public Coordinate getTidyUpRobotCoordinate(UUID tidyUpRobotId){
        return tidyUpRobotRepository.findById(tidyUpRobotId).get().getCoordinate();
    }
}
