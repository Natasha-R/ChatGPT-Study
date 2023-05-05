package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@Component
public class TidyUpRobotService {

    @Autowired
    RoomRepository roomRepository;
    @Autowired
    TidyUpRobotRepository tidyUpRobotRepository;
    @Autowired
    TransportCategoryRepository transportCategoryRepository;


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
     * This method adds a barrier to a given room.
     * @param roomId the ID of the room the barrier shall be placed on
     * @param barrier the end points of the barrier
     */
    public void addBarrier(UUID roomId, Barrier barrier) {
        Room selectedRoom = roomRepository.findById(roomId).get();
        selectedRoom.addBarrier(barrier);
        roomRepository.save(selectedRoom);
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
     * @param sourcePoint the point of the entry point
     * @param destinationRoomId the ID of the room where the exit point of the connection is located
     * @param destinationPoint the point of the exit point
     * @return the UUID of the created connection
     */
    public UUID addConnection(UUID transportCategoryId, UUID sourceRoomId, Point sourcePoint, UUID destinationRoomId, Point destinationPoint) {
        Room sourceRoom = roomRepository.findById(sourceRoomId).get();
        TransportCategory transportCategory = transportCategoryRepository.findById(transportCategoryId).get();
        UUID newConnectionId = sourceRoom.addConnection(transportCategory, sourcePoint, destinationRoomId, destinationPoint);
        roomRepository.save(sourceRoom);
        return newConnectionId;
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot newTidyUpRobot = new TidyUpRobot(name);
        return tidyUpRobotRepository.save(newTidyUpRobot).getId();
    }

    /**
     * This method lets the tidy-up robot execute a command.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param command the given command
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on cells with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a barrier or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Command command) {
        Controller controller = new Controller(tidyUpRobotRepository, roomRepository);
        return controller.processCommand(tidyUpRobotId, command);
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return tidyUpRobotRepository.findById(tidyUpRobotId).get().getRoom().getId();
    }

    /**
     * This method returns the point a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the point of the tidy-up robot
     */
    public Point getTidyUpRobotPoint(UUID tidyUpRobotId){
        return tidyUpRobotRepository.findById(tidyUpRobotId).get().getPosition();
    }
}
