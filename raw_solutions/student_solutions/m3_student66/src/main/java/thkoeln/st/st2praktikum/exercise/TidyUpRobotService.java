package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.entities.*;
import thkoeln.st.st2praktikum.exceptions.OutOfBoundsException;
import thkoeln.st.st2praktikum.exceptions.TaskException;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;


@Service
public class TidyUpRobotService {

    private TidyUpRobotRepository tidyUpRobotRepository;
    private ConnectionRepository connectionRepository;
    private RoomRepository roomRepository;
    private TransportCategoryRepository transportCategoryRepository;

    @Autowired
    public TidyUpRobotService(TidyUpRobotRepository tidyUpRobotRepository, ConnectionRepository connectionRepository,
                              RoomRepository roomRepository, TransportCategoryRepository transportCategoryRepository){
        this.tidyUpRobotRepository = tidyUpRobotRepository;
        this.connectionRepository = connectionRepository;
        this.roomRepository = roomRepository;
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
        return roomRepository.save(newRoom).getUuid();
    }

    /**
     * This method adds a wall to a given room.
     * @param roomId the ID of the room the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID roomId, Wall wall) {
        Room room = roomRepository.findByUuid(roomId);
        if(room.isInsideLimit(wall.getStart()) && room.isInsideLimit(wall.getEnd())) {
            room.addWall(wall);
        }else{
            throw new OutOfBoundsException("Wall is placed out of bounds.");
        }
    }

    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        TransportCategory newTransportCategory = new TransportCategory(category);
        return transportCategoryRepository.save(newTransportCategory).getUuid();
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
        Room sourceRoom = roomRepository.findByUuid(sourceRoomId);
        Room destinationRoom = roomRepository.findByUuid(destinationRoomId);
        TransportCategory transportCategory = transportCategoryRepository.findByUuid(transportCategoryId);
        if(sourceRoom.isInsideLimit(sourcePoint) && destinationRoom.isInsideLimit(destinationPoint)) {
            Connection newConnection = new Connection(sourceRoom, sourcePoint, destinationRoom, destinationPoint);
            sourceRoom.getUseAbles().add(newConnection);
            transportCategory.addConnection(newConnection);
            return connectionRepository.save(newConnection).getUuid();
        }else {
            throw new OutOfBoundsException("Connection is placed out of bounds.");
        }
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot newRobot = new TidyUpRobot(name);
        return tidyUpRobotRepository.save(newRobot).getUuid();
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
    public Boolean executeCommand(UUID tidyUpRobotId, Task task) {
        TidyUpRobot tidyUpRobot = tidyUpRobotRepository.findByUuid(tidyUpRobotId);
        switch (task.getTaskType()){
            case NORTH:
            case SOUTH:
            case WEST:
            case EAST:
                tidyUpRobot.go(task);
                return true;
            case ENTER:
                Room initialRoom = roomRepository.findByUuid(task.getGridId());
                if(initialRoom == null)
                    throw new TaskException("Doesnt know a room with UUID: " + task.getGridId());
                return tidyUpRobot.init(initialRoom);
            case TRANSPORT:
                Room destinationRoom = roomRepository.findByUuid(task.getGridId());
                if(destinationRoom == null)
                    throw new TaskException("Doesnt know a room with UUID: " + task.getGridId());
                return tidyUpRobot.transport(destinationRoom);
            default:
                throw new TaskException("Cant get in here.");
        }
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return tidyUpRobotRepository.findByUuid(tidyUpRobotId).getRoomId();
    }

    /**
     * This method returns the point a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the point of the tidy-up robot
     */
    public Point getTidyUpRobotPoint(UUID tidyUpRobotId){
        return tidyUpRobotRepository.findByUuid(tidyUpRobotId).getPoint();
    }
}
