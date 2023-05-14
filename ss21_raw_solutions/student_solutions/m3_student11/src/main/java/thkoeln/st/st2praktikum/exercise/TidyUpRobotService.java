package thkoeln.st.st2praktikum.exercise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thkoeln.st.st2praktikum.exercise.repositories.ConnectionRepository;
import thkoeln.st.st2praktikum.exercise.repositories.RoomRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TidyUpRobotRepository;
import thkoeln.st.st2praktikum.exercise.repositories.TransportTechnologyRepository;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class TidyUpRobotService {
    @Id
    private final UUID ID = UUID.randomUUID();

    @Autowired
    private RoomRepository roomRepository;
    //private final ArrayList<Room> rooms = new ArrayList<>();
    @Autowired
    private TidyUpRobotRepository tidyUpRobotRepository;
    //private final ArrayList<TidyUpRobot> tidyUpRobots = new ArrayList<>();
    @Autowired
    private ConnectionRepository connectionRepository;
    //private final ArrayList<Connection> connections = new ArrayList<>();
    @Autowired
    private TransportTechnologyRepository transportTechnologyRepository;
    //private final ArrayList<TransportTechnology> transportTechnologies = new ArrayList<>();

    /**
     * This method creates a new room.
     * @param height the height of the room
     * @param width the width of the room
     * @return the UUID of the created room
     */
    public UUID addRoom(Integer height, Integer width) {
        Room newRoom = new Room(height, width);
        roomRepository.save(newRoom);
        return newRoom.getID();
    }

    /**
     * This method adds a obstacle to a given room.
     * @param roomId the ID of the room the obstacle shall be placed on
     * @param obstacle the end points of the obstacle
     */
    public void addObstacle(UUID roomId, Obstacle obstacle) {
        getRoomFromUUID(roomId).addObstacle(obstacle);
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
        Connection connect = new Connection( getTransportTechnologyFromUUID(transportTechnologyId),getRoomFromUUID(sourceRoomId), sourceCoordinate, getRoomFromUUID(destinationRoomId), destinationCoordinate, this);
        connectionRepository.save(connect);
        return connect.getID();
    }

    /**
     * This method adds a new tidy-up robot
     * @param name the name of the tidy-up robot
     * @return the UUID of the created tidy-up robot
     */
    public UUID addTidyUpRobot(String name) {
        TidyUpRobot newRobot = new TidyUpRobot(name);
        tidyUpRobotRepository.save(newRobot);
        return newRobot.getID();
    }

    /**
     * This method lets the tidy-up robot execute a task.
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @param task the given task
     * NORTH, WEST, SOUTH, EAST for movement
     * TRANSPORT for transport - only works on gridcells with a connection to another room
     * ENTER for setting the initial room where a tidy-up robot is placed. The tidy-up robot will always spawn at (0,0) of the given room.
     *
     * @return true if the command was executed successfully. Else false.
     *      (Movement commands are always successful, even if the tidy-up robot hits a obstacle or
     *      another tidy-up robot, and can move only a part of the steps, or even none at all.
     *      tr and en commands are only successful if the action can be performed.)
     */
    public Boolean executeCommand(UUID tidyUpRobotId, Task task) {
        TidyUpRobot tidyUpRobot = getRobotFromUUID(tidyUpRobotId);
        switch(task.getTaskType()){
            case TRANSPORT: return tidyUpRobot.transport(task.getGridId(),connectionRepository, tidyUpRobotRepository);
            case ENTER:     return tidyUpRobot.spawn(roomRepository.findById(task.getGridId()).get(), tidyUpRobotRepository);
            case NORTH:
            case SOUTH:
            case EAST:
            case WEST:
                tidyUpRobot.move(task.giveDirection(), task.getNumberOfSteps(), tidyUpRobotRepository, roomRepository);
                return true;
        }
        return false;
    }

    /**
     * This method returns the room-ID a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the UUID of the room the tidy-up robot is located on
     */
    public UUID getTidyUpRobotRoomId(UUID tidyUpRobotId){
        return getRobotFromUUID(tidyUpRobotId).getRoomId();
    }

    /**
     * This method returns the coordinates a tidy-up robot is standing on
     * @param tidyUpRobotId the ID of the tidy-up robot
     * @return the coordinates of the tidy-up robot
     */
    public Coordinate getCoordinate(UUID tidyUpRobotId){
        return getRobotFromUUID(tidyUpRobotId).getCoordinate();
    }

    public Room getRoomFromUUID(UUID roomId){
        try {
            return roomRepository.findById(roomId).get();
        } catch (Exception e){
            return null;
        }

    }

    public TidyUpRobot getRobotFromUUID(UUID robotId){
        for (TidyUpRobot tidyUpRobot : tidyUpRobotRepository.findAll())
            if (tidyUpRobot.getID().equals(robotId))
                return tidyUpRobot;
        return null;
    }

    public TransportTechnology getTransportTechnologyFromUUID(UUID transportTechnologyId){
        for (TransportTechnology transportTechnology : transportTechnologyRepository.findAll())
            if (transportTechnology.getID().equals(transportTechnologyId))
                return transportTechnology;
        return null;
    }

    public TidyUpRobotRepository getTidyUpRobots(){
        return tidyUpRobotRepository;
    }

    public RoomRepository getRooms(){
        return roomRepository;
    }

    public ConnectionRepository getConnections(){ return connectionRepository; }

    /**
     * This method adds a transport technology
     * @param technology the type of the transport technology
     * @return the UUID of the created transport technology
     */

    public UUID addTransportTechnology(String technology){
        TransportTechnology transportTechnology = new TransportTechnology(technology);
        transportTechnologyRepository.save(transportTechnology);
        return transportTechnology.getID();
    }
}
