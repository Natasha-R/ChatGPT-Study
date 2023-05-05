package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;


@Service
public class TidyUpRobotService {

    private List<Room> rooms = new ArrayList<Room>();
    private List<Connection> connections = new ArrayList<Connection>();
    private List<TidyUpRobot> tidyUpRobots = new ArrayList<TidyUpRobot>();
    private List<TransportCategory> transportCategories = new ArrayList<TransportCategory>();

    private TransportCategoryRepository transportCategoryRepository;
    private TidyUpRobotRepository tidyUpRobotRepository;
    private RoomRepository roomRepository;
    private ConnectionRepository connectionRepository;

    @Autowired
    public TidyUpRobotService(
            TransportCategoryRepository transportCategoryRepository,
            TidyUpRobotRepository tidyUpRobotRepository,
            RoomRepository roomRepository,
            ConnectionRepository connectionRepository){
    this.transportCategoryRepository = transportCategoryRepository;
    this.tidyUpRobotRepository = tidyUpRobotRepository;
    this.roomRepository = roomRepository;
    this.connectionRepository = connectionRepository;
    }

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) throws Vector2DException{
        Room room = new Room(height, width);
        rooms.add(room);
        return room.getId();
    }

    /**
     * This method adds a wall to a given room.
     * @param roomId the ID of the room the wall shall be placed on
     * @param wall the end points of the wall
     */
    public void addWall(UUID roomId, Wall wall) {
        Room room = getRoomByRoomId(roomId);
        if(room.isCoordinateInBounds(wall.getStart()) && room.isCoordinateInBounds(wall.getEnd()))
            room.addBlocker(wall);
        else
            throw new WallException("wall out of bounds");
    }

    /**
     * This method adds a transport category
     * @param category the type of the transport category
     * @return the UUID of the created transport category
     */
    public UUID addTransportCategory(String category) {
        TransportCategory transportCategory = new TransportCategory(category);
        transportCategories.add(transportCategory);
        return transportCategory.getId();
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
        TransportCategory transportCategory = getTransportCategoryByTransportCategoryId(transportCategoryId);
        Room sourceRoom = getRoomByRoomId(sourceRoomId);
        Room destinationRoom = getRoomByRoomId(destinationRoomId);
        Connection connection = new Connection(transportCategory, sourceRoom, sourceVector2D, destinationRoom, destinationVector2D);

        if(sourceRoom.isCoordinateInBounds(sourceVector2D) && destinationRoom.isCoordinateInBounds(destinationVector2D)) {
            connections.add(connection);
            return connection.getId();
        }else {
            throw new Vector2DException("connection out of bounds");
        }
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot tidyUpRobot = new TidyUpRobot(name);
        tidyUpRobots.add(tidyUpRobot);
        return tidyUpRobot.getId();
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
     *      (Movement commands are always successful, even if the tidy-up robot hits a wall or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Task task) {
        TidyUpRobot tidyUpRobot = getRobotByRobotId(tidyUpRobotId);

        switch (task.getTaskType()){
            case TRANSPORT:
                return transportRobot(tidyUpRobot, task);
            case ENTER:
                return placeRobot(tidyUpRobot, task);
            default:
                return moveRobot(tidyUpRobot, task);
        }
    }

    private Boolean transportRobot(TidyUpRobot tidyUpRobot, Task task){
        Room destinationRoom = getRoomByRoomId(task.getGridId());

        Optional<Connection> possibleConnections = findPossibleConnections(destinationRoom, tidyUpRobot);

        if(possibleConnections.isPresent() && destinationNotOccupied(destinationRoom, possibleConnections.get().getSourceCoordinates(), tidyUpRobot)){
            tidyUpRobot.setRoom(destinationRoom);
            tidyUpRobot.move(possibleConnections.get().getDestinationCoordinates());
            destinationRoom.addBlocker(tidyUpRobot);
            possibleConnections.get().getSourceRoom().removeBlocker(tidyUpRobot);

            persistNewData();
            return true;
        }else
            return false;
    }

    private Optional<Connection> findPossibleConnections(Room destinationRoom, TidyUpRobot tidyUpRobot){
        Stream<Connection> possibleConnectionsStream = connections.stream();

        possibleConnectionsStream = possibleConnectionsStream.filter(findConnection ->
                findConnection.getSourceCoordinates().equals(tidyUpRobot.getVector2D())
                        && findConnection.getSourceRoom() == tidyUpRobot.getRoom()
                        && findConnection.getDestinationRoom() == destinationRoom);

        return possibleConnectionsStream.findFirst();
    }

    private Boolean destinationNotOccupied(Room destinationRoom, Vector2D coordinates, TidyUpRobot tidyUpRobot){
        return tidyUpRobots.stream().noneMatch(findTidyUpRobot ->
                findTidyUpRobot.getRoom() == destinationRoom
                        && findTidyUpRobot.getVector2D().equals(coordinates)
                        && findTidyUpRobot != tidyUpRobot);
    }

    private Boolean placeRobot(TidyUpRobot tidyUpRobot, Task task) {
        Room destinationRoom = getRoomByRoomId(task.getGridId());
        if(tidyUpRobot.getRoom() == null && destinationNotOccupied(destinationRoom, new Vector2D(0,0), tidyUpRobot)){
            tidyUpRobot.setRoom(destinationRoom);
            destinationRoom.addBlocker(tidyUpRobot);
            tidyUpRobot.move(new Vector2D(0,0));

            persistNewData();
            return true;
        }else
            return false;
    }

    private Boolean moveRobot(TidyUpRobot tidyUpRobot, Task task) {
        Vector2D coordinates = tidyUpRobot.getVector2D();
        Integer [] movement;
        int steps = task.getNumberOfSteps();
        switch (task.getTaskType()){    //direction
            case NORTH:
                movement = new Integer[]{0, 1};
                break;
            case EAST:
                movement = new Integer[]{1, 0};
                break;
            case SOUTH:
                movement = new Integer[]{0, -1};
                break;
            case WEST:
                movement = new Integer[]{-1, 0};
                break;
            default:
                return true;
        }
        for(int i = 0; i < steps; i++){
            try {
                coordinates = new Vector2D(coordinates.getX() + movement[0],coordinates.getY() + movement[1]);
                Room room = tidyUpRobot.getRoom();
                if(room.isCoordinateInBounds(coordinates) && room.isCoordinateEmpty(coordinates))
                    tidyUpRobot.move(coordinates);
                else
                    break;
            }catch (Vector2DException e){
            }
        }
        persistNewData();
        return true;
    }

    public void persistNewData(){
        transportCategoryRepository.deleteAll();
        tidyUpRobotRepository.deleteAll();
        roomRepository.deleteAll();
        connectionRepository.deleteAll();

        transportCategories.stream().forEach(transportCategory -> transportCategoryRepository.save(transportCategory));
        rooms.stream().forEach(room -> roomRepository.save(room));
        tidyUpRobots.stream().forEach(tidyUpRobot -> tidyUpRobotRepository.save(tidyUpRobot));
        connections.stream().forEach(connection -> connectionRepository.save(connection));
    }


    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return getRobotByRobotId(tidyUpRobotId).getRoom().getId();
    }

    /**
     * This method returns the vector-2D a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the vector-2D of the tidy-up robot
     */
    public Vector2D getTidyUpRobotVector2D(UUID tidyUpRobotId){
        return getRobotByRobotId(tidyUpRobotId).getVector2D();
    }

    private Room getRoomByRoomId(UUID roomId){
        return rooms.stream().filter(findRoom -> findRoom.getId().equals(roomId)).findAny().get();
    }
    private TidyUpRobot getRobotByRobotId(UUID robotId){
        return tidyUpRobots.stream().filter(findTidyUpRobot -> findTidyUpRobot.getId().equals(robotId)).findAny().get();
    }
    private TransportCategory getTransportCategoryByTransportCategoryId(UUID transportCategoryId){
        return transportCategories.stream().filter(findTransportCategory -> findTransportCategory.getId().equals(transportCategoryId)).findAny().get();
    }
}
